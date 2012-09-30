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
	private Project project;
	
	public MyProjectAdapter(Context context, Project project){
		
		this.context = context;
		this.project = project;
		
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return 6;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		
		MyProjectCellView view = (MyProjectCellView) convertView;
		if (view == null) {
			view = new MyProjectCellView(context);
		}
		view.setData(project, position);

		return view;
	}

}
