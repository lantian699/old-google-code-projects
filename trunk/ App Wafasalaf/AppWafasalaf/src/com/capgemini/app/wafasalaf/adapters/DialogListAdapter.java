package com.capgemini.app.wafasalaf.adapters;

import java.util.List;

import com.capgemini.app.wafasalaf.models.Client;
import com.capgemini.app.wafasalaf.models.Impaye;
import com.capgemini.app.wafasalaf.view.DetailClientCellView;
import com.capgemini.app.wafasalaf.view.DialogListCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public final class DialogListAdapter extends BaseAdapter{

	
	private Context context;
	private List<Impaye> listImpaye;

	public DialogListAdapter(Context context, List<Impaye> listImpaye){
		
		this.context = context;
		this.listImpaye = listImpaye;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listImpaye.size()+1;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		DialogListCellView view = (DialogListCellView) convertView;
		if (view == null) {
			view = new DialogListCellView(context);
		}
		
		if(position  == 0){
			view.setData(null, position);
		}else
		view.setData(listImpaye.get(position-1), position);

		return view;
	}

}
