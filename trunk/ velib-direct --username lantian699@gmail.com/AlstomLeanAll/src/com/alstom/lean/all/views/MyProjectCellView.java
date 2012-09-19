package com.alstom.lean.all.views;




import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.fragments.AssistanceRequestFragment;
import com.alstom.lean.all.fragments.TreatmentSectionFragment;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Project;
public class MyProjectCellView extends LinearLayout implements OnClickListener{
	
	private TextView name;
	private TextView type;
	private TextView date_1;
	private TextView date_2;
	private TaskListManager taskListManager;
	private FragmentActivity context;
	private Button btn_treat;


	public MyProjectCellView(Context context, TaskListManager manager) {
		super(context);
		
		this.context = (FragmentActivity) context;
		this.taskListManager = manager;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.my_projet_cell_view, this);
	    
	    name = (TextView) findViewById(R.id.nom);
	    type = (TextView) findViewById(R.id.type);
	    date_1 = (TextView) findViewById(R.id.date);
	    date_2 = (TextView) findViewById(R.id.date2);
	    btn_treat = (Button)findViewById(R.id.btn_treatment);
	    
	    if(((MyProjectModeTabletActivity)context).findViewById(R.id.activity_detail_container_2) == null)
	    	btn_treat.setVisibility(View.GONE);
	    
	    
	    btn_treat.setOnClickListener(this);
	}
	
	
	public void setData(Project project){
		
		name.setText(project.getName());
		type.setText(project.getType());
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_treatment:
			
			
			TreatmentSectionFragment fragmentTreat = new TreatmentSectionFragment(taskListManager );
	        
	        context.getSupportFragmentManager().beginTransaction()
	        .replace(R.id.activity_detail_container_1, fragmentTreat)
	        .commit();
	        
	        
	        Fragment fragment =context.getSupportFragmentManager().findFragmentById(R.id.activity_detail_container_2);
			
	        if(fragment != null)
	        context.getSupportFragmentManager().beginTransaction()
			.remove(fragment).commit();
	        
	        
			break;

		default:
			break;
		}
		
	}

}
