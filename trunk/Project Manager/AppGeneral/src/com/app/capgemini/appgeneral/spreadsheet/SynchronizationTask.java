package com.app.capgemini.appgeneral.spreadsheet;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;

import com.app.capgemini.appgeneral.ProjectControllerActivity;
import com.app.capgemini.appgeneral.WelcomeScreenActivity;
import com.app.capgemini.appgeneral.models.DatabaseHelper;
import com.app.capgemini.appgeneral.models.tools.Tools;
import com.app.capgemini.appgeneral.spreadsheet.Worksheet.Table;
import com.google.api.services.spreadsheet.client.SpreadsheetClient;

/**
 * AsyncTask to synchronize the local DB with the spreadsheet
 * 
 * @author guillaumedavo
 * 
 */
public class SynchronizationTask extends AsyncTask<String, Integer, Integer> {

	private static final String TAG = "SynchronizationTask";
	private Context mMainActivity;

	private static final int NO_ACCOUNT_ERROR = -1;
	private static final int IO_EXCEPTION = -2;

	public String errorMessage;

	private static final String KEY_LAST_SYNC = "last_sync";
	private static Time lastSyncTime;

	// columns indices (store them for performance reasons)
	private Integer mUpdatedIndex;
	private Integer mIdIndex;
	private ProgressDialog dialog;
	private List<ContentValues> allValues;

	// same as in settings.xml
	// Change this key for a different source spreadsheet
	private String spreadsheetKey;
	private SpreadsheetClient client;
	private DatabaseHelper dataHelper;
	private boolean isSend;
	private SpreadsheetAndroidRequestInitializer requestInitializer;

	public SynchronizationTask(Context mainActivity,
			DatabaseHelper dataHelper) {
		super();
		mMainActivity = mainActivity;
		this.dataHelper = dataHelper;
		SharedPreferences settings = mainActivity.getSharedPreferences(TAG, 0);
		long millis = settings.getLong(KEY_LAST_SYNC, 0);
		lastSyncTime = new Time();
		lastSyncTime.set(millis);

	}

	@Override
	protected void onPreExecute() {

		dialog = ProgressDialog.show(mMainActivity, "","Please wait for data download ...");

		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mMainActivity);

		requestInitializer = new SpreadsheetAndroidRequestInitializer(settings, mMainActivity);
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Integer doInBackground(String... params) {
	
		try {

			if (params[0].equals("getAll")) {
				for (Table table : Table.values())

					Tools.getAll(dataHelper, requestInitializer, table);
				isSend = false;
			} else if (params[0].equals("sendAll")) {
				for (Table table : Table.values())
				//	Tools.sendAll(dataHelper, requestInitializer, table);
				isSend = true;

			}
		} catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			Log.e(TAG, errorMessage, e);
			return NO_ACCOUNT_ERROR;
		}

		return null;
	}

	

	@Override
	protected void onPostExecute(Integer result) {

		dialog.dismiss();
		Intent intent = new Intent();
		intent.setClass(mMainActivity, ProjectControllerActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		mMainActivity.startActivity(intent);
	}
}
