package com.ciel.equipe.test;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity implements OnClickListener{

	Button btn_start;
	Button btn_finish;
	private MainActivity context;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        context = this;
        
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        
        btn_start.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_start:
			
			
					Log.d("MainActivity", "onClick: starting srvice");  
				      startService(new Intent(context, LocationService.class)); 
			
			break;
		case R.id.btn_finish:
			
	
						Log.d("MainActivity", "onClick: finishing srvice");  
					      stopService(new Intent(context, LocationService.class)); 

			
			break;

		default:
			break;
		}
		
	}

    
}
