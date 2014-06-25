package com.ciel.equipe.services;

import java.net.Socket;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import com.ciel.equipe.model.InfoStation;
import com.ciel.equipe.model.StationVelib;
import com.ciel.equipe.tools.ParserInfoStation;


public class DecodeStationInfoFromInternet extends AsyncTask<Void, Void, Void>{
	
	

    	private Socket socket;
    	private ProgressDialog Pdialog;
    	private Context context;
    	private StationVelib station;
//    	private MapView mapView;
    	
    	public DecodeStationInfoFromInternet(Context context, StationVelib station){
    		
    		this.context = context;
    		this.station = station;

    	}
    		
//    	
//    	@Override  
//        protected void onPreExecute() {  
//            Pdialog = ProgressDialog.show(context , "Patienter", "en cours de calculer...");
//            
//            
//        }  
//    	
    	protected Void doInBackground(Void... args) {
    		// TODO Auto-generated method stub
    		
    		
//			VelibItemizedOverlay.getInfo(station, infoStation, recentLocation);
//    		Tools.getInfo(context, station, infoStation, recentLocation, mapView);
  	
    		new ParserInfoStation(context, station);
    		return null;
    	}
    	
//    	@Override
//    	 protected void onPostExecute(Void s) {
//             // 
//    		
//    	
//    		Pdialog.dismiss();
//    		
//         }
    

    	
    }


