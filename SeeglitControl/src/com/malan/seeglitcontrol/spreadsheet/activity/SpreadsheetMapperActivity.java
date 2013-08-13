package com.malan.seeglitcontrol.spreadsheet.activity;

import java.util.List;
import java.util.Map;

import org.apache.http.client.HttpResponseException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.google.api.client.http.HttpStatusCodes;
import com.iubiquity.spreadsheets.model.Feed;
import com.iubiquity.spreadsheets.model.ListEntry;
import com.iubiquity.spreadsheets.model.ListFeed;
import com.iubiquity.spreadsheets.model.SpreadsheetEntry;
import com.iubiquity.spreadsheets.model.SpreadsheetFeed;
import com.iubiquity.spreadsheets.model.WorksheetEntry;
import com.iubiquity.spreadsheets.model.WorksheetFeed;
import com.malan.seeglitcontrol.R;
import com.malan.seeglitcontrol.spreadsheet.client.AndroidSpreadsheetClient;
import com.malan.seeglitcontrol.spreadsheet.client.AsyncSpreadsheetCaller;

public class SpreadsheetMapperActivity extends Activity implements
		AsyncSpreadsheetCaller {

	private static final String TAG = "SpreadsheetMapper";
	private static final int GET_OAUTH = 0;
	private static final int REQUEST_CODE_SPREADSHEET_FEED = 0;
	private static final int REQUEST_CODE_WORKSHEET_FEED = 1;
	private static final int REQUEST_CODE_LIST_FEED = 2;
	private static final String PREFS_KEY_AUTH_TOKEN = "Token";
	private AndroidSpreadsheetClient client;
	// private AbstractDatabaseHelper dbHelper;
	private String deviceId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		deviceId = telephonyManager.getDeviceId();

		// Using SharedPreferences to store authToken
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(this);
		final String authToken = prefs.getString(PREFS_KEY_AUTH_TOKEN, "");

		if (authToken.equals("")) {
			// If no authtoken exists, go get one
			final Intent intent = new Intent(this, OAuthActivity.class);
			startActivityForResult(intent, GET_OAUTH);
		} else {
			// otherwise just get the spreadsheets for that authtoken
			getSpreadSheets(authToken);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// Activity 'OAuthActivity' returns the authtoek
		if (resultCode == Activity.RESULT_OK) {
			final String authToken = data.getStringExtra(PREFS_KEY_AUTH_TOKEN);
			final SharedPreferences prefs = PreferenceManager
					.getDefaultSharedPreferences(this);
			prefs.edit().putString(PREFS_KEY_AUTH_TOKEN, authToken).commit();
			getSpreadSheets(authToken);
		}
	}

	private void getSpreadSheets(String authToken) {
		// create the AndroidSpreadsheetClient object
		client = new AndroidSpreadsheetClient(authToken, this);
		client.createClient(this, "seeglit-control-1");
		client.getSpreadsheetMetafeed(REQUEST_CODE_SPREADSHEET_FEED);
	}

	/**
	 * The client then calls the following callback methods based on events
	 */
	private void onSpreadFeedResult(final SpreadsheetFeed spreadSheetFeed) {

		List<SpreadsheetEntry> entries = spreadSheetFeed.getEntries();
		SpreadsheetEntry entry = null;

		boolean found = false;
		for (SpreadsheetEntry e : entries) {
			if (e.title.equals(deviceId)) {
				entry = e;
				found = true;
			}
		}

		if (!found) {
			Log.i(TAG, "Spreadsheet with device name \"" + deviceId
					+ "\" not found");
		}

		client.executeAsyncGetFeed(REQUEST_CODE_WORKSHEET_FEED,
				entry.getWorksheetFeedLink(), WorksheetFeed.class);
	}

	private void onWorksheetFeedResult(final WorksheetFeed worksheetFeed) {

		List<WorksheetEntry> worksheetentries = worksheetFeed.getEntries();

		for (WorksheetEntry worksheetEntry : worksheetentries) {
			client.executeAsyncGetFeed(REQUEST_CODE_LIST_FEED,
					worksheetEntry.getListFeedLink(), ListFeed.class);
		}

		WorksheetEntry worksheetEntry = worksheetentries.get(0);
	}

	private void onListFeedResult(ListFeed listFeed) {

		// final String deviceId = getIntent().getStringExtra(KEY_DEVICE_ID);
		final String registersGroup = listFeed.title.trim();

		for (ListEntry listEntry : listFeed.getEntries()) {
			// do something with the list entries
			Map<String, String> content = listEntry.getColumns();
			System.out.println("list = " + content.get("qds"));

		}

		 finish();
	}

	public void onSpreadsheetResult(int requestCode, Feed feed) {
		switch (requestCode) {
		case REQUEST_CODE_SPREADSHEET_FEED:
			onSpreadFeedResult((SpreadsheetFeed) feed);
			break;

		case REQUEST_CODE_WORKSHEET_FEED:
			onWorksheetFeedResult((WorksheetFeed) feed);
			break;

		case REQUEST_CODE_LIST_FEED:
			onListFeedResult((ListFeed) feed);
			break;
		}

	}

	public void onExceptionResponse(Exception e) {
		Log.e(TAG, e.getMessage(), e);

		if (HttpResponseException.class.isInstance(e)) {
			HttpResponseException exception = (HttpResponseException) e;

			if (exception.getStatusCode() == HttpStatusCodes.STATUS_CODE_UNAUTHORIZED) {
				final Intent intent = new Intent(
						SpreadsheetMapperActivity.this, OAuthActivity.class);
				startActivityForResult(intent, GET_OAUTH);
			}
		}
	}
}
