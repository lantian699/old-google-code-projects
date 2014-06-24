package com.ciel.equipe.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.actionbarsherlock.R;
import com.ciel.equipe.services.LocationService;
import com.ciel.equipe.tools.Tools;
import com.ciel.equipe.views.ToolBar;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class ListeStationAlentourActivity extends MapActivity implements LocationListener, OnClickListener {

	private MapView mapView;
	private static int Rayon = 500;
	private Button changeRadius;
	private ImageButton seLocaliser;
	private Button zoomup;
	private Button zoomdown;
	
	private static int ZOOM = 15;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_list_alentour_map);
		
		Tools.openGPSSettings(this);
		
		AdView adView = (AdView)this.findViewById(R.id.adMap); // show the advertisement
	    adView.loadAd(new AdRequest());
		
		ToolBar.setHighLight(this, ToolBar.HIGHLIGHT_GPS_STATION);

//		startService(new Intent(this ,LocationService.class));
		
		changeRadius = (Button)findViewById(R.id.change_radius);
		seLocaliser = (ImageButton)findViewById(R.id.selocaliser);
		zoomup = (Button) findViewById(R.id.zoomup);
		zoomdown = (Button) findViewById(R.id.zoomdown);
		zoomup.setBackgroundColor(Color.argb(125, 0, 0, 0));
		zoomdown.setBackgroundColor(Color.argb(125, 0, 0, 0));
		changeRadius.setBackgroundColor(Color.argb(125, 0, 0, 0));
		seLocaliser.setBackgroundColor(Color.argb(125, 0, 0, 0));

		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(false);
		
		changeRadius.setText(String.valueOf(Rayon)+"M");
		changeRadius.setOnClickListener(this);
		seLocaliser.setOnClickListener(this);
		zoomup.setOnClickListener(this);
		zoomdown.setOnClickListener(this);

		new DrawStationInBackground(this, mapView, Rayon).execute();
		

	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return true;
	}

	

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

		//Tools.goToMyLocation(getApplicationContext(), LocationService.getRecentLocation(), mapView);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.change_radius:
			
			final CharSequence[] items = { "500 M", "1000 M", "1500 M" };
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Rayon de Cherche");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {		
				
					

					if (item == 0 && Rayon != 500){
						Rayon = 500;
						
						new DrawStationInBackground(ListeStationAlentourActivity.this, mapView, Rayon).execute();
					}
					
					if (item == 1 && Rayon !=1000){
						Rayon = 1000;
						new DrawStationInBackground(ListeStationAlentourActivity.this, mapView, Rayon).execute();
					}
					if (item == 2 && Rayon != 1500){
						Rayon = 1500;
						new DrawStationInBackground(ListeStationAlentourActivity.this, mapView, Rayon).execute();
					}

					
					changeRadius.setText(String.valueOf(Rayon)+"M");

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			
			
			break;
			
			
			
		case R.id.selocaliser:
			
			Tools.goToMyLocation(this, LocationService.getRecentLocation(), mapView);
			
			
			break;
			
		case R.id.zoomup:
			
			mapView.getController().setZoom(ZOOM+1);
			ZOOM = ZOOM+1;
			
			break;
			
		case R.id.zoomdown:
			
			mapView.getController().setZoom(ZOOM-1);
			
			ZOOM = ZOOM-1;
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, EcranAccueilActivity.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
		
		
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
//		stopService(new Intent(this, LocationService.class));
	}
	
}
