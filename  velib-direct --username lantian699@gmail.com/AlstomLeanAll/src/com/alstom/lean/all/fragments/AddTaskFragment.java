package com.alstom.lean.all.fragments;

import java.sql.SQLException;
import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alstom.lean.all.R;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.views.TaskListCellView;
import com.google.common.collect.Lists;
import com.j256.ormlite.dao.Dao;

public class AddTaskFragment extends Fragment implements OnClickListener{

	private static final String TASK_STATUS_NOT_STARTED = "not started";
	private static final String TASK_STATUS_IN_PROGRESS = "in progress";
	private static final String TASK_STATUS_COMPLETED = "completed";
	private static final String TASK_STATUS_CANCELED = "canceled";
	
	
	private static final String TASK_WP_TRUE = "true";
	private static final String TASK_WP_FALSE = "false";

	
	
	private Button btn_create_finding;
	private Button btn_create_task;
	private EditText edit_title;
	private EditText edit_id;
	private EditText edit_type;
	private EditText edit_category;
	private EditText edit_description;
	private Spinner spinner_status;
	private ArrayList<String> listStatus;
	private ArrayAdapter<String> adapter_status;
	private ArrayList<String> listWP;
	private ArrayAdapter<String> adapter_wp;
	private Spinner spinner_wp;
	
	private EditText edit_name;
	private EditText edit_begin;
	private EditText edit_end;
	private EditText edit_object;

	private LinearLayout ll_add_task_view;
	private LinearLayout ll_add_finding_view;
	private String taskType;
	private DatabaseHelper dataHelper;
	private TaskListManager taskManager;
	private Dao<Task, ?> taskDao;

	public AddTaskFragment(String taskType,  DatabaseHelper helper, TaskListManager manager){
		
		this.taskType =taskType;
		this.dataHelper = helper;
		this.taskManager = manager;
		
		
		try {
			taskDao = dataHelper.getDao(Task.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_add_finding, container, false);
		 
		  
		  ll_add_finding_view = (LinearLayout)view.findViewById(R.id.add_finding_view);
		  ll_add_task_view = (LinearLayout)view.findViewById(R.id.add_task_view);
		  listStatus = new ArrayList<String>();
		  listWP = new ArrayList<String>();
		  
		  if(taskType.equals(TaskListFragment.TASK_TYPE_MESUREMENT) || taskType.equals(TaskListFragment.TASK_TYPE_VI)){
			  
			  ll_add_finding_view.setVisibility(view.GONE);
			  ll_add_task_view.setVisibility(view.VISIBLE);
			  
			  edit_name = (EditText)view.findViewById(R.id.task_edit_name);
			  edit_type = (EditText)view.findViewById(R.id.task_edit_type);
			  edit_begin = (EditText)view.findViewById(R.id.task_edit_begin);
			  edit_end = (EditText)view.findViewById(R.id.task_edit_end);
			  spinner_status = (Spinner)view.findViewById(R.id.task_spinner_status);
			  edit_object = (EditText)view.findViewById(R.id.task_edit_object);
			  btn_create_task = (Button)view.findViewById(R.id.task_create);
			  
			  btn_create_task.setOnClickListener(this);
			  
			  edit_type.setText(taskType);
			  
			  listStatus.clear();
			  listStatus.add(TASK_STATUS_NOT_STARTED);
			  listStatus.add(TASK_STATUS_CANCELED);
			  listStatus.add(TASK_STATUS_COMPLETED);
			  listStatus.add(TASK_STATUS_IN_PROGRESS);
			  
			  
			  adapter_status = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listStatus);
			  adapter_status.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			  spinner_status.setAdapter(adapter_status);
			 
			  
		  }else if(taskType.equals(TaskListFragment.TASK_TYPE_FINDING)){
			  
			  ll_add_finding_view.setVisibility(view.VISIBLE);
			  ll_add_task_view.setVisibility(view.GONE);
			  
			  btn_create_finding = (Button)view.findViewById(R.id.finding_create);
			  btn_create_finding.setOnClickListener(this);
			  
			  edit_type = (EditText)view.findViewById(R.id.finding_edit_type);
			  edit_category = (EditText)view.findViewById(R.id.finding_edit_category);
			  edit_description = (EditText)view.findViewById(R.id.finding_edit_description);
			  edit_id = (EditText)view.findViewById(R.id.finding_edit_id);
			  edit_title = (EditText)view.findViewById(R.id.finding_edit_title);
			  
			  
			  spinner_status = (Spinner)view.findViewById(R.id.finding_spinner_status);
			  spinner_wp = (Spinner)view.findViewById(R.id.finding_spinner_require);
			  
			  listStatus.clear();
			  listStatus.add("OPEN");
			  listStatus.add("CLOSED");
			  
			  
			  adapter_status = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listStatus);
			  adapter_status.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			  spinner_status.setAdapter(adapter_status);
			  
			  
			  listWP.clear();
			  listWP.add(TASK_WP_FALSE);
			  listWP.add(TASK_WP_TRUE);

			  
			  adapter_wp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listWP);
			  adapter_wp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			  spinner_wp.setAdapter(adapter_wp);
			  
			  edit_type.setText(TaskListCellView.TASK_TYPE_FINDING);
			  edit_category.setText("Level 1");
		  }
		 
		  
		 
		  
		 
		  
		  
		  
		  
		  
		  
		  
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.finding_create:
			try {
				Task task_finding = new Task();
				task_finding.setName(edit_title.getText().toString());
				task_finding.setBegin("2012/12/12");
				task_finding.setEnd("2012/12/13");
				task_finding.setType("finding");
				taskDao.create(task_finding);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			taskManager.notifyRefreshListChange("");
			
			
			break;

		case R.id.task_create:
			
			try {
				Task task_add = new Task();
				task_add.setName(edit_name.getText().toString());
				task_add.setBegin(edit_begin.getText().toString());
				task_add.setEnd(edit_end.getText().toString());
				task_add.setType(edit_type.getText().toString());
				taskDao.create(task_add);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			taskManager.notifyRefreshListChange("");
			break;

		default:
			break;
		}
		
	}
}
