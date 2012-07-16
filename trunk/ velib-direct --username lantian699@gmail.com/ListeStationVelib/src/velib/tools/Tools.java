package velib.tools;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.StationVelib;
import velib.model.VelibItemizedOverlay;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import essai.cnam.R;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.widget.Toast;

public class  Tools {
	
	public static final int CODE_ACTIVITY_GPS_VERIFICATION = 0; 
	private static final double DISTANCE_UNITAIRE = 82324.0744;
	
	/* Ce fonction est pour vérifier que si le module GPS est bien allumé 
	 * 
	 */
	
	public static void openGPSSettings(Activity context) {
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "GPS fonctionne correctement", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(context, "GPS est éteint !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        context.startActivityForResult(intent,CODE_ACTIVITY_GPS_VERIFICATION); //此为设置完成后返回到获取界面
       
    }
	
	
	/*
	 * c'est pour calculer les stations proches de l'utilisateur dans un rayon défini
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
					new ParserInfoStation(context,InfoStationDao, station.getNumber(), station.getId());
					
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
	
	
	public static void  DrawStationOnMap(Context context, List<StationVelib> listStationSelect, MapView mapView) {

		List<Overlay> mapOverlays = mapView.getOverlays();
		
		
		Drawable drawable = context.getResources().getDrawable(R.drawable.bike);
		VelibItemizedOverlay itemOverlay = new VelibItemizedOverlay(drawable, context, mapView);



		
		try {
			Dao<InfoStation, Integer> InfoStationDao = DatabaseHelper.getInstance(context).getDao(InfoStation.class);
			
			
			for(StationVelib station :listStationSelect){

				GeoPoint StationPos = new GeoPoint((int) (station.getLatitude() * 1E6),(int) (station.getLongitude() * 1E6));
				
				QueryBuilder<InfoStation, Integer> queryBuilder = InfoStationDao.queryBuilder();
				queryBuilder.where().eq(InfoStation.COLUMN_INFO_ID_STATION,station.getId());
				PreparedQuery<InfoStation> preparedQuery = queryBuilder.prepare();
				List<InfoStation> infoList = InfoStationDao.query(preparedQuery);
				
				//Log.i(context, "InfoStation Free = " + infoList.get(0).getAvailable());
				
				OverlayItem overlayitem = new OverlayItem(StationPos, "station","stationInfo");

				itemOverlay.addOverlay(overlayitem,infoList.get(0));
				
				mapOverlays.add(itemOverlay);
			
			}
			
			
			mapView.postInvalidate();
			//mapView.postInvalidate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public static void goToMyLocation(Context context, Location recentLocation, MapView mapView) {
		
	
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = context.getResources().getDrawable(R.drawable.pointer);
		VelibItemizedOverlay velibItemizedOverlay = new VelibItemizedOverlay(drawable, context, mapView);

		GeoPoint mPosition = new GeoPoint((int) (recentLocation.getLatitude() * 1E6),(int) (recentLocation.getLongitude() * 1E6));

		OverlayItem overlayitem = new OverlayItem(mPosition, null, "Ma position actuelle");

		velibItemizedOverlay.addOverlay(overlayitem,null);
		mapOverlays.add(velibItemizedOverlay);

		MapController mapController = mapView.getController();

		mapController.animateTo(mPosition);
		mapController.setZoom(15);
		
	}
	
	

}
