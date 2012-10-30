package com.capgemini.app.wafasalaf.view;

import com.capgemini.app.wafasalaf.R;
import com.capgemini.app.wafasalaf.models.Recouvrement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListSeparatorCellView extends LinearLayout{

	
	private TextView tx_list_name;
	private TextView tx_list_num;


	public ListSeparatorCellView(final Context context) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.listseparator, this);
	    tx_list_name = (TextView) findViewById(R.id.tx_list_name);
	    tx_list_num = (TextView)findViewById(R.id.tx_list_num);
	    
	    }
	
	
	public void setData(String name, int num){
		
		tx_list_name.setText(name);
		tx_list_num.setText(String.valueOf(num));
	}
}
