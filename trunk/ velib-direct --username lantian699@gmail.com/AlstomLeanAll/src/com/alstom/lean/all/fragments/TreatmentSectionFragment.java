package com.alstom.lean.all.fragments;


import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.adapters.SelectTaskAdapter;
import com.alstom.lean.all.adapters.TaskListAdapter;
import com.alstom.lean.all.adapters.TreatmentDateAdapter;
import com.alstom.lean.all.adapters.TreatmentFlashageAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Task;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;



public class TreatmentSectionFragment extends Fragment {
	
	private ListView listTreatment;
	private SectionedAdapter sectionedAdapter;
	private TreatmentDateAdapter  treatmentDateAdapter;
	private TreatmentFlashageAdapter treatmentFlashageAdapter;
	private TaskListAdapter taskListAdapter;
	private List<Task> listTasks;
	private TaskListManager taskListManager;
	private SelectTaskAdapter selectTaskAdapter;
	
	
	public TreatmentSectionFragment(TaskListManager taskListManager){
		
		this.taskListManager = taskListManager;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    
    	simulationTaskList();
    	
    	sectionedAdapter = new SectionedAdapter(getActivity());
    	treatmentDateAdapter = new TreatmentDateAdapter(getActivity(), 2);
    	taskListAdapter = new TaskListAdapter(getActivity(), listTasks,taskListManager, 4);
    	treatmentFlashageAdapter = new TreatmentFlashageAdapter(getActivity(), 2, taskListManager);
    	selectTaskAdapter = new SelectTaskAdapter(getActivity(), listTasks, listTasks.size());
    	
    	
    	
    	sectionedAdapter.addSection(getString(R.string.title_treatment), treatmentDateAdapter);
    	sectionedAdapter.addSection(getString(R.string.title_task_list), taskListAdapter);
    	sectionedAdapter.addSection(getString(R.string.title_select_task), selectTaskAdapter);
    	sectionedAdapter.addSection(getString(R.string.title_flash), treatmentFlashageAdapter);
    	
    	
    }
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		  View view = inflater.inflate(R.layout.fragment_detail, container, false);
		  listTreatment = (ListView) view.findViewById(R.id.list_ent_detail);
		  listTreatment.setAdapter(sectionedAdapter);
		 return view;
    }

	public void simulationTaskList(){
		
		listTasks = new ArrayList<Task>();
		listTasks.clear();
		
		Task task1 = new Task();
		task1.setName("Inspection Unit A");
		task1.setLabel("Accept the intervention");
		task1.setDate("may 12 12:00 - 14:30");
		task1.setMandatory(true);
		task1.setValidated(false);
		task1.setProcessing(false);
		listTasks.add(task1);
		
		Task task2 = new Task();
		task2.setName("Inspection Unit B");
		task2.setLabel("Arrival at the site");
		task2.setDate("may 12 12:30 - ");
		task2.setMandatory(true);
		task2.setValidated(false);
		task2.setProcessing(false);
		listTasks.add(task2);
		
		Task task3 = new Task();
		task3.setName("Inspection Unit C");
		task3.setLabel("Temperature mesurement");
		task3.setDate("---");
		task3.setMandatory(false);
		task3.setValidated(false);
		task3.setProcessing(false);
		listTasks.add(task3);
		
		
		Task task4 = new Task();
		task4.setName("Inspection Unit D");
		task4.setLabel("Communication");
		task4.setDate("---");
		task4.setMandatory(true);
		task4.setValidated(false);
		task4.setProcessing(false);
		listTasks.add(task4);
	}
	
	
}