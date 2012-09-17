package com.alstom.lean.all.views;

import java.util.ArrayList;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.LocationUsineActivity;
import com.alstom.lean.all.activities.MyProjectActivity;
import com.alstom.lean.all.models.Enterprise;
import com.alstom.lean.all.models.Factory;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailSectionCellView extends LinearLayout{
	
	private TextView name;
	private TextView value;
	private Context context;
	private LinearLayout ll_detail_cell_view;

	public DetailSectionCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.detail_section_cell_view, this);
	    
	    this.context = context;
	    name = (TextView)findViewById(R.id.col_name);
	    value = (TextView)findViewById(R.id.col_value);
	    ll_detail_cell_view = (LinearLayout)findViewById(R.id.ll_detail_cell_view);
	    
	    
	}
	
	
	public void setData(Enterprise enterprise,final Factory factory, int position){
		
		switch (position) {
		case 0:
			name.setText(context.getString(R.string.society));
			value.setText(enterprise.getName());
			break;
		case 1:
			name.setText(context.getString(R.string.enter_addr));
			value.setText(enterprise.getAddress());
			break;
		case 2:
			name.setText(context.getString(R.string.geo_coordinate));
			value.setText(enterprise.getGeoCordinate());
			ll_detail_cell_view.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					ArrayList<Factory> listFactory = new ArrayList<Factory>();
					listFactory.add(factory);
					
					Intent intent = new Intent();
					intent.setClass(context, LocationUsineActivity.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable(MyProjectActivity.NAME_BUNDLE_LIST_FACTORY, listFactory);
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
			break;
		case 3:
			name.setText(context.getString(R.string.telephone));
			value.setText(enterprise.getTelephone());
			break;
		case 4:
			name.setText(context.getString(R.string.contact_name));
			value.setText(enterprise.getContactName());
			break;
		case 5:
			name.setText(context.getString(R.string.contact_emil));
			value.setText(enterprise.getContactEmail());
			break;

		default:
			break;
		}
		
	}

}
