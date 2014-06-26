package com.ciel.equipe.activities;

import java.sql.SQLException;
import java.util.List;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ciel.equipe.R;
import com.ciel.equipe.model.DatabaseHelper;
import com.ciel.equipe.model.InfoStation;
import com.ciel.equipe.model.StationVelib;
import com.ciel.equipe.services.getStationFromInternet;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
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
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.ciel.equipe.services.DecodeStationInfoFromInternet;;

public class VelibStationMapActivity extends FragmentActivity implements OnInfoWindowClickListener{

	
	protected static final float MAX_ZOOM_LEVEL = 12.0f;
	protected static final float MY_LOCATION_ZOOM_LEVEL = 14.0f;
	private GoogleMap mMap;
	private UiSettings mUiSetting;
	private Dao<StationVelib, ?> stationDao;
	private List<StationVelib> listStation;
	private boolean markerClicked;

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
		mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
		
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
		            private Dao<InfoStation, ?> infoStationDao;

					@Override
		            public void onCameraChange(CameraPosition position) {
		                LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
		                
		                try {
		                	if(listStation.size() == 0)
							listStation = stationDao.queryForAll();
						
		                // Eviter d'actualiser le map
		                if(!markerClicked)
		                mMap.clear();
		                
		                markerClicked = false;
		                
		                if(position.zoom > MAX_ZOOM_LEVEL)
		                for(final StationVelib station : listStation){
							
							if( bounds.southwest.latitude<station.getLatitude()&& station.getLatitude() < bounds.northeast.latitude
									&& station.getLongitude()> bounds.southwest.longitude && station.getLongitude() < bounds.northeast.longitude){
														
								new DecodeStationInfoFromInternet(VelibStationMapActivity.this, station).execute();
								
								
								infoStationDao = DatabaseHelper.getInstance(VelibStationMapActivity.this).getDao(InfoStation.class);
								
								
								handler.postDelayed(new Runnable(){

								     @Override
								     public void run() {
								    	 try {
								    	 
								    		 QueryBuilder<InfoStation, ?> queryBuilder = infoStationDao.queryBuilder();
								    		 queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
								    		 PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
								    		 List<InfoStation> infoList = infoStationDao.query(preparedQuery);								
										 
										
									  if(infoList.size() !=0) {
										  
										  InfoStation stationInfo = infoList.get(0);
										  
										  MarkerOptions markerOption = new MarkerOptions();
										  markerOption.position(new LatLng(station.getLatitude(), station.getLongitude()));
										  markerOption.title(station.getName());
										  markerOption.snippet("Available : "+ stationInfo.getAvailable() + "  Free Place : "+ stationInfo.getFree()); 
										  if(stationInfo.getOpen())
										  markerOption.icon( BitmapDescriptorFactory.fromResource(R.drawable.picto_normal));
										  else
											  markerOption.icon( BitmapDescriptorFactory.fromResource(R.drawable.picto_closed));
								
										  mMap.addMarker(markerOption);
										  
									  }else {
								            
										  handler.postDelayed(this, 10);
								       		}
								    	 } catch (SQLException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
								     }	 
								     
								  }, 50);
								
								
								
							
							
							}
							
						}
		                
		                } catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		                
		            }
		                
					
		        });
			
			
			  mMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				
				

				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					
					markerClicked = true;
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
		
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		Uri.parse("http://maps.google.com/maps?saddr="+mMap.getMyLocation().getLatitude()+","+mMap.getMyLocation().getLongitude()
			    		+"&daddr="+marker.getPosition().latitude+","+marker.getPosition().longitude));
		startActivity(intent);
//        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }
	
	
	
	/** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements InfoWindowAdapter {
    	

		private View mWindow;


		CustomInfoWindowAdapter() {
            mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
    	}
    	
    	
    	 @Override
         public View getInfoWindow(Marker marker) {
            
             render(marker, mWindow);
             return mWindow;
         }
    	 
    	 @Override
 		public View getInfoContents(Marker arg0) {
 			// TODO Auto-generated method stub
 			return null;
 		}
         
         
         private void render(Marker marker, View view) {
             int badge;
             // Use the equals() method on a Marker to check for equals.  Do not use ==.
           
             ((ImageView) view.findViewById(R.id.badge)).setImageResource(R.drawable.ic_location_directions);

             String title = marker.getTitle();
             TextView titleUi = ((TextView) view.findViewById(R.id.title));
             if (title != null) {
                 // Spannable string allows us to edit the formatting of the text.
                 SpannableString titleText = new SpannableString(title);
                 titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                 titleUi.setText(titleText);
             } else {
                 titleUi.setText("");
             }

             String snippet = marker.getSnippet();
             
             TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
             if (snippet != null && snippet.length() > 12) {
                 SpannableString snippetText = new SpannableString(snippet);
                 snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                 snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, 15, 0);
                 snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 15, 26, 0);
                 snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 27, snippet.length(), 0);
                 snippetUi.setText(snippetText);
             } else {
                 snippetUi.setText("");
             }
         }


		
         
         
    }
	
}
