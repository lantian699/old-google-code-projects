package essai.cnam;


import velib.model.StationVelib;
import velib.model.VelibItemizedOverlay;
import velib.services.LocationService;
import velib.tools.Tools;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;



public class InfoStationActivity extends MapActivity implements OnClickListener {
	
	MapView mapView;
	Double latitude;
	Double longitude;
	int  available;
	int free;
	String addr;
	private Button zoomup;
	private Button zoomdown;
	VelibItemizedOverlay itemOverlay;
	private ImageButton seLocaliser;
	private static int ZOOM = 15;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_list_alentour_map);
		
		 AdView adView = (AdView)this.findViewById(R.id.adMap); // show the advertisement
	     adView.loadAd(new AdRequest());
	     
	     findViewById(R.id.change_radius).setVisibility(View.GONE);

	     StationVelib station = (StationVelib) getIntent().getSerializableExtra("station");
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		seLocaliser = (ImageButton)findViewById(R.id.selocaliser);
		zoomup = (Button) findViewById(R.id.zoomup);
		zoomdown = (Button) findViewById(R.id.zoomdown);
		
		
		
		zoomup.setBackgroundColor(Color.argb(125, 0, 0, 0));
		zoomdown.setBackgroundColor(Color.argb(125, 0, 0, 0));
		seLocaliser.setBackgroundColor(Color.argb(125, 0, 0, 0));
		
		
		zoomup.setOnClickListener(this);
		zoomdown.setOnClickListener(this);
		seLocaliser.setOnClickListener(this);
		
		Tools.DrawOneStationOnMap(this, station, mapView);
		Tools.goToMyLocation(this, LocationService.getRecentLocation(), mapView);
		
		mapView.getController().setZoom(12);
		mapView.setBuiltInZoomControls(false);

}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
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
}