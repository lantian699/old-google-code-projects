package com.alstom.lean.all.fragments;

import java.util.ArrayList;

import com.alstom.lean.all.R;
import com.alstom.lean.all.adapters.MyProjectAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Project;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class StepFragment extends Fragment implements OnClickListener {
	
	private static int pos_terminate=0;
	private Button btn_terminate;
	private FragmentActivity context;
	private TaskListManager taskListManager;
	public static final int RESULT_CODE_TERMINATE = 3;
	
	public StepFragment(Context context, TaskListManager manager){
		
		this.context = (FragmentActivity) context;
		this.taskListManager = manager;
	}
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_step, container, false);
        
        btn_terminate = (Button)rootView.findViewById(R.id.cancelsavesendbar_send);
		
		btn_terminate.setOnClickListener(this);
    
        return rootView;
    }
    
    @Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cancelsavesendbar_send:
			
			context.getSupportFragmentManager().beginTransaction()
			.remove(this).commit();
			
			taskListManager.notifyChange();
			pos_terminate ++;
			
			break;

		default:
			break;
		}
		
	}
    
    public static int getPosTerminate(){
		 return pos_terminate ;
	 }

}
