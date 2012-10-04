package com.alstom.lean.all.activities;



import java.sql.SQLException;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.InspectionTask;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.VisualInspection;
import com.alstom.lean.all.spreadsheet.SynchronizationTask;
import com.j256.ormlite.dao.Dao;

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
	private DatabaseHelper dataHelper;
	
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
				
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.okButton:
			
			Intent intent = new Intent();
			intent.setClass(getApplication(), ProjectListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);

			break;

		default:
			break;
		}
		
	}
	
	

}
