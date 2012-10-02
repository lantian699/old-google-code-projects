package com.alstom.lean.all.fragments;

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
import android.widget.Spinner;

import com.alstom.lean.all.R;
import com.alstom.lean.all.models.Task;
import com.google.common.collect.Lists;

public class AddFindingFragment extends Fragment implements OnClickListener{

	private static final String TASK_STATUS_NOT_STARTED = "not started";
	private static final String TASK_STATUS_IN_PROGRESS = "in progress";
	private static final String TASK_STATUS_COMPLETED = "completed";
	private static final String TASK_STATUS_CANCELED = "canceled";
	
	
	private static final String TASK_WP_TRUE = "true";
	private static final String TASK_WP_FALSE = "false";

	
	
	private Task task;
	private Button btn_create_finding;
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
	


	public AddFindingFragment(Task task){
		
		this.task = task;
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_add_finding, container, false);
		 
		  btn_create_finding = (Button)view.findViewById(R.id.finding_create);
		  btn_create_finding.setOnClickListener(this);
		  
		  edit_category = (EditText)view.findViewById(R.id.finding_edit_category);
		  edit_description = (EditText)view.findViewById(R.id.finding_edit_description);
		  edit_id = (EditText)view.findViewById(R.id.finding_edit_id);
		  edit_title = (EditText)view.findViewById(R.id.finding_edit_title);
		  edit_type = (EditText)view.findViewById(R.id.finding_edit_type);
		  
		  spinner_status = (Spinner)view.findViewById(R.id.finding_spinner_status);
		  spinner_wp = (Spinner)view.findViewById(R.id.finding_spinner_require);
		  
		  if(task != null){
		  edit_title.setText(task.getName());
		  edit_id.setText(task.getName());
		  edit_category.setText(task.getParentProject());
		  edit_type.setText(task.getType());
		  }
		  
		  listStatus = new ArrayList<String>();
		  
		  listStatus.add(TASK_STATUS_CANCELED);
		  listStatus.add(TASK_STATUS_COMPLETED);
		  listStatus.add(TASK_STATUS_IN_PROGRESS);
		  listStatus.add(TASK_STATUS_NOT_STARTED);
		  
		  adapter_status = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listStatus);
		  adapter_status.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		  spinner_status.setAdapter(adapter_status);
		  
		  
		  listWP = new ArrayList<String>();
		  
		  listWP.add(TASK_WP_FALSE);
		  listWP.add(TASK_WP_TRUE);

		  
		  adapter_wp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, listWP);
		  adapter_wp.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		  spinner_wp.setAdapter(adapter_wp);
		  
		return view;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.finding_create:
			
			
			
			break;

		default:
			break;
		}
		
	}
}
