package com.alstom.power.lean.views;

import java.util.ArrayList;

import com.alstom.power.lean.activities.StepActivity;
import com.alstom.power.lean.activities.TaskFragmentActivity;
import com.alstom.power.lean.managers.ChangeObserver;
import com.alstom.power.lean.managers.TaskListManager;
import com.alstom.power.lean.models.Task;
import com.alstom.power.lean.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class SelectTaskListCellView extends LinearLayout{
	
	private CheckBox cb_task;
	private TextView select_task_label;
	private Activity context;
	private ListView listSmallTask;
	private ArrayAdapter<String> listAdapter;
	

	public SelectTaskListCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.select_task_cell_view, this);
	    
	    this.context = (Activity) context;
	    
	    cb_task = (CheckBox)findViewById(R.id.cb_task);
	    select_task_label = (TextView)findViewById(R.id.select_task_label);
	    listSmallTask = (ListView)findViewById(R.id.list_small_task);
	    
    	
	    cb_task.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked){
					listSmallTask.setVisibility(View.VISIBLE);
				}else{
					listSmallTask.setVisibility(View.GONE);
				}
				
			}
		});	
	}
	
	
	public void setData(final Task task, final int position){
		
		select_task_label.setText(task.getName());
		
		listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, getData());

	    listSmallTask.setAdapter(listAdapter);
		
	}
	
	public ArrayList<String> getData(){
		
		ArrayList<String> listName = new ArrayList<String>();
		
		listName.add("small task 1");
		listName.add("small task 2");
		listName.add("small task 3");
		
		
		return listName;
	}
		

}
