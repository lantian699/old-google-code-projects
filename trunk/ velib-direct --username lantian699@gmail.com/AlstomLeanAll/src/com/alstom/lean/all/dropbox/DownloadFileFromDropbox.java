package com.alstom.lean.all.dropbox;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.DropboxAPI.ThumbFormat;
import com.dropbox.client2.DropboxAPI.ThumbSize;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxIOException;
import com.dropbox.client2.exception.DropboxParseException;
import com.dropbox.client2.exception.DropboxPartialFileException;
import com.dropbox.client2.exception.DropboxServerException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

public class DownloadFileFromDropbox extends AsyncTask<Void, Long, Boolean>{

	private Context context;
//	private DropboxAPI<?> mApi;
	private String mPath;
	private final ProgressDialog mDialog;
	private boolean mCanceled;
    private Long mFileLen;
    private String mErrorMsg;
    private FileOutputStream mFos;
    private Drawable mDrawable;
    private final static String IMAGE_FILE_NAME = "dbroulette.png";
    
    
    
    final static private String APP_KEY = "7ensyen019ypui4";
    final static private String APP_SECRET = "joc5umz4cekhlvj";

    // If you'd like to change the access type to the full Dropbox instead of
    // an app folder, change this value.
    final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;

    
    ///////////////////////////////////////////////////////////////////////////
    //                      End app-specific settings.                       //
    ///////////////////////////////////////////////////////////////////////////

    // You don't need to change these, leave them alone.
    final static private String ACCOUNT_PREFS_NAME = "prefs";
    final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
    final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";


    DropboxAPI<AndroidAuthSession> mApi;

	public DownloadFileFromDropbox(Context context,String dropboxPath){
		this.context = context;
//		this.mApi = api;
		this.mPath = dropboxPath;
		
		
		
		// We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = buildSession();
        mApi = new DropboxAPI<AndroidAuthSession>(session);
		
		
		
		 mDialog = new ProgressDialog(context);
	        mDialog.setMessage("Downloading Image");
	        mDialog.setButton("Cancel", new OnClickListener() {
	            public void onClick(DialogInterface dialog, int which) {
	                mCanceled = true;
	                mErrorMsg = "Canceled";

	                // This will cancel the getThumbnail operation by closing
	                // its stream
	                if (mFos != null) {
	                    try {
	                        mFos.close();
	                    } catch (IOException e) {
	                    }
	                }
	            }
	        });

	        mDialog.show();
	}
	
	@Override
	protected Boolean doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		
		try {
            if (mCanceled) {
                return false;
            }

            // Get the metadata for a directory
            Entry dirent = mApi.metadata(mPath, 1000, null, true, null);

            if (!dirent.isDir || dirent.contents == null) {
                // It's not a directory, or there's nothing in it
                mErrorMsg = "File or empty directory";
                return false;
            }

            // Make a list of everything in it that we can get a thumbnail for
            ArrayList<Entry> thumbs = new ArrayList<Entry>();
            for (Entry ent: dirent.contents) {
                if (ent.thumbExists) {
                    // Add it to the list of thumbs we can choose from
                    thumbs.add(ent);
                }
            }

            if (mCanceled) {
                return false;
            }

            if (thumbs.size() == 0) {
                // No thumbs in that directory
                mErrorMsg = "No pictures in that directory";
                return false;
            }

            // Now pick a random one
            int index = (int)(Math.random() * thumbs.size());
            Entry ent = thumbs.get(index);
            String path = ent.path;
            mFileLen = ent.bytes;


            String cachePath = context.getCacheDir().getAbsolutePath() + "/" + IMAGE_FILE_NAME;
            try {
                mFos = new FileOutputStream(cachePath);
            } catch (FileNotFoundException e) {
                mErrorMsg = "Couldn't create a local file to store the image";
                return false;
            }

            // This downloads a smaller, thumbnail version of the file.  The
            // API to download the actual file is roughly the same.
            mApi.getThumbnail(path, mFos, ThumbSize.BESTFIT_960x640,
                    ThumbFormat.JPEG, null);
            if (mCanceled) {
                return false;
            }

            mDrawable = Drawable.createFromPath(cachePath);
            // We must have a legitimate picture
            return true;

        } catch (DropboxUnlinkedException e) {
            // The AuthSession wasn't properly authenticated or user unlinked.
        } catch (DropboxPartialFileException e) {
            // We canceled the operation
            mErrorMsg = "Download canceled";
        } catch (DropboxServerException e) {
            // Server-side exception.  These are examples of what could happen,
            // but we don't do anything special with them here.
            if (e.error == DropboxServerException._304_NOT_MODIFIED) {
                // won't happen since we don't pass in revision with metadata
            } else if (e.error == DropboxServerException._401_UNAUTHORIZED) {
                // Unauthorized, so we should unlink them.  You may want to
                // automatically log the user out in this case.
            } else if (e.error == DropboxServerException._403_FORBIDDEN) {
                // Not allowed to access this
            } else if (e.error == DropboxServerException._404_NOT_FOUND) {
                // path not found (or if it was the thumbnail, can't be
                // thumbnailed)
            } else if (e.error == DropboxServerException._406_NOT_ACCEPTABLE) {
                // too many entries to return
            } else if (e.error == DropboxServerException._415_UNSUPPORTED_MEDIA) {
                // can't be thumbnailed
            } else if (e.error == DropboxServerException._507_INSUFFICIENT_STORAGE) {
                // user is over quota
            } else {
                // Something else
            }
            // This gets the Dropbox error, translated into the user's language
            mErrorMsg = e.body.userError;
            if (mErrorMsg == null) {
                mErrorMsg = e.body.error;
            }
        } catch (DropboxIOException e) {
            // Happens all the time, probably want to retry automatically.
            mErrorMsg = "Network error.  Try again.";
        } catch (DropboxParseException e) {
            // Probably due to Dropbox server restarting, should retry
            mErrorMsg = "Dropbox error.  Try again.";
        } catch (DropboxException e) {
            // Unknown error
            mErrorMsg = "Unknown error.  Try again.";
        }
		
		return null;
	}
	
	@Override
    protected void onProgressUpdate(Long... progress) {
        int percent = (int)(100.0*(double)progress[0]/mFileLen + 0.5);
        mDialog.setProgress(percent);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mDialog.dismiss();
        if (result) {
            // Set the image now that we have it
        //    mView.setImageDrawable(mDrawable);
        } else {
            // Couldn't download it, so show an error
            showToast(mErrorMsg);
        }
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        error.show();
    }
    
    
    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session;

        String[] stored = getKeys();
        if (stored != null) {
            AccessTokenPair accessToken = new AccessTokenPair(stored[0], stored[1]);
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE, accessToken);
        } else {
            session = new AndroidAuthSession(appKeyPair, ACCESS_TYPE);
        }

        return session;
    }
    
    
    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     *
     * @return Array of [access_key, access_secret], or null if none stored
     */
    private String[] getKeys() {
        SharedPreferences prefs = context.getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(ACCESS_KEY_NAME, null);
        String secret = prefs.getString(ACCESS_SECRET_NAME, null);
        if (key != null && secret != null) {
        	String[] ret = new String[2];
        	ret[0] = key;
        	ret[1] = secret;
        	return ret;
        } else {
        	return null;
        }
    }

}
