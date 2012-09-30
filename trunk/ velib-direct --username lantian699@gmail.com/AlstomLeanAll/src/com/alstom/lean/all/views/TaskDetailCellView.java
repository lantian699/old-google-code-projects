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
import com.alstom.lean.all.flashage.CaptureFlashageActivity;
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


	private static final int REQUEST_CODE_FLASH = 0;

	private Activity context;
	
	private EditText edit_key;
	private EditText edit_value;
	private EditText edit_unit;
	private EditText edit_part_num;
	
	private TextView description;
	private TextView tx_unit;
	private String key;
	private String value;
	private String unit;
	private String part_num;
	
	
	private ImageView btn_barcode;
	private ImageButton btn_photo;
	private Button btn_mesure;

	private DatabaseHelper dataHelper;

	private List<Mesurement> listMesure;
	private String taskType;

	public TaskDetailCellView(Context context, DatabaseHelper helper) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.task_detail_cell_view, this);
	    
	    
	    this.dataHelper = helper;
	    
	    description = (TextView)findViewById(R.id.tx_td_description);
	    edit_key = (EditText)findViewById(R.id.edit_td_key);
	    edit_part_num = (EditText)findViewById(R.id.edit_td_part_num);
	    edit_value = (EditText)findViewById(R.id.edit_td_value);
	    edit_unit = (EditText)findViewById(R.id.edit_td_unit);
	    
	    tx_unit = (TextView)findViewById(R.id.title_td_unit);
	    
	    btn_barcode = (ImageView)findViewById(R.id.btn_bar_code);
	    btn_photo = (ImageButton)findViewById(R.id.btn_take_photo);
	    btn_mesure = (Button)findViewById(R.id.btn_take_mesure);
	    
	    btn_barcode.setOnClickListener(this);
	    btn_photo.setOnClickListener(this);
	    btn_mesure.setOnClickListener(this);
	    
	}
	
	
	public void setData(Task task, Mesurement mesurement, VisualInspection visualInspection, int position){
		
		if(task.getType().equals(TaskListCellView.TASK_TYPE_MESURE)){
			
			tx_unit.setVisibility(View.VISIBLE);
			edit_unit.setVisibility(View.VISIBLE);
			btn_mesure.setVisibility(View.VISIBLE);
			
			description.setText(mesurement.getDescription());
			
			taskType = TaskListCellView.TASK_TYPE_MESURE;
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_VI)){
			
			tx_unit.setVisibility(View.GONE);
			edit_unit.setVisibility(View.GONE);
			btn_mesure.setVisibility(View.GONE);
			
			taskType = TaskListCellView.TASK_TYPE_VI;
			
			description.setText(visualInspection.getDescription());
			
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
	
			break;

		default:
			break;
		}
		
	}
}