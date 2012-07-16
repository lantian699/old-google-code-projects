package velib.model;

import java.util.ArrayList;
import java.util.List;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import essai.cnam.R;

//
// http://developer.android.com/resources/tutorials/views/hello-mapview.html
//
public  class VelibItemizedOverlay extends ItemizedOverlay<OverlayItem> implements OnClickListener{
	private List<OverlayItem> overlays= new ArrayList<OverlayItem>();
	private Context context;
	private List<InfoStation> listInfo = new ArrayList<InfoStation>();
	private  static Dialog dialog;
	
	public VelibItemizedOverlay(Drawable defaultMarker, Context context) {
		super(boundCenterBottom(defaultMarker));
		this.context = context;
	}
	
	public void addOverlay(OverlayItem overlay, InfoStation infoStation) {
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
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.luxian:
			
			System.out.println("hahahahah");
			break;
			
		case R.id.cancel:
			
			dialog.dismiss();
			
			break;

		default:
			break;
		}
		
		
	}

	


	/*private void getInfo(String name, String addr){
		 List<Overlay> mapOverlays = mapView.getOverlays();
		 
		
		isFini =false;
		 GeoPoint geopoint=new GeoPoint( (int)  (stalat*1E6), (int )(stalong*1E6));
		 
		 String Destination = "http://maps.google.com/maps/api/directions/xml?origin="+monlat+","+monlong +  
	     	"&destination="+stalat +","+stalong+"&sensor=false&mode=walking";  
		
		  List<GeoPoint> pointsdestination = GetPoint(Destination);
		  
		  print(pointsdestination);
		 drawRoute(pointsdestination,Color.RED,1);
		 
		 
		
		 
	        Drawable drawablea = context.getResources().getDrawable(R.drawable.pointer);
	        Drawable drawableb = context.getResources().getDrawable(R.drawable.bike);
	        VelibItemizedOverlay itemizedOverlaya = new VelibItemizedOverlay(drawablea,mAc);
	        VelibItemizedOverlay itemizedOverlayb = new VelibItemizedOverlay(drawableb,mAc);
	   	 
	     
	        
	        GeoPoint positiona= new GeoPoint((int) (monlat*1E6), (int) (monlong*1E6));
	   
	        OverlayItem overlayitema =  new OverlayItem(positiona, null,"Ma position actuelle" );
	        OverlayItem overlayitemb =  new OverlayItem(geopoint, null, "Nom de station:\n"+name+"\n"+"Adress:"+addr);
	        
	        
	        itemizedOverlaya.addOverlay(overlayitema);
	        itemizedOverlayb.addOverlay(overlayitemb);
	        mapOverlays.add(itemizedOverlaya);
	        mapOverlays.add(itemizedOverlayb);
		
		  
		 mapView.getController().setZoom(15);
		 isFini= true;
		 
	}
	
	public List<GeoPoint> GetPoint(String url){
    	
    	HttpGet get = new HttpGet(url);  
        String strResult = "";  
       try {  
    	   
            HttpParams httpParameters = new BasicHttpParams();  
            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);  
            HttpClient httpClient = new DefaultHttpClient(httpParameters);   
              
            HttpResponse httpResponse = null;  
            httpResponse = httpClient.execute(get);  
              
            if (httpResponse.getStatusLine().getStatusCode() == 200){  
                strResult = EntityUtils.toString(httpResponse.getEntity());  
            }  
        } catch (Exception e) {  
           
              
        }  
          
        if (-1 == strResult.indexOf("<status>OK</status>")){  
            Toast.makeText(this, "echoue de tracer la ligne!", Toast.LENGTH_SHORT).show();  
            this.finish();  
            
        }  
          
        int pos = strResult.indexOf("<overview_polyline>");  
        pos = strResult.indexOf("<points>", pos + 1);  
        if(pos == -1) return null;
        int pos2 = strResult.indexOf("</points>", pos);  
        strResult = strResult.substring(pos + 8, pos2);  
          
        return decodePoly(strResult); 
        
       // return points;
    	
    }
	
	private List<GeoPoint> decodePoly(String encoded) {  

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
	List<Overlay> over;
	public void drawRoute(List<GeoPoint> points, int couleur, int flag ){  


		Polyline mOverlay = new Polyline(points,couleur);  
		over = mapView.getOverlays();  
		if(flag == 1)overlays.clear();
		over.add(mOverlay);  
		//overlays.removeAll(pointsdestination);
		
		if (points.size() >= 2){  
			MapController mapController = mapView.getController();
			mapController.animateTo(points.get(0));  
		}  

		mapView.postInvalidate();  
	}  
	
	 private class Connection extends AsyncTask<String, Long, Boolean>  {

	    	Socket socket;
	    	ProgressDialog Pdialog;
	    	
	    	
	    	@Override  
	        protected void onPreExecute() {  
	            Pdialog = ProgressDialog.show(context , "Patienter", "en cours de calculer...");
	           // System.out.println("je suis onPreExecute()");
	            
	            
	        }  
	    	
	    	protected Boolean doInBackground(String... args) {
	    		// TODO Auto-generated method stub
	    		
	    		
	    		getInfo(args[0],args[1]);
	    		publishProgress();
	    		
	    		
	    		
	    		return null;
	    	}
	    	
	    	@Override
	    	 protected void onPostExecute(Boolean s) {
	             // 返回HTML页面的内容
	    		
	    		while(isFini == false);
	    		Pdialog.dismiss();
	    		
	         }
	    	@Override
	        protected void onProgressUpdate(Long... values) {
	            // 更新进度
	          //    System.out.println(""+values[0]);
	              
	        }
	    	
	    }*/
	
	
}
