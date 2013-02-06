package com.alstom.power.lean.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alstom.power.lean.managers.TaskListManager;
import com.alstom.power.lean.models.Task;
import com.alstom.power.lean.views.SelectTaskListCellView;
import com.alstom.power.lean.views.TaskListCellView;

public class SelectTaskAdapter extends BaseAdapter{
	
	private Context context;
	private int listCount;
	private List<Task> listTasks;

	public SelectTaskAdapter(Context context,List<Task> listTasks,  int listCount){
		this.context = context;
		this.listCount = listCount;
		this.listTasks = listTasks;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return listCount;
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
		SelectTaskListCellView view = (SelectTaskListCellView) convertView;
		if (view == null) {
			view = new SelectTaskListCellView(context);
		}
		
		Task task = listTasks.get(position);
		view.setData(task, position);

		return view;
		
	
	}


}
