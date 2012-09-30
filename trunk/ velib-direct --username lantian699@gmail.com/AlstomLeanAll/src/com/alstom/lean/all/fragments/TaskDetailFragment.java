package com.alstom.lean.all.fragments;

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

import com.alstom.lean.all.R;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.adapters.TaskDetailAdapter;
import com.alstom.lean.all.adapters.TaskListAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Task;

public class TaskDetailFragment extends Fragment implements OnClickListener{

	
	public static final int RESULT_CODE_TERMINATE = 3;
	private static int pos_terminate=0;
	private Button btn_terminate;
	private FragmentActivity context;
	private TaskListManager taskListManager;
	private Task task;
	private ListView listViewTask;
	private TaskDetailAdapter adapter;
	private DatabaseHelper dataHelper;
	private SectionedAdapter sectionedAdapter;


	public TaskDetailFragment(Context context, TaskListManager manager, Task task, DatabaseHelper helper){
		
		this.context = (FragmentActivity) context;
		this.taskListManager = manager;
		this.task = task;
		this.dataHelper = helper;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_task_detail, container, false);
	    
	    sectionedAdapter = new SectionedAdapter(getActivity());
	    
	    listViewTask = (ListView)rootView.findViewById(R.id.list_task_detail);
	    adapter = new TaskDetailAdapter(getActivity(), task, dataHelper); 
	    
	    sectionedAdapter.addSection(task.getName(), adapter);
	    listViewTask.setAdapter(sectionedAdapter);
	    
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
			
			
		//	taskListManager.notifyAddMesureChange();
			break;
	
		default:
			break;
		}
		
	}
	
	


}
