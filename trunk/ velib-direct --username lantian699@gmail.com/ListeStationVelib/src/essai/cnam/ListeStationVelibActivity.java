package essai.cnam;

import velib.model.*;

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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;



import essai.cnam.R;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class ListeStationVelibActivity extends ListActivity {
	 ListeDesStationsVelib stations ;
	 InputStream is;
	 ArrayList<String> list =new ArrayList<String>(); ;
	 StationVelib station;
	 ListView listView;
	 
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.main);
	  
	  AdView adView = (AdView)this.findViewById(R.id.ad); // show the advertisement
      adView.loadAd(new AdRequest());
	 // listView = (ListView)this.findViewById(R.id.list1);
      
	  //listView = getListView();
	  //listView.setTextFilterEnabled(true);
	  try {
		is = ListeStationVelibActivity.this.getAssets().open("stations.xml");
		  
		  
		stations = new ListeDesStationsVelib(is, getApplicationContext());
		
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 
	
	
	list= stations.getList();
	
	  setListAdapter(
			  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list));

	  new AddStringTask().execute();
	  
	}

	
	private class AddStringTask extends AsyncTask<Void, String, Void> {
		
		@Override
		protected void onPreExecute (){
			
			/*ProgressDialog dialog = ProgressDialog.show(ListeStationVelibActivity.this, "", 
                    "Loading. Please wait...", true);*/
			
		}
			
		
		
		
	@Override
	protected Void doInBackground(Void... unused) {
	  try {
			
             //   stations = 
		  
	
 
		} catch (Exception e) {
			e.printStackTrace();
		}
	/*	
	  for (String  item : stations) {
	    publishProgress(item);
	    //SystemClock.sleep(200);
	  }*/

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

  private class ItemClickListener implements OnItemClickListener{
	 
	@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		String item = (String)parent.getItemAtPosition(position);
		StationVelib st = stations.lireStation(item);
		InfoStation info = stations.info(st);
		System.out.println("satation = "+info.getAvailable());
		
		Intent intent = new Intent(getBaseContext(),InfoStationActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("addr", st.getAddress());
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