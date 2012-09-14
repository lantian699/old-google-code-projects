package com.alstom.power.lean.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alstom.power.lean.models.Request;
import com.alstom.power.lean.views.AssistanceRequestCellView;

public class AssistanceRequestAdapter extends BaseAdapter{

	private Request request;
	private Context context;
	
	public  AssistanceRequestAdapter(Context context, Request request){
		
		this.context = context;
		this.request = request;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
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
		
		
		AssistanceRequestCellView view = (AssistanceRequestCellView) convertView;
		if (view == null) {
			view = new AssistanceRequestCellView(context);
		}
		
		
		view.setData(request,position);

		return view;
	}

}
