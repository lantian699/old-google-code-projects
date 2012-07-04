package essai.cnam;

import java.io.InputStream;
import java.util.List;

import velib.model.InfoStation;
import velib.model.ListeDesStationsVelib;
import velib.model.VelibItemizedOverlay;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class ListeStationAlentourActivity extends MapActivity implements
		LocationListener {

	MapView mapView;
	Location mostRecentLocation;
	LocationManager locationManager;
	ListeDesStationsVelib stations;
	private static double distanceMetre = 82324.0744;
	int Rayon = 500;
	Location[] gareAutour;
	double[] lats;
	double[] longs;
	InputStream is;
	double[] latAutour = new double[1200];
	double[] longAutour = new double[1200];
	String[] stNoms = new String[1200];
	String[] stAddr = new String[1200];
	int[] stNum = new int[1200];
	String addr;
	VelibItemizedOverlay itemOverlay;
	List<Overlay> mapOverlays;
	OverlayItem overlayitem;
	GeoPoint maposition;
	static Context mCon;
	private static final String URL_VELIB_INFO = "http://www.velib.paris.fr/service/stationdetails/"; // /number

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_map);

		// AdView adView = (AdView)this.findViewById(R.id.adMap); // show the
		// advertisement
		// adView.loadAd(new AdRequest());

		mCon = this;
		mapView = (MapView) findViewById(R.id.mapView);
		mapView.setBuiltInZoomControls(true);
		mapOverlays = mapView.getOverlays();
		// getLocation();
		// goToMyLocation();

		new waitForLocation().execute();

	}// end of OnCreate()

	static double monLat;
	static double monLong;
	int j = 0;

	public void calculateur(Location recentLocation, double[] lats,
			double[] longs, int length, String[] noms, String[] addr, int[] num) {

		monLat = recentLocation.getLatitude();
		monLong = recentLocation.getLongitude();
		double latDiff = 0.0;
		double longDiff = 0.0;
		double distanceDiff = 0.0;
		for (int i = 0; i < length - 2; i++) {

			latDiff = Math.abs(lats[i] - monLat);
			longDiff = Math.abs(longs[i] - monLong);
			distanceDiff = Math.sqrt(latDiff * latDiff + longDiff * longDiff);

			if (distanceDiff * distanceMetre < Rayon) {
				stAddr[j] = addr[i];
				stNoms[j] = noms[i];
				latAutour[j] = lats[i];
				stNum[j] = num[i];
				longAutour[j++] = longs[i];

				// System.out.println("latAutour =" +latAutour[j-1]+"  "+j);
			}
		}

	}

	public void DrawMap(double lat, double longs, int h) {

		// mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.bike);
		itemOverlay = new VelibItemizedOverlay(drawable, this);

		GeoPoint maposition = new GeoPoint((int) (lat * 1E6),
				(int) (longs * 1E6));

		InfoStation info = new InfoStation(stNum[h]);
		int available = info.getAvailable();
		int free = info.getFree();
		int total = info.getTotal();

		itemOverlay.setMap(mapView);
		itemOverlay.setActivity(this);
		itemOverlay.setInfo(monLat, monLong);

		overlayitem = new OverlayItem(maposition, String.valueOf(latAutour[h])
				+ "_" + String.valueOf(longAutour[h]), stNoms[h] + "_"
				+ available + " Vélos disponibles\n" + free
				+ " vélos en Free\n" + total + " vélos en Total");

		itemOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemOverlay);

	}

	private void getLocation() {
		// TODO Modify user localization according to this article:
		// http://developer.android.com/guide/topics/location/obtaining-user-location.html

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		String provider = locationManager.getBestProvider(criteria, true);
		// In order to make sure the device is getting the location, request
		// updates.
		locationManager.requestLocationUpdates(provider, 1l, 0f, this);
		mostRecentLocation = locationManager.getLastKnownLocation(provider);
		if (mostRecentLocation == null) {
			mostRecentLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			if (mostRecentLocation == null) {
				Log.i("location", "Unknown Location!");
			}
			// showToast("Get location! Provider: " +
			// LocationManager.NETWORK_PROVIDER);
		}
		// locationManager.removeUpdates(this);
		// else
		// showToast("Get location! Provider: " + provider);
	}

	private void goToMyLocation() {
		List<Overlay> mapOverlays = mapView.getOverlays();
		Drawable drawable = this.getResources().getDrawable(R.drawable.pointer);
		VelibItemizedOverlay itemizedOverlay = new VelibItemizedOverlay(
				drawable, this);

		maposition = new GeoPoint(
				(int) (mostRecentLocation.getLatitude() * 1E6),
				(int) (mostRecentLocation.getLongitude() * 1E6));

		OverlayItem overlayitem = new OverlayItem(maposition, null,
				"ma position actuel");

		itemizedOverlay.addOverlay(overlayitem);
		mapOverlays.add(itemizedOverlay);

		MapController mapController = mapView.getController();

		mapController.animateTo(maposition);
		mapController.setZoom(15);
		System.out.println("latitude= " + mostRecentLocation.getLatitude()
				+ "longitude=" + mostRecentLocation.getLongitude());
		System.out.println("latitude= " + maposition.getLatitudeE6()
				+ "longitude=" + maposition.getLongitudeE6());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.item01:

			getLocation();
			goToMyLocation();
			locationManager.removeUpdates(ListeStationAlentourActivity.this);

			return true;
		case R.id.item02:

			final CharSequence[] items = { "500 M", "1000 M", "1500 M" };
			j = 0;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("Rayon de Cherche");
			builder.setItems(items, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int item) {
					// Toast.makeText(getApplicationContext(), items[item],
					// Toast.LENGTH_SHORT).show();
					if (item == 0)
						Rayon = 500;
					if (item == 1)
						Rayon = 1000;
					if (item == 2)
						Rayon = 1500;

					mapOverlays.clear();

					new waitForLocation().execute();

					/*
					 * try { mapOverlays.clear(); goToMyLocation(); is =
					 * ListeStationAlentourActivity
					 * .this.getAssets().open("stations.xml");
					 * 
					 * stations = new ListeDesStationsVelib(is); lats =
					 * stations.getLats(); longs = stations.getLongs();
					 * 
					 * calculateur(mostRecentLocation, lats, longs,
					 * stations.getLength(), stations.getNoms(),
					 * stations.getAddr(), stations.getNum());
					 * //System.out.println
					 * ("Garerearfqfdq="+stations.getGareVelib());
					 * 
					 * } catch (Exception e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); } if(j>0){ for(int h=0;
					 * h<=j-1;h++){ DrawMap(latAutour[h],longAutour[h], h); //
					 * System.out.println("j="+j); }
					 * 
					 * 
					 * Toast.makeText(ListeStationAlentourActivity.this,
					 * "j'ai trouvé"+String.valueOf(j)+"velibs disponibles",
					 * Toast.LENGTH_LONG ).show(); }else{
					 * Toast.makeText(ListeStationAlentourActivity.this,
					 * "Désolé, il n'y a pas de Velib autour de vous",
					 * Toast.LENGTH_LONG ).show(); }
					 */

				}
			});
			AlertDialog alert = builder.create();
			alert.show();

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * class AfficheMap extends AsyncTask<String,Long,Boolean>{
	 * 
	 * 
	 * protected void onPreExecute() {
	 * 
	 * ProgressDialog dialog =
	 * ProgressDialog.show(ListeStationAlentourActivity.this, "",
	 * "Loading. Please wait...", true); }
	 * 
	 * 
	 * @Override protected Boolean doInBackground(String... params) { // TODO
	 * Auto-generated method stub
	 * 
	 * 
	 * 
	 * try { is =
	 * ListeStationAlentourActivity.this.getAssets().open("stations.xml");
	 * 
	 * stations = new ListeDesStationsVelib(is); lats = stations.getLats();
	 * longs = stations.getLongs();
	 * 
	 * calculateur(mostRecentLocation, lats, longs, stations.getLength(),
	 * stations.getNoms());
	 * //System.out.println("Garerearfqfdq="+stations.getGareVelib());
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } if(j>0){ for(int h=0; h<=j-1;h++){
	 * DrawMap(latAutour[h],longAutour[h], h); // System.out.println("j="+j); }
	 * 
	 * 
	 * Toast.makeText(ListeStationAlentourActivity.this,
	 * "j'ai trouvé"+String.valueOf(j-1)+"Velibs disponibles", Toast.LENGTH_LONG
	 * ).show(); }else{ Toast.makeText(ListeStationAlentourActivity.this,
	 * "Désolé, il n'y a pas de Velib autour de vous", Toast.LENGTH_LONG
	 * ).show(); }
	 * 
	 * 
	 * 
	 * return null; }
	 * 
	 * protected void onProgressUpdate(Long... v) { }
	 * 
	 * 
	 * protected void onPostExecute(Boolean b) { }
	 * 
	 * 
	 * }
	 */

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public class waitForLocation extends AsyncTask<Void, Void, Void> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();

			dialog = ProgressDialog
					.show(mCon, "", "En attente de localisation");

		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			Looper.prepare();

			for (int i = 0; i < 10; i++)
				getLocation();

			goToMyLocation();

			try {
				is = ListeStationAlentourActivity.this.getAssets().open(
						"stations.xml");

				stations = new ListeDesStationsVelib(is);
				lats = stations.getLats();
				longs = stations.getLongs();

				System.out.println(stations.getNoms());
				calculateur(mostRecentLocation, lats, longs,
						stations.getLength(), stations.getNoms(),
						stations.getAddr(), stations.getNum());
				// System.out.println("Garerearfqfdq="+stations.getGareVelib());

				if (j > 0) {
					for (int h = 0; h <= j - 1; h++) {
						DrawMap(latAutour[h], longAutour[h], h);
						// System.out.println("j="+j);
					}

					// dialog.dismiss();
					// Toast.makeText(mCon,
					// String.valueOf(j)+" stations de Velib trouvées",
					// Toast.LENGTH_LONG ).show();
				} else {

					// dialog.dismiss();
					// Toast.makeText(mCon,
					// "Désolé, il n'y a pas de Velib autour de vous",
					// Toast.LENGTH_LONG ).show();
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			locationManager.removeUpdates(ListeStationAlentourActivity.this);
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			if (j > 0) {

				dialog.dismiss();
				Toast.makeText(
						mCon,
						String.valueOf(j)
								+ " stations de Velib trouvées, veuillez appuyer sur l'écran",
						Toast.LENGTH_LONG).show();
			} else {

				dialog.dismiss();
				Toast.makeText(
						mCon,
						"Désolé, il n'y a pas de Velib autour de vous, veuillez appuyer sur l'écran",
						Toast.LENGTH_LONG).show();
			}

			/*
			 * if(j>0){ for(int h=0; h<=j-1;h++){
			 * DrawMap(latAutour[h],longAutour[h], h); //
			 * System.out.println("j="+j); }
			 * 
			 * dialog.dismiss(); Toast.makeText(mCon,
			 * String.valueOf(j)+" stations de Velib trouvées",
			 * Toast.LENGTH_LONG ).show(); }else{
			 * 
			 * dialog.dismiss(); Toast.makeText(mCon,
			 * "Désolé, il n'y a pas de Velib autour de vous", Toast.LENGTH_LONG
			 * ).show(); }
			 */
		}
	}
}
