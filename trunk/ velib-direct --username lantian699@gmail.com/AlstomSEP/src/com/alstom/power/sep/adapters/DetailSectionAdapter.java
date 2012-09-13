package com.alstom.power.sep.adapters;

import java.util.ArrayList;

import com.alstom.power.sep.models.Enterprise;
import com.alstom.power.sep.models.Project;
import com.alstom.power.sep.views.DetailSectionCellView;
import com.alstom.power.sep.views.MyProjectCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class DetailSectionAdapter extends BaseAdapter{
	
	private ArrayList<String[]> pData;
	private String nomSeparator;
	private Context context;
	private Enterprise enterprise;
	private Project project;
	
	
	public DetailSectionAdapter(Context context, Enterprise enterprise, Project project,String nomSeparator){
		
		this.context = context;
		this.pData = pData;
		this.nomSeparator = nomSeparator;
		this.enterprise = enterprise;
		this.project = project;
		
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
		
		
		view.setData(enterprise, project, position);

		return view;
	}

}
