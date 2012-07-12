package com.ciel.equipe.test;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service implements LocationListener{

	private LocationManager locationManager;
	private Location mostRecentLocation;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		
		return null;
	}
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		getLocationPermanent.run();
	}
	
	
	Thread getLocationPermanent = new Thread() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while(true){
				
				
				getLocation();
				
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
	};

	
	private void getLocation() {
		// TODO Modify user localization according to this article:
		// http://developer.android.com/guide/topics/location/obtaining-user-location.html

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);
		// In order to make sure the device is getting the location, request
		// updates.
		locationManager.requestLocationUpdates(provider, 1l, 0f, this);
		mostRecentLocation = locationManager.getLastKnownLocation(provider);
		if (mostRecentLocation == null) {
			mostRecentLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (mostRecentLocation == null) {
				Log.i("location", "Unknown Location!");
			}
			
		}
		Log.i("LocationService", " Latitude = " + mostRecentLocation.getLatitude()+"  Longitude = "+ mostRecentLocation.getLongitude());
	}

	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		getLocationPermanent.stop();
		locationManager.removeUpdates(this);
		
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
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
	
}
