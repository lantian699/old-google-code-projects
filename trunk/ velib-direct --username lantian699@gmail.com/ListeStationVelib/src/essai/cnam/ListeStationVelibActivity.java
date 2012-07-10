package essai.cnam;

import velib.model.*;
import velib.tools.ParserListVelib;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;



import essai.cnam.R;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.j256.ormlite.dao.Dao;

public class ListeStationVelibActivity extends ListActivity {
	
	 InputStream is;
	 ArrayList<String> listStation =new ArrayList<String>(); ;
	 StationVelib station;
	 ListView listView;
	private Dao<StationVelib, ?> VelibStationDao;
	private List<StationVelib> listVelib;
	 
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  
	  
	  
	  try {
		  
		  
			VelibStationDao = DatabaseHelper.getInstance(getApplicationContext()).getDao(StationVelib.class);
			listVelib = VelibStationDao.queryForAll();
			
			for(StationVelib station : listVelib){
				
				listStation.add(station.getName());
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  
	
	  setListAdapter(
			  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listStation));

	  
	  ListView listV = (ListView)ListeStationVelibActivity.this.findViewById(android.R.id.list);
	  listV.setOnItemClickListener(new ItemClickListener());

	  
	}

	/*
	private class AddStringTask extends AsyncTask<Void, String, Void> {
		
		@Override
		protected void onPreExecute (){
			
			ProgressDialog dialog = ProgressDialog.show(ListeStationVelibActivity.this, "", 
                    "Loading. Please wait...", true);
			
		}
			
		
		
	
	@Override
	protected Void doInBackground(Void... unused) {
	  try {
			
             //   stations = 
		  
	
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	  for (String  item : stations) {
	    publishProgress(item);
	    //SystemClock.sleep(200);
	  }

	  return(null);
	}

	@Override
	protected void onProgressUpdate(String... item) {
	   ((ArrayAdapter)getListAdapter()).add(item[0]);
	}

	@Override
	protected void onPostExecute(Void unused) {
	  Toast.makeText(ListeStationVelibActivity.this, "Appuyer sur bouton MENU depuis 2 secondes pour chercher les stations", Toast.LENGTH_LONG).show();
	  ListView listV = (ListView)ListeStationVelibActivity.this.findViewById(android.R.id.list);
	  listV.setOnItemClickListener(new ItemClickListener());
	  listV.setTextFilterEnabled(true);
	}
	
  }
*/
	
	
	

	private class ItemClickListener implements OnItemClickListener{
	 
	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		
	//	String item = (String)parent.getItemAtPosition(position);
		StationVelib st = listVelib.get(position);
		System.out.println("listVelib = " + listVelib.get(position) );
		InfoStation info = new InfoStation(st.getNumber(),getApplicationContext());
		
		
		Intent intent = new Intent(getBaseContext(),InfoStationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("addr", st.getName());
		bundle.putDouble("latitude",  st.getLatitude());
		bundle.putDouble("longitude",  st.getLongitude());
		bundle.putInt("available", info.getAvailable());
		bundle.putInt("free", info.getFree());
		intent.putExtras(bundle);
		startActivity(intent);
		
		//Toast.makeText(ListeStationVelibActivity.this, String.valueOf("{"+st.getLatitude()+", "+st.getLongitude()+", "+info.getAvailable()+", "+info.getFree()+"}"), Toast.LENGTH_SHORT).show();
		
	}
	
	
	
	
	  
  }

  
 
	
	
}