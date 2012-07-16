package velib.services;

import java.net.Socket;

import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.model.VelibItemizedOverlay;

import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;


public class DecodeStationLocationFromInternet extends AsyncTask<Void, Void, Void>{
	
	

    	private Socket socket;
    	private ProgressDialog Pdialog;
    	private Context context;
    	private StationVelib station;
    	private InfoStation infoStation;
    	private Location recentLocation;
    	
    	public DecodeStationLocationFromInternet(Context context, InfoStation infoStation, StationVelib station, Location recentLocation){
    		
    		this.context = context;
    		this.station = station;
    		this.infoStation = infoStation;
    		this.recentLocation = recentLocation;
    		
    	}
    		
    	
    	@Override  
        protected void onPreExecute() {  
            Pdialog = ProgressDialog.show(context , "Patienter", "en cours de calculer...");
            
            
        }  
    	
    	protected Void doInBackground(Void... args) {
    		// TODO Auto-generated method stub
    		
    		
			VelibItemizedOverlay.getInfo(station, infoStation, recentLocation);
  	
    		return null;
    	}
    	
    	@Override
    	 protected void onPostExecute(Void s) {
             // 返回HTML页面的内容
    		
    	
    		Pdialog.dismiss();
    		
         }
    

    	
    }


