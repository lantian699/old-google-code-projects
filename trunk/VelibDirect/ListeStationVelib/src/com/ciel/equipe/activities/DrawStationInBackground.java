package com.ciel.equipe.activities;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ciel.equipe.model.StationVelib;
import com.ciel.equipe.services.LocationService;
import com.ciel.equipe.tools.Tools;
import com.google.android.maps.MapView;

public class DrawStationInBackground extends AsyncTask<Void, Void, Void>{

	ProgressDialog dialog;
	private List<StationVelib> listStationSelect;
	private Activity context;
	private MapView mapView;
	private int Rayon;
	
	public DrawStationInBackground(Activity context, MapView mapView, int Rayon){
		
		this.context = context;
		this.mapView = mapView;
		this.Rayon = Rayon;
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		dialog = ProgressDialog.show(context, "", "En cours de calculer");
		mapView.getOverlays().clear();

	}

	@Override
	protected Void doInBackground(Void... params) {

		

		listStationSelect = Tools.Calculateur_Station_Prox(context, LocationService.getRecentLocation(), Rayon);

		
		Tools.DrawStationsOnMap(context, listStationSelect, mapView);
		
		
		Tools.goToMyLocation(context, LocationService.getRecentLocation(), mapView);	
		
		mapView.postInvalidate();
		
		
		return null;
		
		
			
	

	}

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);


		
		if (listStationSelect.size() > 0) {

			
			Toast.makeText(
					context,
					String.valueOf( "Il y a " + listStationSelect.size()+ " station(s) autour de vous dans un rayon de " + Rayon +"m"),
					Toast.LENGTH_LONG).show();
		} else {

			
			Toast.makeText(
					context,
					"Il n'y a pas de Velib autour de vous dans un rayon de " + Rayon + "m",
					Toast.LENGTH_LONG).show();
		}
		dialog.dismiss();
	}


	

}
