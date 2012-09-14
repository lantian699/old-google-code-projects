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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskListCellView extends LinearLayout{
	
	public static final int REQUEST_CODE_STEP_LIST = 3;
	private static final int TASK_WAIT_FOR_PROCESS = 0;
	private static final int TASK_IN_PROCESSING = 1;
	private static final int TASK_PROCESSED = 2;
	private Activity context;
	private TextView stepDate;
	private TextView stepLabel;
	private ImageView validate;
	private ImageView mandatory;
	private ImageView openActivity;
	public TaskListManager taskListManager;
	private RelativeLayout stepRow;
	private int position;
	private Task task;
	

	public TaskListCellView(Context context, TaskListManager taskListManager) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.task_list_cell_view, this);
	    
	    this.context = (Activity) context;
	    this.taskListManager = taskListManager;
	    
	    
	    stepDate = (TextView)findViewById(R.id.etapeDate);
	    stepLabel = (TextView)findViewById(R.id.etape);
	    validate = (ImageView)findViewById(R.id.valider);
	    mandatory = (ImageView)findViewById(R.id.star);
	    openActivity = (ImageView)findViewById(R.id.ic_next);
	    stepRow = (RelativeLayout)findViewById(R.id.etapeRow);
	    
	    
	    
	}
	
	
	public void setData(final Task task, final int position){
		
		this.task = task;
		this.position = position;
		stepDate.setText(task.getDate());
		stepLabel.setText(task.getLabel());
		
		
		if(TaskFragmentActivity.getPosTerminate() == position){
			
			refreshStepColumn(TASK_IN_PROCESSING);

			
		}else{
			refreshStepColumn(TASK_WAIT_FOR_PROCESS);
		}
		
		if(task.isMandatory()){
			mandatory.setVisibility(View.VISIBLE);
		}else{
			mandatory.setVisibility(View.INVISIBLE);
		}
		
		
		
		taskListManager.registerChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange() {
	
				if(position == TaskFragmentActivity.getPosTerminate()-1){
					
					refreshStepColumn(TASK_PROCESSED);
					
				}	
				
				if(position == TaskFragmentActivity.getPosTerminate() ||
						TaskFragmentActivity.getPosTerminate() + 1 == position){
					refreshStepColumn(TASK_IN_PROCESSING);
				}
		
				
			}
		});
		
	}
	
	public void refreshStepColumn(int processeLevel){
		
		switch (processeLevel) {
		case TASK_WAIT_FOR_PROCESS:
			
			task.setValidated(false);
			validate.setVisibility(View.INVISIBLE);		
			openActivity.setVisibility(View.INVISIBLE);
			
			stepDate.setTextColor(Color.rgb(206, 195, 198));
			stepLabel.setTextColor(Color.rgb(206, 195, 198));
			stepRow.setClickable(false);
			
			
			break;
		case TASK_IN_PROCESSING:
			
			task.setValidated(false);
			validate.setVisibility(View.INVISIBLE);		
			openActivity.setVisibility(View.VISIBLE);
			
			stepDate.setTextColor(Color.BLACK);
			stepLabel.setTextColor(Color.BLACK);
			stepRow.setClickable(true);
			
			stepRow.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {

					Intent intent = new Intent();
					intent.setClass(context, StepActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivityForResult(intent, REQUEST_CODE_STEP_LIST);
					
				}
			});
			
			break;
		case TASK_PROCESSED:
			
			task.setValidated(true);
			validate.setVisibility(View.VISIBLE);		
			openActivity.setVisibility(View.INVISIBLE);
			
			stepDate.setTextColor(Color.rgb(206, 195, 198));
			stepLabel.setTextColor(Color.rgb(206, 195, 198));
			stepRow.setClickable(false);
	
			break;

		default:
			break;
		}
		
	}
	
		

}
