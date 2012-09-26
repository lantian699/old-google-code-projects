package com.alstom.lean.all.activities;



import com.alstom.lean.all.R;
import com.alstom.lean.all.spreadsheet.SynchronizationTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {
	
	private EditText login_edit;
	private EditText password_edit;
	private String login;
	private String password;
	private Button btn_ok;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.activity_login);
		
		
		login_edit = (EditText) findViewById(R.id.loginEditText);
		password_edit = (EditText) findViewById(R.id.mdpEditText);
		btn_ok = (Button) findViewById(R.id.okButton);
		
		btn_ok.setOnClickListener(this);
		PreferenceManager.setDefaultValues(this, R.xml.settings, false);
		new SynchronizationTask(this).execute();
		
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.okButton:
			
			Intent intent = new Intent();
			intent.setClass(getApplication(), MyProjectModeTabletActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			break;

		default:
			break;
		}
		
	}
	
	

}
