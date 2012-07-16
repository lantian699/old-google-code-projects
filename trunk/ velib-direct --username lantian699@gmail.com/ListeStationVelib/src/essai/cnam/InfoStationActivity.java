package essai.cnam;


import java.util.List;

import velib.model.StationVelib;
import velib.model.VelibItemizedOverlay;
import velib.services.LocationService;
import velib.tools.Tools;

import essai.cnam.R;
import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;



public class InfoStationActivity extends MapActivity {
	
	MapView mapView;
	Double latitude;
	Double longitude;
	int  available;
	int free;
	String addr;
	
	VelibItemizedOverlay itemOverlay;

	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_map);
		
		 AdView adView = (AdView)this.findViewById(R.id.adMap); // show the advertisement
	     adView.loadAd(new AdRequest());
	     
	     findViewById(R.id.change_radius).setVisibility(View.GONE);

	     StationVelib station = (StationVelib) getIntent().getSerializableExtra("station");
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		
		Tools.DrawOneStationOnMap(this, station, mapView);
		Tools.goToMyLocation(this, LocationService.getRecentLocation(), mapView);
		
		mapView.getController().setZoom(12);
	

}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}