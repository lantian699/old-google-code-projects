package com.alstom.lean.all.fragments;

import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.adapters.AssistanceRequestAdapter;
import com.alstom.lean.all.adapters.MesureDetailAdapter;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Task;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MesureDetailFragment extends Fragment implements OnClickListener{
	
	private List<Mesurement> listMesure;
	private ListView listViewMesure;
	private MesureDetailAdapter adapter;
	private Task task;
	private TextView title;
	private TaskListManager manager;
	private DatabaseHelper helper;
	private Button btn_terminate;
	private TaskListManager taskListManager;


	public MesureDetailFragment(List<Mesurement> listMesure, Task task, TaskListManager manager, DatabaseHelper hepler){
		
		this.listMesure = listMesure;
		this.task = task;
		this.manager = manager;
		this.helper = hepler;
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    	
    	
    	
    	
    }
 
 
	@Override
	 public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_mesure_detail, container, false);
		  
		  title = (TextView)view.findViewById(R.id.title_md_mesure);  
		  title.setText(task.getName());
		  
		  listViewMesure = (ListView) view.findViewById(R.id.list_mesure);
		  adapter = new MesureDetailAdapter(getActivity(), listMesure, manager, helper);
		  listViewMesure.setAdapter(adapter);
		  
		  btn_terminate = (Button)view.findViewById(R.id.cancelsavesendbar_send);
			
		btn_terminate.setOnClickListener(this);
		taskListManager = MyProjectModeTabletActivity.getTaskListManager();
		  
		 return view;
	 }
	
	
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cancelsavesendbar_send:
			
			taskListManager.notifyAddMesureChange("");
			
			getActivity().finish();
			
			break;

		default:
			break;
		}
		
	}
}
