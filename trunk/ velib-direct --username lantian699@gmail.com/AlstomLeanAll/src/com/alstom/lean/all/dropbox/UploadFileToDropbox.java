package com.alstom.lean.all.dropbox;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
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

public class UploadFileToDropbox extends AsyncTask<Void, Long, Boolean> {

	private Context context;
	private DropboxAPI<?> mApi;
	private final ProgressDialog mDialog;
	private boolean mCanceled;
    private Long mFileLen;
    private String mErrorMsg;
    private FileOutputStream mFos;
	private FileInputStream inputStream;


	public UploadFileToDropbox(Context context,DropboxAPI<AndroidAuthSession> api){
		this.context = context;
		this.mApi = api;

		
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
			
			String rootPath = Environment.getExternalStorageDirectory() + "/Alstom";
            File rootDirectory  = new File(rootPath);
        	if(rootDirectory.exists()){
        		
        		
        		for(int i=0; i< rootDirectory.listFiles().length;i++){
        			File sedDirectory = rootDirectory.listFiles()[i]; 
        			for(int j = 0;j<sedDirectory.listFiles().length; j++){
        				File file = sedDirectory.listFiles()[j];
        				
        				SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
        				String today = sdf.format(new Date());
        				
        				sdf = new SimpleDateFormat("yyyy MM dd");
        				String fileDate = sdf.format(new Date(file.lastModified()));
        				
        				inputStream = new FileInputStream(file);
        				
        				if(fileDate.equals(today)){
        					
        					mApi.putFile("Alstom/"+file.getParentFile().getName()+"/"+file.getName(), inputStream, file.length(), null, null);
        					
        				}
        				
                		System.out.println(fileDate.equals(today));
        				
        			}
        			
        		}
        		
        		
        	}

			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
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
        }
       
    }

    private void showToast(String msg) {
        Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        error.show();
    }
	
	
}
