package com.alstom.lean.all.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Person;
import com.alstom.lean.all.views.DetailSectionCellView;
import com.alstom.lean.all.views.PersonCellView;

public class PersonAdapter extends BaseAdapter{
	
	private Context context;
	private Person person;

	
	
	public PersonAdapter(Context context, Person person){
		
		this.context = context;
		this.person = person;
		
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	public Object getItem(int arg0) {
		
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {

		PersonCellView view = (PersonCellView) convertView;
		if (view == null) {
			view = new PersonCellView(context);
		}
		
		
		view.setData(person, position);

		return view;
	}

}