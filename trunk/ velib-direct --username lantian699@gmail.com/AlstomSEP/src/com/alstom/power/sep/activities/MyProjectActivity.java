package com.alstom.power.sep.activities;


import java.util.ArrayList;
import java.util.List;

import com.alstom.power.sep.R;
import com.alstom.power.sep.adapters.AdapterMyProject;
import com.alstom.power.sep.models.Project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class MyProjectActivity extends Activity{
	
	private ListView list_myproject;
	private AdapterMyProject adapterMyProject;
	private List<Project> listProjects;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_myproject);
		
		list_myproject = (ListView)findViewById(R.id.list_myproject);
		listProjects = new ArrayList<Project>();
		
		listProjects = simulationOfProjects();
		
		adapterMyProject = new AdapterMyProject(this,listProjects);
		list_myproject.setAdapter(adapterMyProject);
		
	}


	private List<Project> simulationOfProjects() {

		listProjects.clear();
		
		for(int i=0; i<20; i++){
			
			Project project = new Project();
			project.setName("Project "+i);
			project.setType("Type "+i);
			
			listProjects.add(project);
			
		}
		
		return listProjects;
		
	}

}
