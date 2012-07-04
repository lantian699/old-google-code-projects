package essai.cnam;


import java.util.List;

import velib.model.VelibItemizedOverlay;

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
		
		//intent rcv = getIntent().getIntExtra("latitude", defaultValue);
		
		mapView = (MapView) this.findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		
		Bundle bundle = new Bundle();
        bundle = this.getIntent().getExtras();
		
        addr = bundle.getString("addr");
		latitude = bundle.getDouble("latitude");
		longitude = bundle.getDouble("longitude");
		available = bundle.getInt("available");
		free = bundle.getInt("free");
		
		List<Overlay> mapOverlays = mapView.getOverlays();
        Drawable drawable = this.getResources().getDrawable(R.drawable.bike);
		itemOverlay= new VelibItemizedOverlay(drawable,this);
		
		GeoPoint maposition= new GeoPoint((int) (latitude *1E6), (int) (longitude*1E6));
		OverlayItem overlayitem = new OverlayItem(maposition, addr, String.valueOf(available)+" emplacement libre et "+ String.valueOf(free)+"velo disponibles.");
		itemOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemOverlay);
		MapController mapController = mapView.getController();
		 mapController.animateTo(maposition);
         mapController.setZoom(15);

}
	
	

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
}