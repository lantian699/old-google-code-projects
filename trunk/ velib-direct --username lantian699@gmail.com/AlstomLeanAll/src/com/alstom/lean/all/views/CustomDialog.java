package com.alstom.lean.all.views;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.alstom.lean.all.R;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Task;
import com.j256.ormlite.dao.Dao;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CustomDialog extends Dialog {

	private EditText edit_description;
	private EditText edit_unit;
	private EditText edit_low;
	private EditText edit_high;
	private Spinner spinner_type;
	private Button buttonYes;
	private String description;
	private String unit;
	private String low;
	private String high;
	private DatabaseHelper dataHelper;
	private Task task;
	private ArrayAdapter<String> adapter_type;
	private ArrayList<String> listType;
	private Context context;
	private String type;
	private TaskListManager taskManger;
	
	
	public CustomDialog(Context context, DatabaseHelper dataHelper, Task task, TaskListManager  manager) {
		super(context);
		this.context = context;
		this.dataHelper = dataHelper;
		this.task = task;
		this.taskManger = manager;
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.custom_dialog);
		setTitle("Add measurement to Task "+task.getName());
		
		edit_description = (EditText) findViewById(R.id.edit_sub_mesure_des);
		edit_low = (EditText) findViewById(R.id.edit_sub_mesure_low);
		edit_high = (EditText)findViewById(R.id.edit_sub_mesure_high);
		edit_unit = (EditText) findViewById(R.id.edit_sub_mesure_unit);
	
		listType = new ArrayList<String>();
		listType.add("measure number");
		listType.add("measure attachment");
		
		spinner_type = (Spinner) findViewById(R.id.spinner_sub_mesure_type);
		adapter_type = new ArrayAdapter<String>(context,
				android.R.layout.simple_expandable_list_item_1, listType);
		adapter_type
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner_type.setAdapter(adapter_type);
		
		
		spinner_type.setOnItemSelectedListener(new OnItemSelectedListener() {

			

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				
				if(position == 0){
					type = "MN";
				}else if(position == 1){
					type = "MA";
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
		});
		
		buttonYes = (Button) findViewById(R.id.button_yes);
		buttonYes.setOnClickListener(new Button.OnClickListener() {

			private Dao<Mesurement, ?> mesureDao;

			public void onClick(View v) {
				// TODO Auto-generated method stub

				description = edit_description.getText().toString();
				unit = edit_unit.getText().toString();
				low = edit_low.getText().toString();
				high = edit_high.getText().toString();

				if ((description != "") || (unit != "") ||(low != "")
						|| (high != "")) {

					Mesurement mesure = new Mesurement();
					mesure.setDescription(description);
					mesure.setUnit(unit);
					mesure.setLow(low);
					mesure.setHigh(high);
					mesure.setRule(low + "<Value<" + high);
					mesure.setType(type);
					mesure.setParent(task.getName());
					
					try {
						mesureDao = dataHelper.getDao(Mesurement.class);
						mesureDao.create(mesure);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					Toast.makeText(context, "Please fill out all the information", Toast.LENGTH_SHORT).show();
				}
				
				dismiss();
				
				taskManger.notifyAddMesureChange("");

			}
		});

		Button buttonNo = (Button) findViewById(R.id.button_no);
		buttonNo.setSingleLine(true);
		buttonNo.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();

			}
		});
	}

	// called when this dialog is dismissed
	protected void onStop() {
		Log.d("TAG", "+++++++++++++++++++++++++++");
	}

}