package com.alstom.lean.all.spreadsheet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alstom.lean.all.models.Block;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Plant;
import com.alstom.lean.all.models.Unit;
import com.alstom.lean.all.spreadsheet.Worksheet.Table;
import com.alstom.lean.all.tools.Tools;

import com.google.api.services.spreadsheet.client.SpreadsheetClient;
import com.google.api.services.spreadsheet.url.ListUrl;
import com.j256.ormlite.dao.Dao;



import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.TextView;


/**
 * AsyncTask to synchronize the local DB with the spreadsheet
 * @author guillaumedavo
 *
 */
public class SynchronizationTask extends AsyncTask<String, Integer, Integer> {
	
	private static final String TAG = "SynchronizationTask";
	private Activity mMainActivity;
	
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
	
	public SynchronizationTask(Activity mainActivity, DatabaseHelper dataHelper) {
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
		
		dialog = ProgressDialog.show(mMainActivity, "", "Please wait for data download ...");
		
	
	}

	@Override
	protected Integer doInBackground(String... params) {
		
		// create the SpreadSheetDb
//		SharedPreferences settings = mMainActivity.getSharedPreferences(TAG, Activity.MODE_PRIVATE);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mMainActivity);
		SpreadsheetAndroidRequestInitializer requestInitializer;
		try {
			requestInitializer = new SpreadsheetAndroidRequestInitializer(settings, mMainActivity);
			
			Thread.sleep(100);
			
			if(params[0].equals("getAll")){
			for (Table table:Table.values()) 
			
				Tools.getAll(dataHelper,requestInitializer, table);
			}else if(params[0].equals("sendAll")){
				Tools.sendAll(dataHelper, requestInitializer);
			}
		}
		catch (IllegalArgumentException e) {
			errorMessage = e.getMessage();
			Log.e(TAG, errorMessage, e);
			return NO_ACCOUNT_ERROR;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		

		return null;
	}
	
	
	


/*	private void synchronizeRow(SpreadSheetDb spreadSheetDb, Table t, ContentValues values) {
		
		// to avoid duplicates, try to see if there is already such a row in the local DB
		String backOfficeId = values.getAsString(DatedColumns.KEY_SIEBEL_ID);
		final String[] projection = {DatedColumns._ID, DatedColumns.KEY_SIEBEL_ID, DatedColumns._UPDATED};
		final String selection = DatedColumns.KEY_SIEBEL_ID + "=?";
		final Uri baseUri;
		switch (t) {
		case Customers:
			baseUri = CustomersColumns.CONTENT_URI;
			break;
		case Activities:
			baseUri = ActivitiesColumns.CONTENT_URI;
			break;
		case ServiceRequests:
			baseUri = ServiceRequestsColumns.CONTENT_URI;
			break;
		default:
			baseUri = null;
		}
		
		Cursor c = mMainActivity.getContentResolver().query(baseUri, projection, selection, new String[] {backOfficeId}, null);
		storeColumnIndices(c);
		
		// insert a new object if none is found in the DB
		if (c.getCount() == 0) {
			// remove columns that are not in the local DB
			values.remove(ActivitiesStruct.KEY_SPREADSHEET_LAST_UPDATE_DATE);
			mMainActivity.getContentResolver().insert(baseUri, values);
		}
		
		// else update the oldest
		else {
			c.moveToFirst();
			Time localTime = new Time();
			localTime.set(c.getLong(mUpdatedIndex));
			Time backofficeTime = new Time();
			backofficeTime.parse3339(values.getAsString(ActivitiesStruct.KEY_SPREADSHEET_LAST_UPDATE_DATE));
			
			// update only if the last update was more recent than lastSyncTime
			if ( (Time.compare(lastSyncTime, backofficeTime)>=0) && (Time.compare(lastSyncTime, localTime)>=0) ) {
				c.close();
				return;
			}
			
			Uri localUri = ContentUris.withAppendedId(baseUri, c.getLong(mIdIndex));
			
			if (Time.compare(backofficeTime, localTime)>0) {
				// BO was updated more recently: update local
				// remove columns that are not in the local DB
				values.remove(ActivitiesStruct.KEY_SPREADSHEET_LAST_UPDATE_DATE);
				mMainActivity.getContentResolver().update(localUri, values, null, null);
			}
			else {
				// Local DB was updated more recently: update BO
				// query all the columns
				c.close();
				c = mMainActivity.getContentResolver().query(localUri, null, null, null, null);
				c.moveToFirst();
				ContentValues localValues = getAllValues(c);
				try {
					spreadSheetDb.update(Table.Customers, localValues);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// close the cursor
		c.close();
	}

	*//**
	 * optimize by storing column indices
	 * @param c
	 *//*
	private void storeColumnIndices(Cursor c) {
		if (mIdIndex == null) {
			mIdIndex = c.getColumnIndexOrThrow(DatedColumns._ID);
		}
		if (mUpdatedIndex == null) {
			mUpdatedIndex = c.getColumnIndexOrThrow(DatedColumns._UPDATED);
		}
		
	}

	private ContentValues getAllValues(Cursor c) {
		ContentValues values = new ContentValues(c.getColumnCount());
		for (int i=0; i<c.getColumnCount(); i++) {
			String s = c.getString(i);
			if (s != null)
				values.put(c.getColumnName(i), s);
			else
				values.putNull(c.getColumnName(i));
		}
		return values;
	}*/


	
	@Override
	protected void onPostExecute(Integer result) {
		
		dialog.dismiss();	
	}
}
