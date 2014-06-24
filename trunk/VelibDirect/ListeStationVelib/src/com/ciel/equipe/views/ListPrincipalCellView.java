package com.ciel.equipe.views;

import java.sql.SQLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ciel.equipe.R;
import com.ciel.equipe.activities.InfoStationActivity;
import com.ciel.equipe.model.DatabaseHelper;
import com.ciel.equipe.model.InfoStation;
import com.ciel.equipe.model.StationVelib;
import com.ciel.equipe.tools.ParserInfoStation;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class ListPrincipalCellView extends LinearLayout implements OnCheckedChangeListener, OnClickListener{
	
	private TextView tv_station_name;
	private CheckBox cb_isPrefered;
	private Activity context;
	private TextView free;
	private TextView total;
	private LinearLayout ll_list_station;
	private StationVelib station;
	
	public ListPrincipalCellView(Activity context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.list_station_detail, this);
		
		tv_station_name = (TextView)findViewById(R.id.list_station_name);
		cb_isPrefered = (CheckBox)findViewById(R.id.checkbox_prefer);
		free = (TextView) findViewById(R.id.num_free_bike_principal);
		total = (TextView)findViewById(R.id.num_total_bike_principal);
		ll_list_station = (LinearLayout) findViewById(R.id.ll_list_station);
		
		
		cb_isPrefered.setOnCheckedChangeListener(this);
		ll_list_station.setOnClickListener(this);
	
		
	}

	
	public void setData(final StationVelib station){
		
		this.station = station;
		
		
		try {
			final Dao<InfoStation, Integer> infoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
			
		
			
			
			Runnable thread = new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					
					
					new ParserInfoStation(context, infoStationDao, station.getNumber(), station.getId());
					
				}
			};
			
			HandlerThread myHandlerThread = new HandlerThread("getInfoStation");
			myHandlerThread.start();

			Handler handler = new Handler(myHandlerThread.getLooper());
			handler.post(thread);
			
			
			QueryBuilder<InfoStation, Integer> queryBuilder = infoStationDao.queryBuilder();
			queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
			PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
			List<InfoStation> infoList = infoStationDao.query(preparedQuery);
			
			
			if(infoList.size() > 0){
				
				InfoStation infoStation = infoList.get(0);
				free.setText(String.valueOf(infoStation.getFree()));
				total.setText("/"+String.valueOf(infoStation.getTotal()));
			}
			else {
				
				Toast.makeText(context, "Vous n'avez pas de connexion Internet.", Toast.LENGTH_SHORT).show();
			}
			tv_station_name.setText(station.getName());
			
			
			if(station.getIsPrefered() == 0)
				cb_isPrefered.setChecked(false);
			else
				cb_isPrefered.setChecked(true);
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		/*
		View closeButton = (View) context.findViewById(R.id.abs__action_mode_close_button);
		
		System.out.println("parent view = " + closeButton);
		
		if (closeButton != null) {
			
			closeButton.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					
					cb_isPrefered.setChecked(false);
				}
			});
			
		}
		
		*/
		
		
		LinearLayout listLayout = (LinearLayout) buttonView.getParent().getParent();
		
		TextView tv_station_name = (TextView)listLayout.findViewById(R.id.list_station_name);

		
		String stationName =tv_station_name.getText().toString();
		
		try{
		
			Dao<StationVelib, Integer> listStaionDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
			QueryBuilder<StationVelib, Integer> queryBuilder = listStaionDao.queryBuilder();
			queryBuilder.where().eq(StationVelib.COLUMN_VELIB_NAME,stationName);
			PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
			List<StationVelib> listStation = listStaionDao.query(preparedQuery);
			
			final StationVelib station = listStation.get(0);
			if(isChecked){
				
				station.setIsPrefered(1);
				
			}else{
				
				station.setIsPrefered(0);
			}
			
			listStaionDao.update(station);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ll_list_station:
			
			System.out.println("listVelib = " +station );

			Intent intent = new Intent(context,InfoStationActivity.class);
			intent.putExtra("station", station);
			context.startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}
}
