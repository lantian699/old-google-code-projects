package com.alstom.lean.all.views;




import com.alstom.lean.all.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TreatmentDateCellView extends LinearLayout{

	private Context context;
	private TextView date_title;
	private EditText treat_date;
	private EditText treat_time;
	

	public TreatmentDateCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.treatment_date_cell_view, this);
	    
	    this.context = context;
	    date_title = (TextView) findViewById(R.id.date_title);
	    treat_date = (EditText) findViewById(R.id.treat_date);
	    treat_date = (EditText) findViewById(R.id.treat_date);

	    
	}
	
	
	public void setData(int position){
		
		switch (position) {
		case 0:
			date_title.setText(context.getString(R.string.start_date_title));
			
			break;
			
		case 1:
			date_title.setText(context.getString(R.string.end_date_title));

		default:
			break;
		}
		
		
	}

}
