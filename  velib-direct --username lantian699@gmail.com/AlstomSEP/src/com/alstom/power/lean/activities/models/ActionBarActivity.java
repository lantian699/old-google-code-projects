package com.alstom.power.lean.activities.models;

import com.alstom.power.lean.R;

import android.app.Activity;
import android.view.Menu;

public class ActionBarActivity extends Activity{
	
	
	 @Override
	     public boolean onCreateOptionsMenu(Menu menu) {
	        
	        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
	        return super.onCreateOptionsMenu(menu);
	     }

}
