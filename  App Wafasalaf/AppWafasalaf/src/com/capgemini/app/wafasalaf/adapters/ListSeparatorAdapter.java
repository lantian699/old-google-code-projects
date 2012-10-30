package com.capgemini.app.wafasalaf.adapters;

import java.util.List;

import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.view.ListClientCellView;
import com.capgemini.app.wafasalaf.view.ListSeparatorCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListSeparatorAdapter extends BaseAdapter{

	private Context context;
	private String section;
	private int count;

	public ListSeparatorAdapter(Context context) {

		this.context = context;

	}

	public int getCount() {
		// TODO Auto-generated method stub
			return 1;

	}

	public Object getItem(int arg0) {
		
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		ListSeparatorCellView view = (ListSeparatorCellView) convertView;
		if (view == null) {
			view = new ListSeparatorCellView(context);
		}
		
		view.setData(section, count);
	
		return view;
	
		
	}


	public void add(String section, int count) {
		// TODO Auto-generated method stub
		this.section = section;
		this.count =count;
	}

}
