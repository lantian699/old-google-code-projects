package com.alstom.lean.all.views;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alstom.lean.all.R;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.ModelObject;
import com.j256.ormlite.dao.Dao;
import com.picture.drawing.ui.navigation.activity.PictureDrawingActivity;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MesureDetailCellView extends LinearLayout{
	
	public static final String MESURE_TYPE_NUMBER = "MN";
	public static final String MESURE_TYPE_ATTACHMENT = "MA";
	private TextView description;
	private TextView unit;
	private TextView rule;
	private EditText edit_value;
	private TextView timeStamps;
	private String value;
	private Mesurement mesure;
	private DatabaseHelper dataHelper;
	private Dao<Mesurement, ?> mesureDao;
	private ImageView btn_mesure_photo;

	public MesureDetailCellView(final Context context, TaskListManager manager, DatabaseHelper helper) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.mesure_detail_cell_view, this);
	    this.dataHelper = helper;
	  
		
		
	    description = (TextView)findViewById(R.id.tx_md_description);
	    timeStamps = (TextView)findViewById(R.id.tx_md_time);
	    unit = (TextView)findViewById(R.id.tx_md_unit);
	    rule = (TextView)findViewById(R.id.tx_md_rule);
	    edit_value = (EditText)findViewById(R.id.edit_md_value);
	    btn_mesure_photo = (ImageView)findViewById(R.id.btn_mesure_photo);
	    btn_mesure_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, PictureDrawingActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
			}
		});

	    manager.registerAddMesureChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {


				value = edit_value.getText().toString();
				
				if(value != null)
					mesure.setValue(value);
				mesure.setTimeStamp(timeStamps.getText().toString());
				
				try {
					mesureDao = dataHelper.getDao(Mesurement.class);
					mesureDao.update(mesure);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}		
			@Override
			public void onChange() {
			}

			@Override
			public void onChange(String res, ModelObject model) {
	
			}
		});
	    
	}
	
	
	public void setData(final Mesurement mesure, final int position){
		
		this.mesure = mesure;
		
		final double low = Double.parseDouble(mesure.getLow());
		final double high = Double.parseDouble(mesure.getHigh());
		
		 edit_value.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

					if(!s.toString().equals("")){
						double v = Double.parseDouble(s.toString());
						if(v < low || v> high){
							edit_value.setTextColor(Color.RED);
						}else if(v>low && v< high){
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
		
		
		
		
		description.setText(mesure.getDescription());
		unit.setText(mesure.getUnit());
		rule.setText("( " + mesure.getLow() + " < "+mesure.getHigh()+ " )");
		edit_value.setText(mesure.getValue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate); 
		timeStamps.setText(str);
		
		if(mesure.getType().equals(MESURE_TYPE_NUMBER)){
			btn_mesure_photo.setVisibility(View.INVISIBLE);
		}else if(mesure.getType().equals(MESURE_TYPE_ATTACHMENT)){
			btn_mesure_photo.setVisibility(View.VISIBLE);
		}
		
		
		
	}

}
