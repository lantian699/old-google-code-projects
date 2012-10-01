package com.alstom.lean.all.adapters;

import java.sql.SQLException;
import java.util.List;

import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Request;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.VisualInspection;
import com.alstom.lean.all.views.AssistanceRequestCellView;
import com.alstom.lean.all.views.TaskDetailCellView;
import com.alstom.lean.all.views.TaskListCellView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TaskDetailAdapter extends BaseAdapter {

	
	
	private static final int TASK_DETAIL_COUNT = 1;
	private Context context;
	private DatabaseHelper dataHelper;
	private Task task;
	private TaskListManager tasklistManager;

	
	public  TaskDetailAdapter(Context context, Task task, DatabaseHelper helper,TaskListManager manager){
		
		this.context = context;
		this.task = task;
		this.dataHelper = helper;
		this.tasklistManager = manager;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TASK_DETAIL_COUNT;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		TaskDetailCellView view = (TaskDetailCellView) convertView;
		if (view == null) {
			view = new TaskDetailCellView(context, dataHelper,tasklistManager);
		}
		
		view.setData(task, position);
		
		
		
		

		return view;
	}

}
