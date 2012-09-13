package com.alstom.power.sep.views;

import com.alstom.power.sep.R;
import com.alstom.power.sep.models.Enterprise;
import com.alstom.power.sep.models.Project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailSectionCellView extends LinearLayout{
	
	private TextView name;
	private TextView value;
	private Context context;

	public DetailSectionCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.detail_section_cell_view, this);
	    
	    this.context = context;
	    name = (TextView)findViewById(R.id.col_name);
	    value = (TextView)findViewById(R.id.col_value);
	    
	}
	
	
	public void setData(Enterprise enterprise, Project project, int position){
		
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
