package com.alstom.lean.all.dropbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.alstom.lean.all.activities.ProjectListActivity;
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

public class DownloadFileFromDropbox extends AsyncTask<Void, Long, Boolean>{

	private Context context;
	private DropboxAPI<?> mApi;
	private String mPath;
	private final ProgressDialog mDialog;
	private boolean mCanceled;
    private Long mFileLen;
    private String mErrorMsg;
    private FileOutputStream mFos;


	public DownloadFileFromDropbox(Context context,DropboxAPI<AndroidAuthSession> api, String dropboxPath){
		this.context = context;
		this.mApi = api;
		this.mPath = dropboxPath;
	
	
        
        
       
        
		
		
		 mDialog = new ProgressDialog(context);
	        mDialog.setMessage("Downloading necessary files");
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
		
		try {
            if (mCanceled) {
                return false;
            }


            Entry dirent = mApi.metadata(mPath, 1000, null, true, null);
           
            if (!dirent.isDir || dirent.contents == null) {
                mErrorMsg = "File or empty directory";
                return false;
            }
            ArrayList<Entry> thumbs = new ArrayList<Entry>();
            for (Entry ent: dirent.contents) {

                    thumbs.add(ent);
            }
            

            if (mCanceled) {
                return false;
            }

            if (thumbs.size() == 0) {
                // No thumbs in that directory
                mErrorMsg = "No pictures in that directory";
                return false;
            }

            for(int index = 0; index< thumbs.size(); index++){
            Entry ent = thumbs.get(index);
            String path = ent.path;
            String fileName = ent.fileName();
            mFileLen = ent.bytes;
            

            String rootPath = Environment.getExternalStorageDirectory() + "/Alstom";
            File file  = new File(rootPath);
        	if(!file.exists())
        		file.mkdirs();

            if(ent.thumbExists){
            	 try {
                 	
                     mFos = new FileOutputStream(rootPath+"/"+fileName);
                 
                    mApi.getThumbnail(path, mFos, ThumbSize.BESTFIT_960x640,
                    ThumbFormat.JPEG, null);
            
            	 } catch (FileNotFoundException e) {
                     mErrorMsg = "Couldn't create a local file to store the image";
                     return false;
                 }
            }else if(ent.isDir){
            	String directPath = rootPath+"/"+fileName;
            	File direct  = new File(directPath);
            	if(!direct.exists())
            		direct.mkdirs();
            	rootPath = directPath;
            	
            	
            	Entry subDirent = mApi.metadata(mPath+"/"+fileName, 1000, null, true, null);
            	for(Entry subEnt : subDirent.contents){
            		
            		try {
            			
            			File subFile = new File(rootPath, subEnt.fileName());
            			
            			if(subFile.exists()){
            				System.out.println("modified  " + subEnt.modified);
            			}else{
						mFos = new FileOutputStream(subFile);
						
						mApi.getFile(subEnt.path, null, mFos, null);
            			}
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
            	}
            }
            
            
            if (mCanceled) {
                return false;
            }
            
            // We must have a legitimate picture
            }
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
        	e.printStackTrace();
            mErrorMsg = "Network error.  Try again.";
        } catch (DropboxParseException e) {
            // Probably due to Dropbox server restarting, should retry
        	e.printStackTrace();
            mErrorMsg = "Dropbox error.  Try again.";
        } catch (DropboxException e) {
            // Unknown error
        	e.printStackTrace();
            mErrorMsg = "Unknown error.  Try again.";
        }
		
		return true;
	}
	
	@Override
    protected void onProgressUpdate(Long... progress) {
        int percent = (int)(100.0*(double)progress[0]/mFileLen + 0.5);
        mDialog.setProgress(percent);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mDialog.dismiss();
        if (!result) {
        	showToast(mErrorMsg);
        }else {
        	Intent intent = new Intent();
			intent.setClass(context, ProjectListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
		}
       
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        error.show();
    }
      
  

}
