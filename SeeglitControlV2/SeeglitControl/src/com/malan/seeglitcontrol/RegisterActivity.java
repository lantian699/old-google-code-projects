/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.malan.seeglitcontrol;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import com.malan.seeglitcontrol.library.AsyncLoginAndRegistration;
import com.malan.seeglitcontrol.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;
	private Spinner spinnerSociety;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private String scSelected;
	public static ArrayList<HashMap<String, String>> listSid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		setContentView(R.layout.register);
		
		

		// Importing all assets like buttons, text fields
		inputFullName = (EditText) findViewById(R.id.registerName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		spinnerSociety = (Spinner)findViewById(R.id.societyList);
		
		
		
		new AsyncLoginAndRegistration(null, this, "society", spinnerSociety).execute();
		
		spinnerSociety.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, 
		            int pos, long id) {
				// TODO Auto-generated method stub
				
				scSelected = (String)parent.getAdapter().getItem(pos);
				ActivityDiscovery.societyName = scSelected;
				scSelected = listSid.get(pos).get(scSelected);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		
			
		});
		
		
		// Register Button Click event
				btnRegister.setOnClickListener(new View.OnClickListener() {			
					public void onClick(View view) {
						String name = inputFullName.getText().toString();
						String email = inputEmail.getText().toString();
						String password = inputPassword.getText().toString();
						System.out.println(scSelected);
						String[] param = {name, email, password,scSelected};
						new AsyncLoginAndRegistration( registerErrorMsg, RegisterActivity.this, "register", null).execute(param);
						
						
						
					}
				});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});
	}
}
