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
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.Button;
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
	private static List<OverlayItem> overlays= new ArrayList<OverlayItem>();
	private static Context context;
	private List<InfoStation> listInfo = new ArrayList<InfoStation>();
	private  static Dialog dialog;
	private static MapView mapView;
	
	public VelibItemizedOverlay(Drawable defaultMarker, Context context, MapView mapView) {
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
	  InfoStation infoStation = listInfo.get(index);
	  
	  
	  try {

		  if(item.getSnippet()!= null &&  item.getTitle()!= null){
			  
			 
		  
	      dialog= new Dialog(context);
	      dialog.setContentView(R.layout.custom_dialog);
	      dialog.setTitle("Détail de station");
 
	      
	      	Dao<StationVelib,Integer> StationVelibDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		    QueryBuilder<StationVelib, Integer> queryBuilder = StationVelibDao.queryBuilder();
			queryBuilder.where().eq(StationVelib.COLUMN_VELIB_ID,infoStation.getStationVelibId());
			PreparedQuery<StationVelib> preparedQuery = queryBuilder.prepare();
			List<StationVelib> listStation = StationVelibDao.query(preparedQuery);
	      
	      TextView name = (TextView) dialog.findViewById(R.id.name);
	      name.setText(listStation.get(0).getName());
	 
	      TextView addr = (TextView) dialog.findViewById(R.id.addr);
	      addr.setText(listStation.get(0).getAddress());
	      
	     
	      TextView total = (TextView) dialog.findViewById(R.id.total);
	      total.setText(String.valueOf(infoStation.getTotal()));
	      
	      TextView free = (TextView) dialog.findViewById(R.id.free);
	      free.setText(String.valueOf(infoStation.getFree()));
	      
 
	      Button btn_itineraire = (Button)dialog.findViewById(R.id.luxian);
	      Button btn_cancel = (Button)dialog.findViewById(R.id.cancel);
	      dialog.show();
	      
	      btn_itineraire.setOnClickListener(this);
	      btn_itineraire.setTag(index);
	      btn_cancel.setOnClickListener(this);
	         
	      
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
			
			new DecodeStationLocationFromInternet(context, infoStation, listStation.get(0), LocationService.getRecentLocation()).execute();
			
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

	


	public static void getInfo(StationVelib station, InfoStation infoStation,  Location recentLocation){
		
		double sLatitude = station.getLatitude();
		double sLongitude = station.getLongitude();
		
		double mLatitude = recentLocation.getLatitude();
		double mLongitude = recentLocation.getLongitude();
		
		 List<Overlay> mapOverlays = mapView.getOverlays();

		 GeoPoint Point_Station = new GeoPoint( (int)  (sLatitude*1E6), (int )(sLongitude*1E6));
		 
		 String Destination = "http://maps.google.com/maps/api/directions/xml?origin="+mLatitude+","+ mLongitude +  
	     	"&destination="+sLatitude +","+sLongitude+"&sensor=false&mode=walking";  
		
		  List<GeoPoint> pointsdestination = GetPoint(Destination);
		  
		  drawRoute(pointsdestination,Color.RED,1);
		 
		 
		
		 
	        Drawable Drawable_pointer = context.getResources().getDrawable(R.drawable.pointer);
	        Drawable Drawable_station = context.getResources().getDrawable(R.drawable.bike);
	        VelibItemizedOverlay itemizedOverlay_pointer = new VelibItemizedOverlay(Drawable_pointer);
	        VelibItemizedOverlay itemizedOverlay_station = new VelibItemizedOverlay(Drawable_station);
	   	 
	     
	        
	        GeoPoint positiona= new GeoPoint((int) (mLatitude*1E6), (int) (mLongitude*1E6));
	   
	        OverlayItem overlayitema =  new OverlayItem(positiona, null,"Ma position actuelle" );
	        OverlayItem overlayitemb =  new OverlayItem(Point_Station, "infoStation", "infoStation");
	        
	        
	        itemizedOverlay_pointer.addOverlay(overlayitema,null);
	        itemizedOverlay_station.addOverlay(overlayitemb, infoStation);
	        mapOverlays.add(itemizedOverlay_pointer);
	        mapOverlays.add(itemizedOverlay_station);
		
		  
	        mapView.getController().setZoom(15);

		 
	}
	
	public static List<GeoPoint> GetPoint(String url){
    	
    	HttpGet get = new HttpGet(url);  
        String strResult = "";  
        
        try {  
    	   
            HttpParams httpParameters = new BasicHttpParams();  
            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);  
            HttpClient httpClient = new DefaultHttpClient(httpParameters);   
              
            HttpResponse httpResponse = httpResponse = httpClient.execute(get);  
              
            if (httpResponse.getStatusLine().getStatusCode() == 200){  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
            }  
        } catch (Exception e) {  
           
              
        }  
          
        if (-1 == strResult.indexOf("<status>OK</status>")){  
            Toast.makeText(context, "echoue de tracer la ligne!", Toast.LENGTH_SHORT).show();  
           // context.finish();  
            
        }  
          
        int pos = strResult.indexOf("<overview_polyline>");  
        pos = strResult.indexOf("<points>", pos + 1);  
        if(pos == -1) return null;
        int pos2 = strResult.indexOf("</points>", pos);  
        strResult = strResult.substring(pos + 8, pos2);  
          
        return decodePoly(strResult); 
        
       // return points;
    	
    }
	
	private static List<GeoPoint> decodePoly(String encoded) {  

		List<GeoPoint> poly = new ArrayList<GeoPoint>();  
		int index = 0, len = encoded.length();  
		int lat = 0, lng = 0;  

		while (index < len) {  
			int b, shift = 0, result = 0;  
			do {  
				b = encoded.charAt(index++) - 63;  
				result |= (b & 0x1f) << shift;  
				shift += 5;  
			} while (b >= 0x20);  
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
			lat += dlat;  

			shift = 0;  
			result = 0;  
			do {  
				b = encoded.charAt(index++) - 63;  
				result |= (b & 0x1f) << shift;  
				shift += 5;  
			} while (b >= 0x20);  
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
			lng += dlng;  

			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),  
					(int) (((double) lng / 1E5) * 1E6));  
			poly.add(p);  
		}  

		return poly;  
	}
	
	
	private static List<Overlay> overlayList;
	public static void drawRoute(List<GeoPoint> points, int couleur, int flag ){  


		Polyline mOverlay = new Polyline(points,couleur);  
		overlayList = mapView.getOverlays();  
		if(flag == 1)overlays.clear();
		overlayList.add(mOverlay);  
		
		if (points.size() >= 2){  
			MapController mapController = mapView.getController();
			mapController.animateTo(points.get(0));  
		}  

		mapView.postInvalidate();  
	}  
		 
	
	
}
