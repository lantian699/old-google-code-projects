package velib.views;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.PreparedUpdate;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;

import essai.cnam.R;
import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.tools.ParserInfoStation;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListPrincipalCellView extends LinearLayout implements OnCheckedChangeListener{
	
	private TextView tv_station_name;
	private CheckBox cb_isPrefered;
	private Context context;
	

	public ListPrincipalCellView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.list_station, this);
		
		tv_station_name = (TextView)findViewById(R.id.stationname);
		cb_isPrefered = (CheckBox)findViewById(R.id.checkbox_prefer);
		
		cb_isPrefered.setOnCheckedChangeListener(this);
		
	}

	
	public void setData(StationVelib station){
		
		tv_station_name.setText(station.getName());
		
		if(station.getIsPrefered() == 0)
			cb_isPrefered.setChecked(false);
		else
			cb_isPrefered.setChecked(true);
		
	}


	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
		
		LinearLayout listLayout = (LinearLayout) buttonView.getParent();
		
		TextView tv_station_name = (TextView)listLayout.findViewById(R.id.stationname);

		
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
				final Dao<InfoStation, Integer> InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
				
				Runnable thread = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							
							
							new ParserInfoStation(context, InfoStationDao, station.getNumber(), station.getId());
							
							
						}
					};
					
					HandlerThread myHandlerThread = new HandlerThread("getInfoStation");
					myHandlerThread.start();

					Handler handler = new Handler(myHandlerThread.getLooper());
					handler.post(thread);
				
			}else{
				
				station.setIsPrefered(0);
			}
			
			listStaionDao.update(station);
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
		
	}
}
