package com.alstom.lean.all.adapters;


import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.views.DetailSectionCellView;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public class DetailSectionAdapter extends BaseAdapter{
	
	private Context context;
	private Enterprise enterprise;
	private Factory factory;

	
	
	public DetailSectionAdapter(Context context, Enterprise enterprise, Factory factory){
		
		this.context = context;
		this.enterprise = enterprise;
		this.factory = factory;
		
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
		
		
		view.setData(enterprise,factory, position);

		return view;
	}

}
