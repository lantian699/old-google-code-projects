package com.alstom.lean.all.adapters;


import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.views.TreatmentFlashageCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TreatmentFlashageAdapter extends BaseAdapter{
	
	private Context context;
	private int listCount;
	private TaskListManager manager;

	public TreatmentFlashageAdapter(Context context, int listCount, TaskListManager manager){
		this.context = context;
		this.listCount = listCount;
		this.manager = manager;
		
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
		TreatmentFlashageCellView view = (TreatmentFlashageCellView) convertView;
		if (view == null) {
			view = new TreatmentFlashageCellView(context, manager);
		}
		
		
		view.setData( position);

		return view;
		
	
	}

}