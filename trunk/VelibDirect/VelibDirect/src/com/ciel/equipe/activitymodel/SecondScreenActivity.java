package com.ciel.equipe.activitymodel;

import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;

import com.ciel.equipe.activities.EcranAccueilActivity;

public class SecondScreenActivity extends Activity{

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			Intent intent = new Intent();
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(this, EcranAccueilActivity.class);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
		
		
		return super.onKeyDown(keyCode, event);
	}
}
