package essai.cnam;


import velib.model.StationVelib;
import velib.model.VelibItemizedOverlay;
import velib.services.LocationService;
import velib.tools.Tools;
import android.os.Bundle;
import android.view.View;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;



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
		setContentView(R.layout.act_list_alentour_map);
		
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