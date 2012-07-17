package velib.model;

import java.sql.SQLException;
import java.util.List;

import velib.tools.Log;
import velib.tools.ParserListVelib;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import essai.cnam.R;

public class getStationFromSite extends AsyncTask<Void, Void, Void>{
	
	 private ProgressDialog dialog ;
	 private Activity context;
	 private ListView listView;
	 
	 public getStationFromSite(Activity context, ListView listView) {
		// TODO Auto-generated constructor stub
		 this.context = context;
		 this.listView = listView;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		dialog  =  ProgressDialog.show(context, "", "Loading data .....");
	}

	
	 
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		try {
			  Dao<StationVelib, Integer> StationVelibDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
			  List<StationVelib> list = StationVelibDao.queryForAll();
			
				if(list.size() == 0)
				new ParserListVelib(context, StationVelibDao);
			

			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e(this, "SQL error", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		try{
			
			Dao<InfoStation, Integer> InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
			QueryBuilder<InfoStation, Integer> queryBuilder = InfoStationDao.queryBuilder();
			queryBuilder.where().eq(InfoStation.COLUMN_INFO_ISPREFERED,"1");
			PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
			List<InfoStation> infoList = InfoStationDao.query(preparedQuery);
		
			Log.i(context, "Prefer Station --> "+infoList.size());
			
			if(infoList.size() > 0){
		
			AdapterListPreferStation adapter_prefer = new AdapterListPreferStation(context, infoList, null);
		
			listView.setAdapter(adapter_prefer);
			
			context.findViewById(R.id.title).setVisibility(View.GONE);
			
		
		}
		
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		dialog.dismiss();
	}
	
	
}

