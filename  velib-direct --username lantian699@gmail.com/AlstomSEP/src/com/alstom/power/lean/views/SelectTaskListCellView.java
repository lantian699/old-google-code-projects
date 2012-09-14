package com.alstom.power.lean.views;

import com.alstom.power.lean.activities.StepActivity;
import com.alstom.power.lean.activities.TaskFragmentActivity;
import com.alstom.power.lean.managers.ChangeObserver;
import com.alstom.power.lean.managers.TaskListManager;
import com.alstom.power.lean.models.Task;
import com.alstom.power.sep.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SelectTaskListCellView extends LinearLayout{
	
	private CheckBox cb_task;
	private TextView select_task_label;
	private Activity context;
	

	public SelectTaskListCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.select_task_cell_view, this);
	    
	    this.context = (Activity) context;
	    
	    cb_task = (CheckBox)findViewById(R.id.cb_task);
	    select_task_label = (TextView)findViewById(R.id.select_task_label);
	    
	    
	}
	
	
	public void setData(final Task task, final int position){
		
		select_task_label.setText(task.getName());
		
		
	}
	
		

}
