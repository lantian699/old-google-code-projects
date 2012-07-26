package velib.views;

import java.sql.SQLException;
import java.util.List;

import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.model.getStationFromSite;
import velib.tools.Log;
import velib.tools.ParserInfoStation;
import velib.tools.Tools;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import essai.cnam.InfoStationActivity;
import essai.cnam.R;

public class PreferStationCellView extends LinearLayout implements OnClickListener{

	private Activity context;
	private TextView StationName;
	private TextView Free;
	private TextView Total;
	private ImageButton actualiser;
	private ImageButton supprimer;
	private ListView listView;
	private LinearLayout ll_prefer_station;
	private StationVelib station;

	public PreferStationCellView(Activity context, ListView listView) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		this.listView = listView;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.list_prefer_station, this);
		
		
		StationName = (TextView)findViewById(R.id.prefer_station_name);
		Free = (TextView) findViewById(R.id.num_free_bike);
		Total = (TextView)findViewById(R.id.num_total_bike);
		actualiser = (ImageButton) findViewById(R.id.refresh_station);
		supprimer = (ImageButton) findViewById(R.id.delete_station);
		ll_prefer_station = (LinearLayout)findViewById(R.id.ll_prefer_station);
		
		ll_prefer_station.setOnClickListener(this);
		
	}
	
	
	
	public void setData(final StationVelib station, Runnable screenCallback){
		
		this.station = station;
		
		try{
		Dao<InfoStation, Integer> listInfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
		QueryBuilder<InfoStation, Integer> queryBuilder = listInfoStationDao.queryBuilder();
		queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
		PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
		List<InfoStation> infoList = listInfoStationDao.query(preparedQuery);
		
	
		StationName.setText(station.getName());
		Free.setText(String.valueOf(infoList.get(0).getFree()));
		Total.setText(String.valueOf("/"+infoList.get(0).getTotal()));
		
		actualiser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				try {
					Dao<InfoStation, Integer> infoDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
					
					Log.i(context, "lancer l'actualisation");
					new ParserInfoStation(context, infoDao, station.getNumber(), station.getId());
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		
		final Dao<StationVelib, Integer> listStationDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		supprimer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				try {
					
					station.setIsPrefered(0);
					listStationDao.update(station);
					
					new getStationFromSite(context, listView).execute();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.ll_prefer_station:
			
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
