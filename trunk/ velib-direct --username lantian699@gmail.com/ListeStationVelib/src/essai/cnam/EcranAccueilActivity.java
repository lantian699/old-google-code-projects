package essai.cnam;

import java.util.ArrayList;

import velib.activitymodel.FirstScreenActivity;
import velib.model.AdapterListPreferStation;
import velib.model.getStationFromSite;
import velib.services.LocationService;
import velib.tools.Tools;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class EcranAccueilActivity extends FirstScreenActivity {

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
		
		Tools.openGPSSettings(this);
		
		/*
		 if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
	            BitmapDrawable bg = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
	            bg.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
	            getSupportActionBar().setBackgroundDrawable(bg);

	            BitmapDrawable bgSplit = (BitmapDrawable)getResources().getDrawable(R.drawable.bg_striped_split_img);
	            bgSplit.setTileModeXY(TileMode.REPEAT, TileMode.REPEAT);
	            getSupportActionBar().setSplitBackgroundDrawable(bgSplit);
	        }*/
		
		startService(new Intent(this, LocationService.class));

		list_prefer_station = (ListView)findViewById(R.id.list_prefer_station);
		
		new  getStationFromSite(this, list_prefer_station).execute(); 
		
	}

	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = true;
        menu.add("menu")
            .setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_dark )
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        

        return super.onCreateOptionsMenu(menu);
    }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This uses the imported MenuItem from ActionBarSherlock
        Toast.makeText(this, "Got click: " + item.toString(), Toast.LENGTH_SHORT).show();
        return true;
    }


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		
	}

	
	public void viewAddListIsClicked(View v){
		
		Intent intent_list = new Intent();
		intent_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent_list.setClass(this, ListeStationVelibActivity.class);
		startActivity(intent_list);
		
	}


	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		stopService(new Intent(this, LocationService.class));
	}



}
