package essai.cnam;

import java.sql.SQLException;
import java.util.List;

import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.tools.Log;
import velib.tools.Tools;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;

import com.j256.ormlite.dao.Dao;

public class ListeStationVelibActivity extends Activity implements TextWatcher {
	
	

	private ListView listView;
	private Dao<StationVelib, Integer> VelibStationDao;
	private List<StationVelib> listVelib;
	private AutoCompleteTextView searchStation;
	
	 
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  
	  
	  searchStation =(AutoCompleteTextView) findViewById(R.id.autoComplete_search_station);
	  listView = (ListView) findViewById(R.id.listprincipal);
	  
	  try {
		  
		  
			VelibStationDao = DatabaseHelper.getInstance(getApplicationContext()).getDao(StationVelib.class);
			listVelib = VelibStationDao.queryForAll();
			Log.i(this, "StationVelib size = "+ listVelib.size());
				
			listView.setAdapter(new AdapterListPrincipal(this, listVelib));
	
			listView.setOnItemClickListener(new ItemClickListener());
	
			searchStation.addTextChangedListener(this);
		
	  	} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	  
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		searchStation.setText("");
	}


	private class ItemClickListener implements OnItemClickListener{
	 
	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		
	//	String item = (String)parent.getItemAtPosition(position);
		StationVelib st = listVelib.get(position);
		System.out.println("listVelib = " + listVelib.get(position) );
		InfoStation info = new InfoStation(st.getNumber(),getApplicationContext());
		
		
		Intent intent = new Intent(getBaseContext(),InfoStationActivity.class);
		intent.putExtra("station", st);
		startActivity(intent);
		
		//Toast.makeText(ListeStationVelibActivity.this, String.valueOf("{"+st.getLatitude()+", "+st.getLongitude()+", "+info.getAvailable()+", "+info.getFree()+"}"), Toast.LENGTH_SHORT).show();
		
	}
	
	
	
	
	  
  }

	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub
		
		Tools.setNewListForSearchModule(listView, getApplicationContext(), s, listVelib);
		
	}

  
 
	
	
}