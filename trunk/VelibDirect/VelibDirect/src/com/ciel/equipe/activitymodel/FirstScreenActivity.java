package com.ciel.equipe.activitymodel;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class FirstScreenActivity extends Activity {
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
		}
		
		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
		}
		
		
		@Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			// TODO Auto-generated method stub
			
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Etes-vous sûr de vouloir quitter l'application ?")
				       .setCancelable(false)
				       .setPositiveButton("OUI", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                FirstScreenActivity.this.finish();
				           }
				       })
				       .setNegativeButton("NON", new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				                dialog.cancel();
				           }
				       });
				AlertDialog alert = builder.create();
				
				alert.show();
				
			
				
				break;

			default:
				break;
			}
			
			
			
			return super.onKeyDown(keyCode, event);
		}
		

}
