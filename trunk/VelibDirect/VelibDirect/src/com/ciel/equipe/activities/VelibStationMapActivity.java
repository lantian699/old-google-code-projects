package com.ciel.equipe.activities;

import java.sql.SQLException;
import java.util.List;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.ciel.equipe.R;
import com.ciel.equipe.model.DatabaseHelper;
import com.ciel.equipe.model.StationVelib;
import com.ciel.equipe.services.getStationFromInternet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.j256.ormlite.dao.Dao;

public class VelibStationMapActivity extends FragmentActivity implements OnInfoWindowClickListener{

	
	protected static final float MAX_ZOOM_LEVEL = 12.0f;
	protected static final float MY_LOCATION_ZOOM_LEVEL = 14.0f;
	private GoogleMap mMap;
	private UiSettings mUiSetting;
	private Dao<StationVelib, ?> stationDao;
	private List<StationVelib> listStation;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_velib_station_map);
		
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(false);
		ColorDrawable colorDrawable = new ColorDrawable();
		colorDrawable.setColor(Color.rgb(253, 253, 253));
		actionBar.setBackgroundDrawable(colorDrawable);
		
		mMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();	
		mUiSetting = mMap.getUiSettings();
		
		mMap.setMyLocationEnabled(true);
		mMap.setOnInfoWindowClickListener(this);
		mUiSetting.setMyLocationButtonEnabled(true);
		
		
		
		try {
		
			stationDao = DatabaseHelper.getInstance(this).getDao(StationVelib.class);
			listStation = stationDao.queryForAll();
			
			
			if(listStation.size() < 1227){
				
				
				new getStationFromInternet(this).execute();
				listStation = stationDao.queryForAll();
			}
			
			
			 final Handler handler = new Handler();
			  handler.postDelayed(new Runnable(){

			     @Override
			     public void run() {
				
				  if(mMap != null && mMap.getMyLocation() != null) {
					  
					  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
							  new LatLng(mMap.getMyLocation().getLatitude(), mMap.getMyLocation().getLongitude()),MY_LOCATION_ZOOM_LEVEL));
					  
				  }else {
			            
					  handler.postDelayed(this, 500);
			       		}
				     }
			  }, 500);
			
			

			  
			  mMap.setOnCameraChangeListener(new OnCameraChangeListener() {
		            @Override
		            public void onCameraChange(CameraPosition position) {
		                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		                
		                System.out.println(position.zoom);
		                
		                mMap.clear();
		                
		                if(position.zoom > MAX_ZOOM_LEVEL)
		                for(final StationVelib station : listStation){
							
							if( bounds.southwest.latitude<station.getLatitude()&& station.getLatitude() < bounds.northeast.latitude
									&& station.getLongitude()> bounds.southwest.longitude && station.getLongitude() < bounds.northeast.longitude){
														
//								new DecodeStationInfoFromInternet(VelibStationMapActivity.this, station).execute();
								
								mMap.addMarker(new MarkerOptions()
						        .position(new LatLng(station.getLatitude(), station.getLongitude()))
						        .title(station.getName())
						        .snippet(station.getAddress())
						        
						        .icon( BitmapDescriptorFactory.fromResource(R.drawable.pin)));
							
							
							}
							
						}
		                
		            }
		        });
			
			
			  mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					
			
					return false;
				}
			});
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	@Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }
}
