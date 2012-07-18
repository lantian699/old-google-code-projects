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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class EcranAccueilActivity extends Activity implements OnClickListener{

	private ListView list_prefer_station;
	private ArrayList<String> listPre = new ArrayList<String>();
	private Runnable screenCallback;
	private Context context;
	private AdapterListPreferStation adapter_prefer;
	private Button btn_prox;
	private Button btn_list;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.accueil);
		

		list_prefer_station = (ListView)findViewById(R.id.list_prefer_station);
		btn_list = (Button)findViewById(R.id.btn_list);
		btn_prox = (Button)findViewById(R.id.btn_prox);
			
		

		btn_list.setOnClickListener(this);
		btn_prox.setOnClickListener(this);
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		new  getStationFromSite(this, list_prefer_station).execute(); 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_prox:
			Intent intent = new Intent(this, ListeStationAlentourActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			
			break;
			
		case R.id.btn_list:
			
			Intent intent_list = new Intent(this, ListeStationVelibActivity.class);
			intent_list.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent_list);
			
			break;

		default:
			break;
		}
		
	}




}
