package com.example.androidhive;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.androidhive.library.DatabaseHandler;
import com.example.androidhive.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AsyncLoginAndRegistration extends AsyncTask<String, String, JSONObject>{

	
	// JSON Response node names
		private static String KEY_SUCCESS = "success";
		private static String KEY_ERROR = "error";
		private static String KEY_ERROR_MSG = "error_msg";
		private static String KEY_UID = "uid";
		private static String KEY_NAME = "name";
		private static String KEY_EMAIL = "email";
		private static String KEY_CREATED_AT = "created_at";

		private TextView errorMsgTextView;
		private Context context;
		private UserFunctions userFunction;
		private ProgressDialog dialog;
		private String tag;
		
	
	public AsyncLoginAndRegistration(TextView errorMsgTextView, Context context, String tag ){
			
		this.context = context;
		this.errorMsgTextView = errorMsgTextView;
		this.tag = tag;
		}
	
	
	@Override
	protected void onPreExecute() {
	// TODO Auto-generated method stub
	super.onPreExecute();
	
		if(tag == "register"){
			dialog = ProgressDialog.show(context, "", "Registration in progress ...");
		}else if(tag == "login"){
			dialog = ProgressDialog.show(context, "", "Login in progress ...");
		}
	}
	
	@Override
	protected JSONObject doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		
		
		userFunction = new UserFunctions();
		
		if(tag == "register"){
			String name = arg0[0];
			String email = arg0[1];
			String password = arg0[2];
			
			JSONObject json = userFunction.registerUser(name, email, password);
			return json;
		}else if(tag == "login"){
			
			String email = arg0[0];
			String password = arg0[1];

			JSONObject json = userFunction.loginUser(email, password);
			return json;
		}
		
		return null;
	}
	
	
	@Override
	protected void onPostExecute(JSONObject json) {
		
		super.onPostExecute(json);
		
		dialog.dismiss();
		
		if(tag == "register"){
		// check for login response
		try {
			if (json.getString(KEY_SUCCESS) != null) {
				errorMsgTextView.setText("");
				String res = json.getString(KEY_SUCCESS); 
				if(Integer.parseInt(res) == 1){
					// user successfully registred
					// Store user details in SQLite Database
					DatabaseHandler db = new DatabaseHandler(context);
					JSONObject json_user = json.getJSONObject("user");
					
					// Clear all previous data in database
					userFunction.logoutUser(context);
					db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
					// Launch Dashboard Screen
					Intent dashboard = new Intent(context, DashboardActivity.class);
					// Close all views before launching Dashboard
					dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivity(dashboard);
					// Close Registration Screen
					((Activity) context).finish();
				}else{
					// Error in registration
					errorMsgTextView.setText("Error occured in registration");
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		}else if(tag == "login"){
			
			

					// check for login response
					try {
						if (json.getString(KEY_SUCCESS) != null) {
							errorMsgTextView.setText("");
							String res = json.getString(KEY_SUCCESS); 
							if(Integer.parseInt(res) == 1){
								// user successfully logged in
								// Store user details in SQLite Database
								DatabaseHandler db = new DatabaseHandler(context);
								JSONObject json_user = json.getJSONObject("user");
								
								// Clear all previous data in database
								userFunction.logoutUser(context);
								db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
								
								// Launch Dashboard Screen
								Intent dashboard = new Intent(context, DashboardActivity.class);
								
								// Close all views before launching Dashboard
								dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								context.startActivity(dashboard);
								
								// Close Login Screen
								((Activity) context).finish();
							}else{
								// Error in login
								errorMsgTextView.setText("Incorrect username/password");
							}
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				
			
			
			
		}
		
	}
		
	

}
