package com.ciel.equipe.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.ciel.equipe.R;
import com.ciel.equipe.activitymodel.FirstScreenActivity;
import com.ciel.equipe.model.getStationFromSite;
import com.ciel.equipe.services.LocationService;
import com.ciel.equipe.tools.Tools;
import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class EcranAccueilActivity extends FirstScreenActivity {
	
	public static final int REQUEST_CODE_ACTIVITY_LIST = 0;

	private ListView list_prefer_station;

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accueil);
		
		Tools.openGPSSettings(this);
		
		AdView adView = (AdView)this.findViewById(R.id.ad_accueil); // show the advertisement
	    adView.loadAd(new AdRequest());
		
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
		
        menu.add("menu")
            .setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_dark )
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        

        return super.onCreateOptionsMenu(menu);
    }
	
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //This uses the imported MenuItem from ActionBarSherlock
        
        Intent intent = new Intent();
        intent.setClass(this, AProposActivity.class);
        startActivity(intent);
        
        
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
		startActivityForResult(intent_list, REQUEST_CODE_ACTIVITY_LIST);
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case REQUEST_CODE_ACTIVITY_LIST:
			
			new  getStationFromSite(this, list_prefer_station).execute(); 
			
			break;

		default:
			break;
		}
		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		return super.onKeyDown(keyCode, event);
	}
	
	

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		stopService(new Intent(this, LocationService.class));
	}



}
