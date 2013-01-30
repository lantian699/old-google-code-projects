package ciel.equipe.years;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class FirstPictureActivity extends Activity {
	private static final long PAUSE_INTERVAL = 1000;
	private Handler mHandler;
	private ImageView imageview;
	private int alpha = 100;
	private int alphaActual = 0;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reletive);

		mHandler = new Handler();
		imageview = (ImageView) this.findViewById(R.id.viewPicture);
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(PAUSE_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				startActivity(new Intent(FirstPictureActivity.this, CatalogActivity.class));
			}
		}).start();
		
		
	}

	// new Thread(new Runnable() {
	// public void run() {
	// initApp();
	// while (alphaActual < 2) {
	// try {
	// if (alphaActual == 0) {
	// Thread.sleep(1500);
	// alphaActual = 1;
	// } else {
	// Thread.sleep(50);
	// }
	// updateApp();
	// } catch (InterruptedException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }).start();
	//
	// mHandler = new Handler() {
	// @Override
	// public void handleMessage(Message msg) {
	// super.handleMessage(msg);
	// imageview.setAlpha(alpha);
	// imageview.invalidate();
	// }
	// };
	// }
	//
	// public void updateApp() {
	// alpha -= 5;
	// if (alpha <= 0) {
	// alphaActual = 2;
	// Intent in = new Intent(this, CatalogActivity.class);
	//
	// startActivity(in);
	// this.finish();
	// }
	// mHandler.sendMessage(mHandler.obtainMessage());
	// }
	//
	// public void initApp() {
	//
	// }

	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			finish();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("ExitApp");
		this.registerReceiver(broadcastReceiver, filter);
	}

}