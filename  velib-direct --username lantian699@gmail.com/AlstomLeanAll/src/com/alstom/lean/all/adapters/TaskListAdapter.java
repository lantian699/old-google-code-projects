package com.alstom.lean.all.adapters;

import java.util.List;

import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.views.TaskListCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class TaskListAdapter extends BaseAdapter {
	
	private Context context;
	private List<Task> listTasks;
	private TaskListManager taskListManager;

	public TaskListAdapter(Context context,List<Task> listTasks, TaskListManager taskListManager){
		this.context = context;
		this.listTasks = listTasks;
		this.taskListManager = taskListManager;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listTasks.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
	

	public View getView(int position, View convertView, ViewGroup arg2) {
		TaskListCellView view = (TaskListCellView) convertView;
		if (view == null) {
			view = new TaskListCellView(context, taskListManager);
		}
		
		Task task = listTasks.get(position);
		view.setData(task, position);

		return view;
		
	
	}

}
