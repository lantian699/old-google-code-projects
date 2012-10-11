package com.alstom.lean.all.activities;



import java.sql.SQLException;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.dropbox.DownloadFileFromDropbox;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.InspectionTask;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.VisualInspection;
import com.alstom.lean.all.spreadsheet.SynchronizationTask;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;
import com.dropbox.client2.session.Session.AccessType;
import com.j256.ormlite.dao.Dao;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	
	private EditText login_edit;
	private EditText password_edit;
	private String login;
	private String password;
	private Button btn_ok;
	private DatabaseHelper dataHelper;
	
	
	
	final static private String APP_KEY = "7ensyen019ypui4";
	final static private String APP_SECRET = "joc5umz4cekhlvj";
	final static private AccessType ACCESS_TYPE = AccessType.DROPBOX;
	final static private String ACCOUNT_PREFS_NAME = "prefs";
	final static private String ACCESS_KEY_NAME = "ACCESS_KEY";
	final static private String ACCESS_SECRET_NAME = "ACCESS_SECRET";


	 private static DropboxAPI<AndroidAuthSession> mApi;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		
		
		login_edit = (EditText) findViewById(R.id.loginEditText);
		password_edit = (EditText) findViewById(R.id.mdpEditText);
		btn_ok = (Button) findViewById(R.id.okButton);
		
		btn_ok.setOnClickListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);

		try {
			dataHelper = DatabaseHelper.getInstance(this);
			Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
			List<Task> listTask = taskDao.queryForAll();
			
			if(listTask.size() == 0)
			new SynchronizationTask(this, dataHelper).execute("getAll");
			
			// We create a new AuthSession so that we can use the Dropbox API.
	        AndroidAuthSession session = buildSession();
	        mApi = new DropboxAPI<AndroidAuthSession>(session);
	       if(!mApi.getSession().isLinked())
	       mApi.getSession().startAuthentication(this);
	        
			
			//new DownloadFileFromDropbox(this,mApi, "/Alstom/Project ALBA").execute();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
    @Override
    protected void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                TokenPair tokens = session.getAccessTokenPair();
                storeKeys(tokens.key, tokens.secret);
  
            } catch (IllegalStateException e) {
                showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
            }
        }
    }
	

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.okButton:
			
			new DownloadFileFromDropbox(this,mApi, "/Alstom").execute();	
			
			/*Intent intent = new Intent();
			intent.setClass(getApplication(), ProjectListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);*/

			break;

		default:
			break;
		}
		
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
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
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
	
    private void showToast(String msg) {
        Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        error.show();
    }
    
    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void storeKeys(String key, String secret) {
        // Save the access key for later
        SharedPreferences prefs = getSharedPreferences(ACCOUNT_PREFS_NAME, 0);
        Editor edit = prefs.edit();
        edit.putString(ACCESS_KEY_NAME, key);
        edit.putString(ACCESS_SECRET_NAME, secret);
        edit.commit();
    }
 
	public static DropboxAPI<AndroidAuthSession> getDropboxApi(){
		return mApi;
	}

}
