package essai.cnam;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import velib.model.AdapterListPreferStation;
import velib.model.DatabaseHelper;
import velib.model.InfoStation;
import velib.model.getStationFromSite;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class EcranAccueilActivity extends Activity{

	ListView list_prefer_station;
	ArrayList<String> listPre = new ArrayList<String>();
	private Runnable screenCallback;
	Context context;
	private AdapterListPreferStation adapter_prefer;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.accueil);
		

			list_prefer_station = (ListView)findViewById(R.id.list_prefer_station);
			
			new  getStationFromSite(this, list_prefer_station).execute(); 
			
			
		
		
	}
}
