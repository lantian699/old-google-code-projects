package com.alstom.power.sep.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alstom.power.sep.R;
import com.alstom.power.sep.models.Request;

public class AssistanceRequestCellView extends LinearLayout{

	private TextView name;
	private TextView value;
	private Context context;

	public AssistanceRequestCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.detail_section_cell_view, this);
	    
	    
	    this.context = context;

	    name = (TextView)findViewById(R.id.col_name);
	    value = (TextView)findViewById(R.id.col_value);
	    
	}
	
	
	public void setData(Request request, int position){
		
		switch (position) {
		case 0:
			name.setText(context.getString(R.string.req_num));
			value.setText(request.getNumbre());
			break;
		case 1:
			name.setText(context.getString(R.string.req_title));
			value.setText(request.getTitle());
			break;
		case 2:
			name.setText(context.getString(R.string.req_description));
			value.setText(request.getDescription());
			break;
		case 3:
			name.setText(context.getString(R.string.req_incident_num));
			value.setText(request.getIncidentClientNumber());
			break;
		case 4:
			name.setText(context.getString(R.string.req_deadline));
			value.setText(request.getDeadLine());
			break;

		default:
			break;
		}
		
	}


	
	

}
