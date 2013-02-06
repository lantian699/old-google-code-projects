package com.alstom.lean.all.activities;


import java.util.ArrayList;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;


import com.alstom.lean.all.R;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.tools.Tools;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class LocationUsineActivity extends MapActivity implements LocationListener{
	
	
	private MapView mapView;
	private ArrayList<Factory> listFactory;


	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_location_usine);
		
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		
		mapView.getController().setZoom(12);
		
		
		Bundle bundle = getIntent().getExtras();
		listFactory = (ArrayList<Factory>) bundle.getSerializable(MyProjectModeTabletActivity.NAME_BUNDLE_LIST_FACTORY);
		
		for(Factory factory : listFactory)
		Tools.DrawOneStationOnMap(this, factory, mapView);
		
		
	}
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
