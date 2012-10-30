package com.capgemini.app.wafasalaf;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.app.wafasalaf.adapters.ListClientAdapter;
import com.capgemini.app.wafasalaf.adapters.SectionedAdapter;
import com.capgemini.app.wafasalaf.managers.ChangeDisplayObserver;
import com.capgemini.app.wafasalaf.managers.ChangeObserver;
import com.capgemini.app.wafasalaf.managers.ListManager;
import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.j256.ormlite.dao.Dao;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ListeClientActivity extends MapActivity implements OnItemClickListener{
	
	public static final String BUNDLE_RECOUVERT = "bundle_recouvert";

	private static final double LATITUDE = 48.857487;

	private static final double LONGITUDE = 2.356567;
	
	private ListView listView;
	private List<Recouvrement> listClient;
	private List<Recouvrement> listClientEnCours;
	private List<Recouvrement> listClientTermine;
	private DatabaseHelper dataHelper;
	private Dao<Recouvrement, ?> listClientDao;
	private SectionedAdapter sectionAdapter;
	private ListClientAdapter enCoursAdapter;
	private ListClientAdapter termineAdapter;

	private ListManager listManager;

	private MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_client);
        listManager = new ListManager();
        
        GeoPoint StationPos = new GeoPoint((int) ( LATITUDE* 1E6),(int) ( LONGITUDE* 1E6));
        
        mapView = (MapView) findViewById(R.id.mapView_ecran_list);
		mapView.setBuiltInZoomControls(true);
		mapView.getController().animateTo(StationPos);
		mapView.getController().setZoom(10);
        
        listView = (ListView)findViewById(R.id.listview_client);
        sectionAdapter = new SectionedAdapter(this, listManager);
        dataHelper = DatabaseHelper.getInstance(this);
       
       
        searchForList(false, -1);
        
        listManager.registerListDisplayChangeObserver(new ChangeDisplayObserver() {
			
			@Override
			public void onChange(boolean isDisplay, int pos) {
				searchForList(isDisplay, pos);	
			}
		});
        
    }

   /* @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	searchForList(false);
    	
    }*/
    
    
    private void searchForList(boolean isDisplay, int pos){
    	
    	 try {
 			listClientDao = dataHelper.getDao(Recouvrement.class);
 			listClient = listClientDao.queryForAll();
 			listClientEnCours = new ArrayList<Recouvrement>();
 			listClientTermine = new ArrayList<Recouvrement>();
 			
 			for (Recouvrement recouvert : listClient) {
 				
 				if(recouvert.getStatut().equals("en cours")){
 					listClientEnCours.add(recouvert);
 				}else if(recouvert.getStatut().equals("termine")) {
 					listClientTermine.add(recouvert);
 				}
 			}
 			
 			
 		} catch (SQLException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
        
		if (pos == 0)
			enCoursAdapter = new ListClientAdapter(this, listClientEnCours,isDisplay);
		else if (pos == 1)
			termineAdapter = new ListClientAdapter(this, listClientTermine,isDisplay);
		else if (pos == -1) {
			enCoursAdapter = new ListClientAdapter(this, listClientEnCours,isDisplay);
			termineAdapter = new ListClientAdapter(this, listClientTermine,isDisplay);
		}
 		sectionAdapter.addSection("VISITE EN COURS", enCoursAdapter);
 		sectionAdapter.addSection("VISITE TERMINEE", termineAdapter);
 		
 		listView.setAdapter(sectionAdapter);
 		listView.setOnItemClickListener(this);
    	
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
		

		Recouvrement recouvert  = (Recouvrement) listView.getItemAtPosition(position);
		Intent intent = new Intent();
		intent.setClass(this, DetailClientRecouvrementActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(BUNDLE_RECOUVERT, recouvert);
		startActivity(intent);
		
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}
