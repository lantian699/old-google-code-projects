package velib.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import velib.services.DecodeStationLocationFromInternet;
import velib.services.LocationService;
import velib.tools.Tools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import essai.cnam.R;

//
// http://developer.android.com/resources/tutorials/views/hello-mapview.html
//
public  class VelibItemizedOverlay extends ItemizedOverlay<OverlayItem> implements OnClickListener{
	private  List<OverlayItem> overlays= new ArrayList<OverlayItem>();
	private static  Activity context;
	private  List<InfoStation> listInfo = new ArrayList<InfoStation>();
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
	
	public void addOverlay(OverlayItem overlay, InfoStation infoStation) {
		if(infoStation != null)
		listInfo.add(infoStation);
		
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

	  
	  
	  try {

		  if(item.getSnippet()!= null &&  item.getTitle()!= null){
			  
			  InfoStation infoStation = listInfo.get(index);
			 
		  
	      dialog= new Dialog(context);
	      dialog.setContentView(R.layout.custom_dialog);
	      dialog.setTitle("Détail de station");
 
	      
	      	final Dao<StationVelib,Integer> StationVelibDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		    QueryBuilder<StationVelib, Integer> queryBuilder = StationVelibDao.queryBuilder();
			queryBuilder.where().eq(StationVelib.COLUMN_VELIB_ID,infoStation.getStationVelibId());
			PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
			List<StationVelib> listStation = StationVelibDao.query(preparedQuery);
			final StationVelib station = listStation.get(0);
			
			
	      TextView name = (TextView) dialog.findViewById(R.id.name);
	      name.setText(station.getName());
	 
	      TextView addr = (TextView) dialog.findViewById(R.id.addr);
	      addr.setText(station.getAddress());
	      
	     
	      TextView total = (TextView) dialog.findViewById(R.id.total);
	      total.setText(String.valueOf(infoStation.getTotal()));
	      
	      TextView free = (TextView) dialog.findViewById(R.id.free);
	      free.setText(String.valueOf(infoStation.getFree()));
	      
	      CheckBox cb_dialog = (CheckBox) dialog.findViewById(R.id.cb_dialog);
	      
	      if(station.getIsPrefered() == 1)
	    	  cb_dialog.setChecked(true);
	      else
	    	  cb_dialog.setChecked(false);
	      
 
	      Button btn_itineraire = (Button)dialog.findViewById(R.id.luxian);
	      Button btn_cancel = (Button)dialog.findViewById(R.id.cancel);
	      dialog.show();
	      
	      btn_itineraire.setOnClickListener(this);
	      btn_itineraire.setTag(index);
	      btn_cancel.setOnClickListener(this);
	      
	      cb_dialog.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
	
				Tools.marquerIsPrefered(context, station, isChecked);
			}
				
		});
	         
	      
	  }
	  else{
		  
		   AlertDialog.Builder Adialog = new AlertDialog.Builder(context);
		   //Adialog.setTitle(item.getTitle());
		   Adialog.setMessage(item.getSnippet());
		   Adialog.show();
		  
	  }
	  
	  }catch (Exception e) {
		// TODO: handle exception
		  
		  e.printStackTrace();
		  
	  }
	  
	  return true;
	}
	
	

	@Override
	public void onClick(View v) {

		InfoStation infoStation;
		switch (v.getId()) {
		case R.id.luxian:
			try{
			int index = (Integer) dialog.findViewById(R.id.luxian).getTag();
			
			infoStation = listInfo.get(index);
			
			Dao<StationVelib,Integer> StationVelibDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		    QueryBuilder<StationVelib, Integer> queryBuilder = StationVelibDao.queryBuilder();
			queryBuilder.where().eq(StationVelib.COLUMN_VELIB_ID,infoStation.getStationVelibId());
			PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
			List<StationVelib> listStation = StationVelibDao.query(preparedQuery);
			
			new DecodeStationLocationFromInternet(context, infoStation, listStation.get(0), LocationService.getRecentLocation(),mapView).execute();
			
			dialog.dismiss();
			
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			break;
			
		case R.id.cancel:
			
			dialog.dismiss();
			
			break;

		default:
			break;
		}
		
		
	}

	


	
	
}
