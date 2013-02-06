package com.alstom.lean.all.views;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.LocationUsineActivity;
import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Person;

public class PersonCellView extends LinearLayout{
	
	private TextView name;
	private TextView value;
	private Context context;
	private LinearLayout ll_detail_cell_view;

	public PersonCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.detail_section_cell_view, this);
	    
	    this.context = context;
	    name = (TextView)findViewById(R.id.col_name);
	    value = (TextView)findViewById(R.id.col_value);
	    ll_detail_cell_view = (LinearLayout)findViewById(R.id.ll_detail_cell_view);
	    
	    
	}
	
	
	public void setData(Person person, int position){
		
		switch (position) {
		case 0:
			name.setText("Name");
			value.setText(person.getName());
			break;
		case 1:
			name.setText("EMAIL");
			value.setText(person.getEmail());
			break;
		
		case 2:
			name.setText(context.getString(R.string.telephone));
			value.setText(person.getTelNumber());
			break;


		default:
			break;
		}
		
	}

}