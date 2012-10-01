package com.alstom.lean.all.views;




import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.fragments.AssistanceRequestFragment;
import com.alstom.lean.all.fragments.TreatmentSectionFragment;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Project;
public class MyProjectCellView extends LinearLayout implements OnClickListener{
	
	private TextView projectName;
	private TextView startDate;
	private TextView endDate;
	private TextView description;
	private TextView titleListName;
	private FragmentActivity context;
	private Button btn_add_finding;
	private Button btn_home;
	private LinearLayout ll_project_detail;

	public MyProjectCellView(Context context) {
		super(context);
		
		this.context = (FragmentActivity) context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.my_project_cell_view_mod, this);
	    
	    projectName = (TextView)findViewById(R.id.project_name);
	    startDate = (TextView)findViewById(R.id.tx_start_date);
	    endDate = (TextView)findViewById(R.id.tx_end_date);
	    description = (TextView)findViewById(R.id.tx_description);
	    ll_project_detail = (LinearLayout)findViewById(R.id.ll_project_detail);
	    titleListName = (TextView)findViewById(R.id.title_list_name);
	    
	    
	    btn_home = (Button)findViewById(R.id.btn_home);
	    btn_add_finding = (Button)findViewById(R.id.btn_add_finding);
	    btn_add_finding.setOnClickListener(this);
	    btn_home.setOnClickListener(this);
	    
	}
	
	
	public void setData(Project project, int position){
		
		switch (position) {
		case 0:
			projectName.setText(project.getName());
			startDate.setText(project.getStartDate());
			endDate.setText(project.getEndDate());
			description.setText(project.getDescription());
			
			break;
			
		case 1:
			ll_project_detail.setVisibility(View.GONE);
			titleListName.setVisibility(View.VISIBLE);
			titleListName.setText("DETAILS/REQUEST");
			
			break;
		case 2:
			ll_project_detail.setVisibility(View.GONE);
			titleListName.setVisibility(View.VISIBLE);
			titleListName.setText("TASKS");
			break;
		case 3:
			ll_project_detail.setVisibility(View.GONE);
			titleListName.setVisibility(View.VISIBLE);
			titleListName.setText("COMPONENTS");
			break;
		case 4:
			ll_project_detail.setVisibility(View.GONE);
			titleListName.setVisibility(View.VISIBLE);
			titleListName.setText("DOCUMENTS");
			break;
			
		case 5:
			titleListName.setVisibility(View.GONE);
			ll_project_detail.setVisibility(View.GONE);
			btn_add_finding.setVisibility(View.VISIBLE);
			
			break;

		default:
			
			
			break;
		}
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_add_finding:
			
			
		/*	TreatmentSectionFragment fragmentTreat = new TreatmentSectionFragment(taskListManager );
	        
	        context.getSupportFragmentManager().beginTransaction()
	        .replace(R.id.activity_detail_container_1, fragmentTreat)
	        .commit();
	        
	        
	        Fragment fragment =context.getSupportFragmentManager().findFragmentById(R.id.activity_detail_container_2);
			
	        if(fragment != null)
	        context.getSupportFragmentManager().beginTransaction()
			.remove(fragment).commit();*/
	        
	        
			break;
			
		case R.id.btn_home:
			
			context.finish();
			break;

		default:
			break;
		}
		
	}

}
