package com.alstom.power.lean.tools;

import java.util.List;

import com.alstom.power.lean.R;
import com.alstom.power.lean.models.Factory;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class Tools {
	
	
	public static void  DrawOneStationOnMap(Activity context,Factory factory , MapView mapView) {

		List<Overlay> mapOverlays = mapView.getOverlays();
		
		
		Drawable drawable = context.getResources().getDrawable(R.drawable.pointer);
		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);

				GeoPoint StationPos = new GeoPoint((int) (factory.getLatitude() * 1E6),(int) (factory.getLongitude() * 1E6));
		
				
				OverlayItem overlayitem = new OverlayItem(StationPos, context.getString(R.string.title_dialog_factory),factory.getName()+"\n"+factory.getAddress());

				velibItemizedOverlay.addOverlay(overlayitem);
				
				mapOverlays.add(velibItemizedOverlay);
			
				mapView.postInvalidate();
			
				MapController mapController = mapView.getController();
				mapController.animateTo(StationPos);
				mapController.setZoom(12);
			



	}

}
