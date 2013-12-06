package com.malan.seeglitcontrol;

import org.json.JSONException;
import org.json.JSONObject;





import com.iubiquity.spreadsheets.model.DatabaseHelper;
import com.malan.seeglitcontrol.library.AsyncLoginAndRegistration;
import com.malan.seeglitcontrol.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity{
	
	private final int SPLASH_DISPLAY_LENGHT = 6000; // —”≥Ÿ¡˘√Î 
	
	
	
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_activity);
		
		
		DatabaseHelper.getInstance(this);
		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
				btnLogin.setOnClickListener(new View.OnClickListener() {

					public void onClick(View view) {
									
						UserFunctions userFunction = new UserFunctions();
						Log.d("Button", "Login");
						String email = inputEmail.getText().toString();
						String password = inputPassword.getText().toString();
				
						String[] params = {email, password};
				
						new AsyncLoginAndRegistration(loginErrorMsg, LoginActivity.this, "login").execute(params);
						
						
					}
				});

		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	
	
	  
//    @Override  
//    protected void onCreate(Bundle savedInstanceState) {  
//        super.onCreate(savedInstanceState);  
//        setContentView(R.layout.splash);  
//  
//        new Handler().postDelayed(new Runnable() {  
//            public void run() {  
//                Intent mainIntent = new Intent(LoginActivity.this,  ActivityDiscovery.class);  
//                LoginActivity.this.startActivity(mainIntent);  
//                LoginActivity.this.finish();  
//            }  
//  
//        }, SPLASH_DISPLAY_LENGHT);  
//  
//    }  

}
