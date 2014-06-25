package com.ciel.equipe.tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

import com.ciel.equipe.model.DatabaseHelper;
import com.ciel.equipe.model.InfoStation;
import com.ciel.equipe.model.StationVelib;
import com.google.android.gms.maps.GoogleMap;
import com.j256.ormlite.dao.Dao;

public class  Tools {
	
	public static final int CODE_ACTIVITY_GPS_VERIFICATION = 0; 
	private static final double DISTANCE_UNITAIRE = 82324.0744;
	
	/* Ce fonction est pour vérifier que si le module GPS est bien allumÃ© 
	 * 
	 */
	
	public static void openGPSSettings(Activity context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "GPS fonctionne correctement", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(context, "GPS est Ã©teint !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        context.startActivityForResult(intent,CODE_ACTIVITY_GPS_VERIFICATION); //æ­¤ä¸ºè®¾ç½®å®Œæˆ�å�Žè¿”å›žåˆ°èŽ·å�–ç•Œé�¢
       
    }
	
	
	/*
	 * c'est pour calculer les stations proches de l'utilisateur dans un rayon dÃ©fini
	 */
	
	public static List<StationVelib> Calculateur_Station_Prox(Context context, Location recentLocation, int Rayon) {

		double mlatitude = recentLocation.getLatitude();
		double mlongitide = recentLocation.getLongitude();

		double latDiff = 0.0;
		double longDiff = 0.0;
		double distanceDiff = 0.0;
		List<StationVelib> listStationSelect = new ArrayList<StationVelib>();
		listStationSelect.clear();
		
		try{
		Dao<StationVelib, Integer> listStationDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
		List<StationVelib> listStation = listStationDao.queryForAll();
		
		for (StationVelib station : listStation) {

			latDiff = Math.abs(station.getLatitude() - mlatitude);
			longDiff = Math.abs(station.getLongitude() - mlongitide);
			distanceDiff = Math.sqrt(latDiff * latDiff + longDiff * longDiff);
			
			if (distanceDiff * DISTANCE_UNITAIRE < Rayon) {

					listStationSelect.add(station);

					Dao<InfoStation, Integer> InfoStationDao; InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
//					new ParserInfoStation(context,InfoStationDao, station.getNumber(), station.getId());
					
					Log.i(context, "Find a station --> " + station.getName());

				}

			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Log.i(context, "<-- There is  " + listStationSelect.size() + " station(s) in " + Rayon + "m -->");
		return listStationSelect;
		
	}
	
	
	public static void  DrawStationsOnMap(List<StationVelib> listStationSelect, GoogleMap mMap) {

//		List<Overlay> mapOverlays = mapView.getOverlays();
//		
//		
//		Drawable drawable = context.getResources().getDrawable(R.drawable.bike);
//		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);
//
//
//
//		
//		try {
//			Dao<InfoStation, Integer> InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
//			
//			
//			for(StationVelib station :listStationSelect){
//
//				GeoPoint StationPos = new GeoPoint((int) (station.getLatitude() * 1E6),(int) (station.getLongitude() * 1E6));
//				
//				QueryBuilder<InfoStation, Integer> queryBuilder = InfoStationDao.queryBuilder();
//				queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
//				PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
//				List<InfoStation> infoList = InfoStationDao.query(preparedQuery);
//				
//				//Log.i(context, "InfoStation Free = " + infoList.get(0).getAvailable());
//				
//				OverlayItem overlayitem = new OverlayItem(StationPos, "station","stationInfo");
//
//				velibItemizedOverlay.addOverlay(overlayitem,infoList.get(0));
//				
//				mapOverlays.add(velibItemizedOverlay);
//			
//			}
//			
//			
//
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}
	
	
//	public static void  DrawOneStationOnMap(Activity context, StationVelib station, MapView mapView) {
//
//		List<Overlay> mapOverlays = mapView.getOverlays();
//		
//		
//		Drawable drawable = context.getResources().getDrawable(R.drawable.bike);
//		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);
//
//		
//		try {
//			Dao<InfoStation, Integer> InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
//			
//			
//			
//
//				GeoPoint StationPos = new GeoPoint((int) (station.getLatitude() * 1E6),(int) (station.getLongitude() * 1E6));
//				
//				QueryBuilder<InfoStation, Integer> queryBuilder = InfoStationDao.queryBuilder();
//				queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
//				PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
//				List<InfoStation> infoList = InfoStationDao.query(preparedQuery);
//				
//				
//				OverlayItem overlayitem = new OverlayItem(StationPos, "station","stationInfo");
//
//				velibItemizedOverlay.addOverlay(overlayitem,infoList.get(0));
//				
//				mapOverlays.add(velibItemizedOverlay);
//			
//				mapView.postInvalidate();
//			
//				MapController mapController = mapView.getController();
//				mapController.animateTo(StationPos);
//				mapController.setZoom(15);
//			
//
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//	
	
//	public static void goToMyLocation(Activity context, Location recentLocation, MapView mapView) {
//		
//	
//		List<Overlay> mapOverlays = mapView.getOverlays();
//		Drawable drawable = context.getResources().getDrawable(R.drawable.pointer);
//		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);
//
//		GeoPoint mPosition = new GeoPoint((int) (recentLocation.getLatitude() * 1E6),(int) (recentLocation.getLongitude() * 1E6));
//
//		OverlayItem overlayitem = new OverlayItem(mPosition, null, "Ma position actuelle");
//
//		velibItemizedOverlay.addOverlay(overlayitem,null);
//		mapOverlays.add(velibItemizedOverlay);
//
//		MapController mapController = mapView.getController();
//		mapController.animateTo(mPosition);
//		mapController.setZoom(15);
//		
//	}
//	
//	
//	public static void getInfo(Context context, StationVelib station, InfoStation infoStation,  Location recentLocation, MapView mapView){
//		
//		double sLatitude = station.getLatitude();
//		double sLongitude = station.getLongitude();
//		
//		double mLatitude = recentLocation.getLatitude();
//		double mLongitude = recentLocation.getLongitude();
//		
//		 List<Overlay> mapOverlays = mapView.getOverlays();
//
//		 GeoPoint Point_Station = new GeoPoint( (int)  (sLatitude*1E6), (int )(sLongitude*1E6));
//		 
//		 String Destination = "http://maps.google.com/maps/api/directions/xml?origin="+mLatitude+","+ mLongitude +  
//	     	"&destination="+sLatitude +","+sLongitude+"&sensor=false&mode=walking";  
//		
//		  List<GeoPoint> pointsdestination = GetPoint(context, Destination);
//		  
//		  drawRoute(pointsdestination,Color.RED,1, mapView);
//		 
//		 
//		
//		 
//	        Drawable Drawable_pointer = context.getResources().getDrawable(R.drawable.pointer);
//	        Drawable Drawable_station = context.getResources().getDrawable(R.drawable.bike);
//	        
//	        VelibItemizedOverlay itemizedOverlay_pointer = new VelibItemizedOverlay(Drawable_pointer);
//	        VelibItemizedOverlay itemizedOverlay_station = new VelibItemizedOverlay(Drawable_station);
//	   	 
//	     
//	        
//	        GeoPoint positiona= new GeoPoint((int) (mLatitude*1E6), (int) (mLongitude*1E6));
//	   
//	        OverlayItem overlayitema =  new OverlayItem(positiona, null,"Ma position actuelle" );
//	        OverlayItem overlayitemb =  new OverlayItem(Point_Station, "infoStation", "infoStation");
//	        
//	        
//	        itemizedOverlay_pointer.addOverlay(overlayitema,null);
//	        itemizedOverlay_station.addOverlay(overlayitemb, infoStation);
//	        mapOverlays.add(itemizedOverlay_pointer);
//	        mapOverlays.add(itemizedOverlay_station);
//		
//		  
//	        mapView.getController().setZoom(15);
//
//		 
//	}
	
//	public static List<GeoPoint> GetPoint(Context context, String url){
//    	
//    	HttpGet get = new HttpGet(url);  
//        String strResult = "";  
//        
//        try {  
//    	   
//            HttpParams httpParameters = new BasicHttpParams();  
//            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);  
//            HttpClient httpClient = new DefaultHttpClient(httpParameters);   
//              
//            HttpResponse httpResponse = httpClient.execute(get);  
//              
//            if (httpResponse.getStatusLine().getStatusCode() == 200){  
//                strResult = EntityUtils.toString(httpResponse.getEntity());  
//            }  
//        } catch (Exception e) {  
//           
//              
//        }  
//          
//        if (-1 == strResult.indexOf("<status>OK</status>")){  
//            Toast.makeText(context, "echoue de tracer la ligne!", Toast.LENGTH_SHORT).show();  
//           // context.finish();  
//            
//        }  
//          
//        int pos = strResult.indexOf("<overview_polyline>");  
//        pos = strResult.indexOf("<points>", pos + 1);  
//        if(pos == -1) return null;
//        int pos2 = strResult.indexOf("</points>", pos);  
//        strResult = strResult.substring(pos + 8, pos2);  
//          
//        return decodePoly(strResult); 
//        
//       // return points;
//    	
//    }
//	
//	private static List<GeoPoint> decodePoly(String encoded) {  
//
//		List<GeoPoint> poly = new ArrayList<GeoPoint>();  
//		int index = 0, len = encoded.length();  
//		int lat = 0, lng = 0;  
//
//		while (index < len) {  
//			int b, shift = 0, result = 0;  
//			do {  
//				b = encoded.charAt(index++) - 63;  
//				result |= (b & 0x1f) << shift;  
//				shift += 5;  
//			} while (b >= 0x20);  
//			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
//			lat += dlat;  
//
//			shift = 0;  
//			result = 0;  
//			do {  
//				b = encoded.charAt(index++) - 63;  
//				result |= (b & 0x1f) << shift;  
//				shift += 5;  
//			} while (b >= 0x20);  
//			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));  
//			lng += dlng;  
//
//			GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),  
//					(int) (((double) lng / 1E5) * 1E6));  
//			poly.add(p);  
//		}  
//
//		return poly;  
//	}
//	
//	
//
//	public static  void drawRoute(List<GeoPoint> points, int couleur, int flag, MapView mapView ){  
//
//
//		Polyline mOverlay = new Polyline(points,couleur);  
//		List<Overlay> overlayList = mapView.getOverlays();  
//
//		overlayList.clear();
//		overlayList.add(mOverlay);  
//		
//		if (points.size() >= 2){  
//			MapController mapController = mapView.getController();
//			mapController.animateTo(points.get(0));  
//		}  
//
//		mapView.postInvalidate();  
//	}  
//		 
//	
//	
//	/**
//	 * Fonction qui permet de recharger la liste pour le module de recherche
//	 * 
//	 * @param s
//	 */
//
//	public static void setNewListForSearchModule(ListView listView, SherlockActivity context,CharSequence searchModule, List<StationVelib> listVelib) {
//
//		
//		ArrayList<StationVelib> stationSearch = new ArrayList<StationVelib>();
//
//	
//		if (!"".equals(searchModule.toString())) {
//			for (StationVelib station : listVelib) {
//				boolean containsLabel = station.getName() != null &&  station.getName().toLowerCase().contains(searchModule.toString().toLowerCase());
//				boolean containsCodeType =  String.valueOf(station.getNumber()) != null && String.valueOf(station.getNumber()).toLowerCase().contains(searchModule.toString().toLowerCase());
//
//				if (containsLabel || containsCodeType) {
//					stationSearch.add(station);
//				}
//			}
//			
//			Log.d(listView, "dataStationSearch.size() : " + stationSearch.size());
//
//			refreshScreenData(listView,context,stationSearch);
//			
//		}else{
//	
//			listView.setAdapter(new AdapterListPrincipal(context, listVelib));
//		}
//
//		
//
//	}
//
//	public static void refreshScreenData(ListView listView, SherlockActivity context, List<StationVelib> stationSearch) {
//
//
//			listView.setAdapter(new AdapterListPrincipal(context, stationSearch));
//		
//
//
//	}
//	
	
	
	
//	private static void showVirtualKeyboard(ListActivity listActivity) {
//		InputMethodManager in = (InputMethodManager)listActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
//		in.showSoftInput(((View)listActivity.findViewById(R.id.autoComplete_search_station)), InputMethodManager.SHOW_IMPLICIT);
//	}
//
//	
//	public static void marquerIsPrefered(Activity context, StationVelib station, boolean isPrefered){
//		
//		try {
//			Dao<StationVelib, Integer> stationDao = DatabaseHelper.getInstance(context).getDao(StationVelib.class);
//			
//			if(isPrefered)
//			station.setIsPrefered(1);
//			else 
//			station.setIsPrefered(0);
//			
//			stationDao.update(station);
//			
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
	
//	
	public static String StringUtilsSeperator(String s){
		
		s = s.substring(s.indexOf("-")+2, s.length());
		
		return s;
		
	}

}
