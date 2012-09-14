package com.alstom.power.lean.activities;


import java.util.ArrayList;
import java.util.List;

import com.alstom.power.lean.activities.models.ActionBarActivity;
import com.alstom.power.lean.adapters.MyProjectAdapter;
import com.alstom.power.lean.models.Factory;
import com.alstom.power.lean.models.Project;
import com.alstom.power.lean.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MyProjectActivity extends ActionBarActivity implements OnItemClickListener{
	
	public static final String NAME_BUNDLE_LIST_FACTORY = "listFactory";
	private ListView list_myproject;
	private MyProjectAdapter adapterMyProject;
	private ArrayList<Project> listProjects;
	private ArrayList<Factory> listFactory;
	
	
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
		
		simulationFactory();
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
		switch (item.getItemId()) {
		case R.id.menu_map:
			
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LocationUsineActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable(NAME_BUNDLE_LIST_FACTORY, listFactory);	
			intent.putExtras(bundle);
			
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}


	private ArrayList<Project> simulationOfProjects() {

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
	
	public void simulationFactory(){
		
		listFactory = new ArrayList<Factory>();
		listFactory.clear();
		
		Factory factory1 = new Factory();
		factory1.setLatitude(48.893376);
		factory1.setLongitude(2.287474);
		factory1.setName("Alstom");
		factory1.setAddress("3 Avenue Andr¨¦ Malraux, 92300 Levallois-Perret");
		listFactory.add(factory1);
		
		Factory factory2 = new Factory();
		factory2.setLatitude(48.896352);
		factory2.setLongitude(2.273505);
		factory2.setName("Alstom Power Industries");
		factory2.setAddress("3 Avenue Andr¨¦ Malraux 92300 Levallois-Perret");
		listFactory.add(factory2);
		
		
		Factory factory3 = new Factory();
		factory3.setLatitude(48.719919);
		factory3.setLongitude(2.267003);
		factory3.setName("Alstom Power Systems");
		factory3.setAddress("12 Rue Jean Bart 91300 Massy");
		listFactory.add(factory3);
		
		
		Factory factory4 = new Factory();
		factory4.setLatitude(48.914265);
		factory4.setLongitude(2.332149);
		factory4.setName("Alstom ITC");
		factory4.setAddress("48 Rue Albert Dhalenne 93400 Saint-Ouen");
		listFactory.add(factory4);
		
		
	}

}
