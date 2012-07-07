package essai.cnam;

import java.sql.SQLException;
import java.util.List;

import velib.model.DatabaseHelper;
import velib.model.StationVelib;
import velib.tools.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.Dao;

public class LogoActivity extends OrmLiteBaseActivity<DatabaseHelper> {

	private Handler mHandler = new Handler();
	ImageView imageview;
	Button bn1, bn2;
	int alpha = 255;// 透明值255
	int b = 0;
	private Dao<StationVelib, Integer> StationVelibDao;

	/**
	 * 
	 * @Title: getHelper
	 * @Description: TODO(获得自定义数据库小助手类DBHelper)
	 * @return 设定文件
	 * @return DBHelper 返回类型
	 */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logo);

		try {
			// StationVelibDao =
			// DatabaseHelper.getInstance(getApplicationContext()).getDao(StationVelib.class);
			StationVelibDao = getHelper().getDao(StationVelib.class);
			
			List<StationVelib> list = StationVelibDao.queryForAll();

			System.out.println("LogoActivity StationVelibDao = "
					+ StationVelibDao);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Log.e(this, "SQL error", e);
		}

		imageview = (ImageView) this.findViewById(R.id.imageView5);
		// textview = (TextView) this.findViewById(R.id.TextView01);
		// Log.v("ColaBox", "ColaBox start ...");
		imageview.setAlpha(alpha);

		bn1 = (Button) this.findViewById(R.id.button1);
		bn2 = (Button) this.findViewById(R.id.button2);

		/*
		 * new Thread(new Runnable() { public void run() { initApp(); //初始化程序
		 * 
		 * while (b < 2) { try { if (b == 0) { Thread.sleep(1500); b = 1; } else
		 * { Thread.sleep(50); } updateApp(); } catch (InterruptedException e) {
		 * e.printStackTrace(); } } } }).start();
		 * 
		 * mHandler = new Handler() {
		 * 
		 * @Override public void handleMessage(Message msg) {
		 * super.handleMessage(msg); imageview.setAlpha(alpha);
		 * imageview.invalidate(); } }; } public void updateApp() { alpha -= 5;
		 * if (alpha <= 0) { b = 2; Intent in = new
		 * Intent(this,ListeStationVelibActivity.class); startActivity(in);
		 * this.finish(); } mHandler.sendMessage(mHandler.obtainMessage());
		 */

	}// end of OnCreate();

	public void bnProxiClick(View v) {

		Intent in = new Intent(this, ListeStationAlentourActivity.class);
		startActivity(in);

	}

	public void bnListeClick(View v) {
		Intent in = new Intent(this, ListeStationVelibActivity.class);
		startActivity(in);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			new AlertDialog.Builder(this)

					.setMessage("Êtes-vous sûr de quitter VelibDirect")
					.setNegativeButton("Non",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
					.setPositiveButton("Oui",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									finish();
								}
							}).show();

		}
		return false;

	}

	public void initApp() {

	}

}