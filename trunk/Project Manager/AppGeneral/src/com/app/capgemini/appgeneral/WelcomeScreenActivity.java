package com.app.capgemini.appgeneral;

import com.app.capgemini.appgeneral.models.DatabaseHelper;
import com.app.capgemini.appgeneral.spreadsheet.SynchronizationTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WelcomeScreenActivity extends Activity{

	
	private EditText edit_login;
	private EditText edit_pass;
	private Button btn_ok;
	private DatabaseHelper dataHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_welcom_screen);
		
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		edit_login = (EditText)findViewById(R.id.edit_login);
		edit_pass = (EditText)findViewById(R.id.edit_password);
		
		btn_ok = (Button)findViewById(R.id.btn_ok);
		
		
		dataHelper = DatabaseHelper.getInstance(this);
		btn_ok.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String login = edit_login.getText().toString();
				String passwd = edit_pass.getText().toString();
				
				System.out.println(login + passwd);
				
				new SynchronizationTask(WelcomeScreenActivity.this, dataHelper).execute("getAll");
			}
		});
	}
	

}
