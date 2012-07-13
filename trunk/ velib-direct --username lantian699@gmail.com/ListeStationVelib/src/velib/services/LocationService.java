package velib.services;

import essai.cnam.ListeStationAlentourActivity;
import essai.cnam.LogoActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.util.Log;

public class LocationService extends Service implements LocationListener,
		Runnable {

	private LocationManager locationManager;
	private Location mostRecentLocation;
	private HandlerThread myHandlerThread;
	private Handler handler;
	private boolean isContinous = true;
	private LogoActivity ecran_accueil = new LogoActivity();
	private ListeStationAlentourActivity listAlentourActivity = new ListeStationAlentourActivity();
	
	private double latitude;
	private double longitude;
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		this.isContinous = true;
		myHandlerThread = new HandlerThread("LocationService");
		myHandlerThread.start();

		handler = new Handler(myHandlerThread.getLooper());
		handler.post(this);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		while (isContinous) {

			getLocation();
			
		//	ecran_accueil.setLocationInfo(latitude, longitude);

			System.out.println("TestMapActivity latitude = " + latitude + "  longitude = " +longitude);
			
			listAlentourActivity.goToMyLocation(latitude, longitude);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void getLocation() {
		// TODO Modify user localization according to this article:
		// http://developer.android.com/guide/topics/location/obtaining-user-location.html

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_HIGH);
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
		latitude = mostRecentLocation.getLatitude();
		longitude = mostRecentLocation.getLongitude();
		
		Log.i("LocationService", " Latitude = " + latitude + "  Longitude = " + longitude);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		this.isContinous = false;

		handler.removeCallbacks(this);
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
