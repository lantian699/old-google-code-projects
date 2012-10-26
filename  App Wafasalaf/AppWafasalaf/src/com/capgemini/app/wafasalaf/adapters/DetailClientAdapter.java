package com.capgemini.app.wafasalaf.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.capgemini.app.wafasalaf.view.DetailClientCellView;
import com.capgemini.app.wafasalaf.view.ListClientCellView;

public class DetailClientAdapter extends BaseAdapter {

	private Context context;
	private String type;
	private int count;
	private Client client;

	public DetailClientAdapter(Context context, String type, int count, Client client){
		
		this.context = context;
		this.type = type;
		this.count = count;
		this.client = client;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return count;
	}

	public Object getItem(int arg0) {
		
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		DetailClientCellView view = (DetailClientCellView) convertView;
		if (view == null) {
			view = new DetailClientCellView(context);
		}
		
		
		view.setData(client, type, position );

		return view;
	}

}
