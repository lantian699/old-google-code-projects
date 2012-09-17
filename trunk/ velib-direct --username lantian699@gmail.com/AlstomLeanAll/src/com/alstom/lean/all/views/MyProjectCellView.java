package com.alstom.lean.all.views;




import com.alstom.lean.all.R;
import com.alstom.lean.all.models.Project;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MyProjectCellView extends LinearLayout{
	
	private TextView name;
	private TextView type;
	private TextView date_1;
	private TextView date_2;

	public MyProjectCellView(Context context) {
		super(context);

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.my_projet_cell_view, this);
	    
	    name = (TextView) findViewById(R.id.nom);
	    type = (TextView) findViewById(R.id.type);
	    date_1 = (TextView) findViewById(R.id.date);
	    date_2 = (TextView) findViewById(R.id.date2);
	    
	}
	
	
	public void setData(Project project){
		
		name.setText(project.getName());
		type.setText(project.getType());
	}

}
