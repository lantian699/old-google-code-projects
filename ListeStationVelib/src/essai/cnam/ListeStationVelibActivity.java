package essai.cnam;

import java.sql.SQLException;
import java.util.List;

import velib.activitymodel.FirstScreenActivity;
import velib.activitymodel.SecondScreenActivity;
import velib.model.DatabaseHelper;
import velib.model.StationVelib;
import velib.tools.Log;
import velib.tools.Tools;
import velib.views.ToolBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.R;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.j256.ormlite.dao.Dao;

public class ListeStationVelibActivity extends SecondScreenActivity implements TextWatcher {
	
	

	private ListView listView;
	private Dao<StationVelib, Integer> VelibStationDao;
	private List<StationVelib> listVelib;
	private AutoCompleteTextView searchStation;
	private Activity context;
	
	 
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  setContentView(R.layout.act_list_station_velib);
	  
	  AdView adView = (AdView)this.findViewById(R.id.ad_list); // show the advertisement
	    adView.loadAd(new AdRequest());
	    
	  Toast.makeText(this, "Cochez la case pour choisir votre station préférée", Toast.LENGTH_LONG).show();
	  
	  
	  
	  ToolBar.setHighLight(this, ToolBar.HIGHLIGHT_LIST);
	  listView = (ListView) findViewById(R.id.listprincipal);

	  
	  
	  try {
					  
					  
					VelibStationDao = DatabaseHelper.getInstance(getApplicationContext()).getDao(StationVelib.class);
					listVelib = VelibStationDao.queryForAll();
					Log.i(this, "StationVelib size = "+ listVelib.size());
						
					listView.setAdapter(new AdapterListPrincipal(this, listVelib));

					
			  	} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
  
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		if(searchStation != null)
		searchStation.setText("");
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar

		menu.add("Search")
        .setIcon(R.drawable.ic_search_inverse)
        .setActionView(R.layout.collapsible_edittext)
        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	
        

        return super.onCreateOptionsMenu(menu);
    }

	
	
	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
	        // If this callback does not handle the item click, onPerformDefaultAction
	        // of the ActionProvider is invoked. Hence, the provider encapsulates the
	        // complete functionality of the menu item.
		 
		 if(item.getActionView() instanceof AutoCompleteTextView){
		 searchStation =(AutoCompleteTextView)item.getActionView().findViewById(R.id.autoComplete_search_station);
		 searchStation.addTextChangedListener(this);
		 }
		 
		 
		return false;
	       
	    }
	

	/*@Override
	public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
		
		
	//	String item = (String)parent.getItemAtPosition(position);
		StationVelib station = listVelib.get(position);
		System.out.println("listVelib = " + listVelib.get(position) );
		InfoStation info = new InfoStation(station.getNumber(),context);
		
		
		Intent intent = new Intent(getBaseContext(),InfoStationActivity.class);
		intent.putExtra("station", station);
		startActivity(intent);
		
		//Toast.makeText(ListeStationVelibActivity.this, String.valueOf("{"+st.getLatitude()+", "+st.getLongitude()+", "+info.getAvailable()+", "+info.getFree()+"}"), Toast.LENGTH_SHORT).show();

	
	
	
	
	  
  }*/
	 
	 
	 @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
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
		
		Tools.setNewListForSearchModule(listView, this, s, listVelib);
		
	}

	
  
 
	
	
}