package essai.cnam;

import java.util.ArrayList;

import velib.model.AdapterListPreferStation;
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
		
		
		this.list_prefer_station = (ListView)findViewById(R.id.list_prefer_station);
		
		
		adapter_prefer = new AdapterListPreferStation(getApplicationContext(), listPre, screenCallback);
		
		this.list_prefer_station.setAdapter(adapter_prefer);
	}
}
