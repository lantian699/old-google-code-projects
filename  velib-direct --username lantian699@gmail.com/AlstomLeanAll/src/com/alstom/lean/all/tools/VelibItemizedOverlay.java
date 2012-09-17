package com.alstom.lean.all.tools;



import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

//
// http://developer.android.com/resources/tutorials/views/hello-mapview.html
//
public  class VelibItemizedOverlay extends ItemizedOverlay<OverlayItem>{
	private  List<OverlayItem> overlays= new ArrayList<OverlayItem>();
	private static  Activity context;
	private   Dialog dialog;
	private  MapView mapView;
	
	public VelibItemizedOverlay(Drawable defaultMarker, Activity context, MapView mapView) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
		this.mapView = mapView;
	
	}
	
	public VelibItemizedOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}
	
	public void addOverlay(OverlayItem overlay) {
		
		overlays.add(overlay);
	    populate();
	}
	
	@Override
	protected OverlayItem createItem(int i) {
	  return overlays.get(i);
	}
	
	@Override
	public int size() {
	  return overlays.size();
	}

	
	
	
	@Override
	protected boolean onTap(int index) {
		
	  OverlayItem item = overlays.get(index);
	  AlertDialog.Builder Adialog = new AlertDialog.Builder(context);
	  Adialog.setTitle(item.getTitle());
	  Adialog.setMessage(item.getSnippet());
	  Adialog.show();
		     
	  return true;
	}
	
	
}
