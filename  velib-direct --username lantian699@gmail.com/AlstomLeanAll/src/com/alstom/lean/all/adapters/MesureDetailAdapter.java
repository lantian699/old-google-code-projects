package com.alstom.lean.all.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.views.MesureDetailCellView;
import com.alstom.lean.all.views.MyProjectCellView;

public class MesureDetailAdapter extends BaseAdapter{
	
	private Context context;
	private List<Mesurement> listMesure;
	private TaskListManager manager;
	private DatabaseHelper helper;
	private Task task;
	
	public MesureDetailAdapter(Context context, List<Mesurement> listMesure, TaskListManager manager, DatabaseHelper hepler, Task task){
		
		this.context = context;
		this.listMesure = listMesure;
		this.manager = manager;
		this.helper = hepler;
		this.task = task;
	}


	public int getCount() {
		// TODO Auto-generated method stub
		return listMesure.size();
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
		
		MesureDetailCellView view = (MesureDetailCellView) convertView;
		if (view == null) {
			view = new MesureDetailCellView(context, manager, helper);
		}
		view.setData(listMesure.get(position), position, task);

		return view;
	}

}
