package com.capgemini.app.wafasalaf.adapters;

import java.util.List;

import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.view.ListClientCellView;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ListClientAdapter extends BaseAdapter{
	
	
	private List<Recouvrement> listClient;
	private Context context;

	public ListClientAdapter(Context context, List<Recouvrement> listClient){
		
		this.context = context;
		this.listClient = listClient;
		
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
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
		
		
		view.setData(listClient.get(position));

		return view;
	}


}
