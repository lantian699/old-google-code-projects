package com.alstom.lean.all.views;


import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MesureDetailActivity;
import com.alstom.lean.all.flashage.CaptureFlashageActivity;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.VisualInspection;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.view.View.OnClickListener;

public class TaskDetailCellView extends LinearLayout implements OnClickListener{


	public static final int REQUEST_CODE_FLASH = 0;

	public static final String TASK_NAME = "task";

	private Activity context;
	

	private EditText edit_part_num;
	
	private TextView description;
	private TextView unit;
	private TextView key;
	private TextView value;
	
	private String part_num;
	
	
	private ImageView btn_barcode;
	private ImageButton btn_photo;
	private Button btn_mesure;
	private LinearLayout ll_td_unit;

	private DatabaseHelper dataHelper;

	private List<Mesurement> listMesure;
	private String taskType;

	private Task task;

	public TaskDetailCellView(Context context, DatabaseHelper helper, TaskListManager manager) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.task_detail_cell_view, this);
	    
	    this.context = (Activity) context;
	    this.dataHelper = helper;
	    
	    description = (TextView)findViewById(R.id.tx_td_description);
	    key = (TextView)findViewById(R.id.edit_td_key);
	    edit_part_num = (EditText)findViewById(R.id.edit_td_part_num);
	    value = (TextView)findViewById(R.id.edit_td_value);
	    unit = (TextView)findViewById(R.id.edit_td_unit);
	    ll_td_unit = (LinearLayout)findViewById(R.id.ll_td_unit);
	    
	    btn_barcode = (ImageView)findViewById(R.id.btn_bar_code);
	    btn_photo = (ImageButton)findViewById(R.id.btn_take_photo);
	    btn_mesure = (Button)findViewById(R.id.btn_take_mesure);
	    
	    btn_barcode.setOnClickListener(this);
	    btn_photo.setOnClickListener(this);
	    btn_mesure.setOnClickListener(this);
	    
	    manager.registerBarcodeChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {
				edit_part_num.setText(res);
			}

			@Override
			public void onChange() {
			
			}
		});
	    
	}
	
	
	public void setData(Task task, int position){
		
		this.task = task;
		
		description.setText(task.getName());
		key.setText(task.getStatus());
		unit.setText(task.getType());
		value.setText(task.getBegin() + " - " +task.getEnd());
		
		if(task.getType().equals(TaskListCellView.TASK_TYPE_MESURE)){
			
			
			btn_mesure.setVisibility(View.VISIBLE);	
			taskType = TaskListCellView.TASK_TYPE_MESURE;
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_VI)){
			
			
			btn_mesure.setVisibility(View.GONE);
			
			taskType = TaskListCellView.TASK_TYPE_VI;
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_FINDING)){
			
		}
	
	}


	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_bar_code:
			
			Intent intent = new Intent();
			intent.setClass(context, CaptureFlashageActivity.class);
			context.startActivityForResult(intent, REQUEST_CODE_FLASH);
			
			break;
			
		case R.id.btn_take_photo:
			
			Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			context.startActivity(intent_photo);
			
			break;
			
		case R.id.btn_take_mesure:
			
			Intent intent_mesure = new Intent();
			intent_mesure.setClass(context, MesureDetailActivity.class);
			intent_mesure.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_mesure.putExtra(TASK_NAME, task);
			context.startActivity(intent_mesure);
	
			break;

		default:
			break;
		}
		
	}
}