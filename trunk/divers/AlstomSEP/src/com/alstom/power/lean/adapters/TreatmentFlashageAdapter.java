package com.alstom.power.lean.adapters;

import com.alstom.power.lean.views.DetailSectionCellView;
import com.alstom.power.lean.views.TreatmentDateCellView;
import com.alstom.power.lean.views.TreatmentFlashageCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TreatmentFlashageAdapter extends BaseAdapter{
	
	private Context context;
	private int listCount;

	public TreatmentFlashageAdapter(Context context, int listCount){
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
		TreatmentFlashageCellView view = (TreatmentFlashageCellView) convertView;
		if (view == null) {
			view = new TreatmentFlashageCellView(context);
		}
		
		
		view.setData( position);

		return view;
		
	
	}

}
