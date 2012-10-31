package com.capgemini.app.wafasalaf.adapters;

import java.util.List;

import com.capgemini.app.wafasalaf.managers.ListManager;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.view.ListClientCellView;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListClientAdapter extends BaseAdapter{
	
	
	private List<Recouvrement> listClient;
	private Context context;
	private boolean isDispaly;
	private ListManager listManager;

	public ListClientAdapter(Context context, List<Recouvrement> listClient, boolean isDisplay, ListManager manager){
		
		this.context = context;
		this.listClient = listClient;
		this.isDispaly = isDisplay;
		this.listManager = manager;
		
		
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		if (isDispaly)
			return listClient.size();
		else
			return 0;
	}

	public int getRealCount() {
		
		return listClient.size();
	}
	public Object getItem(int arg0) {
		
		return listClient.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		ListClientCellView view = (ListClientCellView) convertView;
		if (view == null) {
			view = new ListClientCellView(context);
			
		}
		
		
		view.setData(listClient.get(position), position);
		return view;
	
		
	}


}
