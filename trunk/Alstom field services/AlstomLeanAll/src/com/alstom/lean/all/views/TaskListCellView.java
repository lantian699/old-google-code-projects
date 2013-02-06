package com.alstom.lean.all.views;



import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;

import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Task;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskListCellView extends LinearLayout{
	
/*	public static final int REQUEST_CODE_STEP_LIST = 3;
	private static final int TASK_WAIT_FOR_PROCESS = 0;
	private static final int TASK_IN_PROCESSING = 1;
	private static final int TASK_PROCESSED = 2;*/
	public static final String TASK_TYPE_VI = "vi";
	public static final String TASK_TYPE_MESURE = "mesure";
	public static final String TASK_TYPE_FINDING = "finding";
	private Activity context;
	private TextView startDate;
	private TextView endDate;
	private TextView taskLabel;
	private ImageView imgTaskType;
	private ImageView flash;
	public TaskListManager taskListManager;
	

	public TaskListCellView(Context context, TaskListManager taskListManager) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.task_list_cell_view_mod, this);
	    
	    this.context = (Activity) context;
	    this.taskListManager = taskListManager;
	    
	    startDate = (TextView) findViewById(R.id.tx_task_start_date);
	    endDate = (TextView)findViewById(R.id.tx_task_end_date);
	    taskLabel = (TextView)findViewById(R.id.tx_task_name);
	    imgTaskType = (ImageView)findViewById(R.id.img_type_task);
	    flash = (ImageView)findViewById(R.id.flash);
	    flash.setVisibility(View.INVISIBLE);
	    
	}
	
	
	public void setData(Task task, int position){
		
		startDate.setText(task.getBegin());
		endDate.setText(task.getEnd());
		taskLabel.setText(task.getName());
		
		
		
		if(task.getType().equals(TASK_TYPE_VI)){
			
			imgTaskType.setImageResource(R.drawable.icon_task_vi);
		}
		else if(task.getType().equals(TASK_TYPE_MESURE)){
			imgTaskType.setImageResource(R.drawable.icon_task_mesure);
		}
		else if(task.getType().equals(TASK_TYPE_FINDING)){
			imgTaskType.setImageResource(R.drawable.icon_task_fi);
		}
			
	
	}
	
	
	/*public void setData(final Task task, final int position){
		
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
	
				if(context instanceof TaskFragmentActivity){
				if(position == TaskFragmentActivity.getPosTerminate()-1){
					
					refreshStepColumn(TASK_PROCESSED);
					
				}	
				
				if(position == TaskFragmentActivity.getPosTerminate() ||
						TaskFragmentActivity.getPosTerminate() + 1 == position){
					refreshStepColumn(TASK_IN_PROCESSING);
				}
		
				}else{
					
					
					if(position == StepFragment.getPosTerminate()-1){
						
						refreshStepColumn(TASK_PROCESSED);
						
					}	
					
					if(position == StepFragment.getPosTerminate() ||
							StepFragment.getPosTerminate() + 1 == position){
						refreshStepColumn(TASK_IN_PROCESSING);
					}
					
				}
			}

			@Override
			public void onChange(String res) {
				// TODO Auto-generated method stub
				
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

					
					if(context instanceof TaskFragmentActivity){
					Intent intent = new Intent();
					intent.setClass(context, StepActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					context.startActivityForResult(intent, REQUEST_CODE_STEP_LIST);
					}else{
						
						StepFragment fragment = new StepFragment(context,taskListManager);
						
						((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
						.replace(R.id.activity_detail_container_2, fragment).commit();
					}
					
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
	*/
		

}
