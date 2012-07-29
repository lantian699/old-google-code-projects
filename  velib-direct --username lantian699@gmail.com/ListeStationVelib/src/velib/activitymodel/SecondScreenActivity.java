package velib.activitymodel;

import android.content.Intent;
import android.view.KeyEvent;

import com.actionbarsherlock.app.SherlockActivity;

import essai.cnam.EcranAccueilActivity;

public class SecondScreenActivity extends SherlockActivity{

	
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
