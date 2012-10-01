package com.alstom.lean.all.views;

import java.sql.SQLException;

import com.alstom.lean.all.R;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.j256.ormlite.dao.Dao;

import android.R.integer;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MesureDetailCellView extends LinearLayout{
	
	private TextView description;
	private TextView unit;
	private TextView rule;
	private EditText edit_value;
	private String value;
	private Mesurement mesure;
	private DatabaseHelper dataHelper;

	public MesureDetailCellView(Context context, TaskListManager manager, DatabaseHelper helper) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.mesure_detail_cell_view, this);
	    
	    description = (TextView)findViewById(R.id.tx_md_description);
	    unit = (TextView)findViewById(R.id.tx_md_unit);
	    rule = (TextView)findViewById(R.id.tx_md_rule);
	    edit_value = (EditText)findViewById(R.id.edit_md_value);
	    this.dataHelper = helper;
	    
	   
	    
	    manager.registerAddMesureChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChange() {
				// TODO Auto-generated method stub
				
				value = edit_value.getText().toString();
				if(value != null)
					mesure.setValue(value);
				
				Dao<Mesurement, ?> mesureDao;
				try {
					mesureDao = dataHelper.getDao(Mesurement.class);
					mesureDao.update(mesure);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
		});
	    
	}
	
	
	public void setData(Mesurement mesure, int position){
		
		this.mesure = mesure;
		description.setText(mesure.getDescription());
		unit.setText(mesure.getUnit());
		rule.setText("( " + mesure.getLow() + " < "+mesure.getHigh()+ " )");
		
		final double low = Double.parseDouble(mesure.getLow());
		final double high = Double.parseDouble(mesure.getHigh());
		
		 edit_value.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {


					if(!s.toString().equals("")){
						double v = Double.parseDouble(s.toString());
						if(!(v > low && v < high)){
							
							edit_value.setTextColor(Color.RED);
						}else{
							edit_value.setTextColor(Color.BLACK);
						}
					}
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
			});
	}

}
