package com.capgemini.app.wafasalaf.tools;

import com.capgemini.app.wafasalaf.models.DatabaseHelper;
import com.capgemini.app.wafasalaf.models.ModelObjet;
import com.capgemini.app.wafasalaf.models.Recouvrement;
import com.google.android.maps.MapView;

import android.content.Context;
import android.os.AsyncTask;

public class UpdateMapPoint extends AsyncTask<Void, Void, Void>{

	private ModelObjet objet;
	private Context context;
	private MapView mapView;
	private DatabaseHelper dataHelper;

	public UpdateMapPoint (Context context, MapView mapView, DatabaseHelper dataHelper, ModelObjet objet){
		this.context = context;
		this.objet = objet;
		this.mapView = mapView;
		this.dataHelper = dataHelper;
	}

	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		
		Tools.DrawOneStationOnMap(context, mapView, (Recouvrement)objet, dataHelper);
		return null;
	}
	
	

	

}
