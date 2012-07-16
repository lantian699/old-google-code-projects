package essai.cnam;


import velib.services.LocationService;
import velib.tools.Tools;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;

public class ListeStationAlentourActivity extends MapActivity implements LocationListener, OnClickListener {

	private MapView mapView;
	private static int Rayon = 10000;
	private Button changeRadius;
	private ImageButton seLocaliser;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_map);
		
		Tools.openGPSSettings(this);

		changeRadius = (Button)findViewById(R.id.change_radius);
		seLocaliser = (ImageButton)findViewById(R.id.selocaliser);
		
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);

		changeRadius.setOnClickListener(this);
		seLocaliser.setOnClickListener(this);
		
		Tools.goToMyLocation(getApplicationContext(), LocationService.getRecentLocation(), mapView);

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

		Tools.goToMyLocation(getApplicationContext(), LocationService.getRecentLocation(), mapView);
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

					
					

				}
			});
			AlertDialog alert = builder.create();
			alert.show();
			
			
			break;
			
			
			
		case R.id.selocaliser:
			
			Tools.goToMyLocation(getApplicationContext(), LocationService.getRecentLocation(), mapView);
			
			
			break;

		default:
			break;
		}
		
	}

	
}
