package com.alstom.lean.all.activities;

import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.R;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Project;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProjectListActivity extends ListActivity{
	
	public static final String INTENT_EXTRA_PROJECT_NAME = "project";
	private ArrayAdapter<String> adapter;
	private List<Project> listProject;
	private List<String> listProjectName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_project_list);
		
		try{
		listProjectName = new ArrayList<String>();
		Dao<Project, ?> projectDao = DatabaseHelper.getInstance(this).getDao(Project.class);
		listProject = projectDao.queryForAll();
		
		for (Project project : listProject) {
			
			listProjectName.add(project.getName());
		}
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,listProjectName);
		
		setListAdapter(adapter);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		Intent intent = new Intent();
		intent.setClass(this, MyProjectModeTabletActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(INTENT_EXTRA_PROJECT_NAME, listProject.get(position));
		startActivity(intent);
	}

}
