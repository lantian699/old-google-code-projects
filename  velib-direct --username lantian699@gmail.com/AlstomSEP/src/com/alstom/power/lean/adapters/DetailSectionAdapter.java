package com.alstom.power.lean.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alstom.power.lean.models.Enterprise;
import com.alstom.power.lean.views.DetailSectionCellView;

public class DetailSectionAdapter extends BaseAdapter{
	
	private Context context;
	private Enterprise enterprise;

	
	
	public DetailSectionAdapter(Context context, Enterprise enterprise){
		
		this.context = context;
		this.enterprise = enterprise;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
	}

	public Object getItem(int arg0) {
		
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		DetailSectionCellView view = (DetailSectionCellView) convertView;
		if (view == null) {
			view = new DetailSectionCellView(context);
		}
		
		
		view.setData(enterprise, position);

		return view;
	}

}
