package velib.views;

import java.util.List;

import velib.model.AnActionModeOfEpicProportions;
import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.tools.ParserInfoStation;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.ActionMode;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import essai.cnam.R;

public class ListPrincipalCellView extends LinearLayout implements OnCheckedChangeListener{
	
	private TextView tv_station_name;
	private CheckBox cb_isPrefered;
	private SherlockActivity context;
	private ActionMode mMode;

	public ListPrincipalCellView(SherlockActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		inflater.inflate(R.layout.list_station_detail, this);
		
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
				
				//mMode = context.startActionMode(new AnActionModeOfEpicProportions(context));
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
