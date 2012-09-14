package com.alstom.power.lean.activities;


import java.util.ArrayList;
import java.util.List;

import com.alstom.power.lean.adapters.MyProjectAdapter;
import com.alstom.power.lean.models.Project;
import com.alstom.power.sep.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyProjectActivity extends Activity implements OnItemClickListener{
	
	private ListView list_myproject;
	private MyProjectAdapter adapterMyProject;
	private List<Project> listProjects;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_myproject);
		
		list_myproject = (ListView)findViewById(R.id.list_myproject);
		listProjects = new ArrayList<Project>();
		
		listProjects = simulationOfProjects();
		
		adapterMyProject = new MyProjectAdapter(this,listProjects);
		list_myproject.setAdapter(adapterMyProject);
		list_myproject.setOnItemClickListener(this);
		
	}


	private List<Project> simulationOfProjects() {

		listProjects.clear();
		
		for(int i=0; i<20; i++){
			
			Project project = new Project();
			project.setName(getString(R.string.project)+i);
			project.setType("Type "+i);
			
			listProjects.add(project);
			
		}
		
		return listProjects;
		
	}


	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
		
		System.out.println("view = " + listProjects.get(position));
		
		Intent intent = new Intent();
		intent.setClass(this, TaskFragmentActivity.class);
		intent.putExtra("project", listProjects.get(position));
		
		startActivity(intent);
		
		
	}

}
