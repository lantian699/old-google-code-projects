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
import android.view.ViewGroup.LayoutParams;
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
		
		LayoutParams params = (LayoutParams) getLayoutParams();
		params.height = LayoutParams.WRAP_CONTENT;
		params.width = 450;
		
		setLayoutParams(params);
		
		StationName = (TextView)findViewById(R.id.prefer_station_name);
		Free = (TextView) findViewById(R.id.num_free_bike);
		Total = (TextView)findViewById(R.id.num_total_bike);
	}
	
	
	
	public void setData(StationVelib station, Runnable screenCallback){
		
		try{
		Dao<InfoStation, Integer> listInfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
		QueryBuilder<InfoStation, Integer> queryBuilder = listInfoStationDao.queryBuilder();
		queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
		PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
		List<InfoStation> listStation = listInfoStationDao.query(preparedQuery);
		
	
		StationName.setText("Station - "+station.getName());
		Free.setText(String.valueOf(listStation.get(0).getFree()));
		Total.setText(String.valueOf("/"+listStation.get(0).getTotal()));
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	

}
