package com.alstom.lean.all.adapters;


import java.util.ArrayList;
import java.util.List;

import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.views.MyProjectCellView;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyProjectAdapter extends BaseAdapter{
	private Context context;
	private List<Project> listProjects;
	private TaskListManager manager;
	
	public MyProjectAdapter(Context context, List<Project> listProjects, TaskListManager manager){
		
		this.context = context;
		this.listProjects = listProjects;
		this.manager = manager;
		
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return listProjects.size();
	}

	public Project getItem(int position) {
		// TODO Auto-generated method stub
		
		return listProjects.get(position);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		MyProjectCellView view = (MyProjectCellView) convertView;
		if (view == null) {
			view = new MyProjectCellView(context, manager);
		}

		Project project = getItem(position);
		view.setData(project);

		return view;
	}

}
