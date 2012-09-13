package com.alstom.power.sep.adapters;

import com.alstom.power.sep.views.DetailSectionCellView;
import com.alstom.power.sep.views.TreatmentDateCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TreatmentDateAdapter extends BaseAdapter{
	
	private Context context;
	private int listCount;

	public TreatmentDateAdapter(Context context, int listCount){
		this.context = context;
		this.listCount = listCount;
		
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
		TreatmentDateCellView view = (TreatmentDateCellView) convertView;
		if (view == null) {
			view = new TreatmentDateCellView(context);
		}
		
		
		view.setData( position);

		return view;
		
	
	}

}
