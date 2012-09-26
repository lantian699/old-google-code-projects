package com.alstom.lean.all.spreadsheet;


import java.io.IOException;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.api.client.extensions.android2.AndroidHttp;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.services.spreadsheet.SpreadsheetRequestInitializer;

/**
 * Class that handles requests to connect to the spreadsheet
 * 
 * @author Thibault Pouget
 * 
 */
public class SpreadsheetAndroidRequestInitializer extends SpreadsheetRequestInitializer {
	private static final String TAG = "SpreadsheetAndroidRequestInitializer";

	private static final String AUTH_TOKEN_TYPE = "wise"; // spreadsheet service
	private static final int REQUEST_AUTHENTICATE = 0;

	final static HttpTransport transport = AndroidHttp.newCompatibleTransport();
	public SharedPreferences settings;

	// google account you must log in to on your phone
	private String accountName;
	GoogleAccountManager accountManager;

	String authToken;

	private Activity mainActivity;

	static final String PREF_ACCOUNT_NAME = "accountName"; // same as in settings.xml
	static final String PREF_AUTH_TOKEN = "authToken";
	static final String PREF_GSESSIONID = "gsessionid";

	public SpreadsheetAndroidRequestInitializer(SharedPreferences settings,
			Activity mainActivity) {
		super(transport);
		this.settings = settings;
		this.mainActivity = mainActivity;
		this.accountManager = new GoogleAccountManager(mainActivity);
		
		accountName = settings.getString(PREF_ACCOUNT_NAME, null);	
		
		System.out.println("account name = "+accountName);
		
		Account account = accountManager.getAccountByName(accountName);
		accountManager.manager.getAuthToken(account, AUTH_TOKEN_TYPE, true,
				new AccountManagerCallback<Bundle>() {
					public void run(AccountManagerFuture<Bundle> future) {
						try {
							Bundle bundle = future.getResult();
							if (bundle.containsKey(AccountManager.KEY_INTENT)) {
								Intent intent = bundle
										.getParcelable(AccountManager.KEY_INTENT);
								int flags = intent.getFlags();
								flags &= ~Intent.FLAG_ACTIVITY_NEW_TASK;
								intent.setFlags(flags);
								SpreadsheetAndroidRequestInitializer.this.mainActivity
										.startActivityForResult(intent,
												REQUEST_AUTHENTICATE);
							} else if (bundle
									.containsKey(AccountManager.KEY_AUTHTOKEN)) {
								setAuthToken(bundle
										.getString(AccountManager.KEY_AUTHTOKEN));
							}
						} catch (Exception e) {
							handleException(e);
						}
					}
				}, null);
		authToken = settings.getString(PREF_AUTH_TOKEN, null);
		setGsessionid(settings.getString(PREF_GSESSIONID, null));
	}

	void setAuthToken(String authToken) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_AUTH_TOKEN, authToken);
		editor.commit();
		this.authToken = authToken;
	}

	@Override
	public void intercept(HttpRequest request) throws IOException {
		super.intercept(request);
		request.getHeaders().setAuthorization(
				GoogleHeaders.getGoogleLoginValue(authToken));
	}

	@Override
	public boolean handleResponse(HttpRequest request, HttpResponse response,
			boolean retrySupported) throws IOException {
		switch (response.getStatusCode()) {
		case 302:
			super.handleResponse(request, response, retrySupported);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(PREF_GSESSIONID, getGsessionid());
			editor.commit();
			return true;
		case 401:
			accountManager.invalidateAuthToken(authToken);
			authToken = null;
			SharedPreferences.Editor editor2 = settings.edit();
			editor2.remove(PREF_AUTH_TOKEN);
			editor2.commit();
			return false;
		}
		return false;
	}

	void handleException(Exception e) {
		e.printStackTrace();
		if (e instanceof HttpResponseException) {
			HttpResponse response = ((HttpResponseException) e).getResponse();
			int statusCode = response.getStatusCode();
			try {
				response.ignore();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			// TODO(yanivi): should only try this once to avoid infinite loop
			if (statusCode == 401) {
				gotAccount();
				return;
			}
			try {
				Log.e(TAG, response.parseAsString());
			} catch (IOException parseException) {
				parseException.printStackTrace();
			}
		}
		Log.e(TAG, e.getMessage(), e);
	}

	private void gotAccount() {
		Account account = accountManager.getAccountByName(accountName);
		if (account != null) {
			// handle invalid token
			if (this.authToken == null) {
				accountManager.manager.getAuthToken(account, AUTH_TOKEN_TYPE,
						true, new AccountManagerCallback<Bundle>() {
							public void run(AccountManagerFuture<Bundle> future) {
								try {
									Bundle bundle = future.getResult();
									if (bundle
											.containsKey(AccountManager.KEY_INTENT)) {
										Intent intent = bundle
												.getParcelable(AccountManager.KEY_INTENT);
										int flags = intent.getFlags();
										flags &= ~Intent.FLAG_ACTIVITY_NEW_TASK;
										intent.setFlags(flags);
										mainActivity.startActivityForResult(
												intent, REQUEST_AUTHENTICATE);
									} else if (bundle
											.containsKey(AccountManager.KEY_AUTHTOKEN)) {
										setAuthToken(bundle
												.getString(AccountManager.KEY_AUTHTOKEN));
									}
								} catch (Exception e) {
									handleException(e);
								}
							}
						}, null);
			}
			return;
		}
		chooseAccount();
	}

	private void chooseAccount() {
		accountManager.manager.getAuthTokenByFeatures(
				GoogleAccountManager.ACCOUNT_TYPE, AUTH_TOKEN_TYPE, null,
				mainActivity, null, null, new AccountManagerCallback<Bundle>() {
					public void run(AccountManagerFuture<Bundle> future) {
						Bundle bundle;
						try {
							bundle = future.getResult();
							setAccountName(bundle
									.getString(AccountManager.KEY_ACCOUNT_NAME));
							setAuthToken(bundle
									.getString(AccountManager.KEY_AUTHTOKEN));
						} catch (OperationCanceledException e) {
							// user canceled
						} catch (AuthenticatorException e) {
							handleException(e);
						} catch (IOException e) {
							handleException(e);
						}
					}
				}, null);
	}

	private void setAccountName(String accountName) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(PREF_ACCOUNT_NAME, accountName);
		editor.remove(PREF_GSESSIONID);
		editor.commit();
		this.accountName = accountName;
		this.setGsessionid(null);
	}
}
