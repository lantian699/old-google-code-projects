package velib.views;

import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import essai.cnam.R;

public class PreferStationCellView extends LinearLayout{

	private Context context;
	private TextView StationName;
	private TextView Free;
	private TextView Total;

	public PreferStationCellView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.list_prefer_station, this);
		
		StationName = (TextView)findViewById(R.id.prefer_station_name);
		Free = (TextView) findViewById(R.id.num_free_bike);
		Total = (TextView)findViewById(R.id.num_total_bike);
	}
	
	
	
	public void setData(InfoStation infoStation, Runnable screenCallback){
		
		try{
		Dao<StationVelib, Integer> listStationDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		QueryBuilder<StationVelib, Integer> queryBuilder = listStationDao.queryBuilder();
		queryBuilder.where().eq(StationVelib.COLUMN_VELIB_ID,infoStation.getStationVelibId());
		PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
		List<StationVelib> listStation = listStationDao.query(preparedQuery);
		
	
		StationName.setText("Station - "+listStation.get(0).getName());
		Free.setText(String.valueOf(infoStation.getFree()));
		Total.setText(String.valueOf("/"+infoStation.getTotal()));
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

}
