package essai.cnam;

import java.util.List;

import com.google.android.maps.MapView;

import velib.model.StationVelib;
import velib.services.LocationService;
import velib.tools.Tools;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DrawStationInBackground extends AsyncTask<Void, Void, Void>{

	ProgressDialog dialog;
	private List<StationVelib> listStationSelect;
	private Context context;
	private MapView mapView;
	private int Rayon;
	
	public DrawStationInBackground(Context context, MapView mapView, int Rayon){
		
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



		
		Tools.goToMyLocation(context, LocationService.getRecentLocation(), mapView);

		listStationSelect = Tools.Calculateur_Station_Prox(context, LocationService.getRecentLocation(), Rayon);

			
		Tools.DrawStationOnMap(context, listStationSelect, mapView);
		
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
