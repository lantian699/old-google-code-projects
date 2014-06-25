package com.ciel.equipe.views;

import com.ciel.equipe.R;
import com.ciel.equipe.activities.EcranAccueilActivity;
import com.ciel.equipe.activities.ListeStationAlentourActivity;
import com.ciel.equipe.activities.ListeStationVelibActivity;
import com.ciel.equipe.tools.Log;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class ToolBar extends LinearLayout implements OnClickListener{
	
	public static final int HIGHLIGHT_GPS_STATION= 0;
	public static final int HIGHLIGHT_LIST = 1;
	
	private Activity mContext;

	public ToolBar(Context context, AttributeSet attrs) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.mContext = (Activity) context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.toolbar, this);
		
		findViewById(R.id.btn_go_station).setOnClickListener(this);
		findViewById(R.id.btn_go_list).setOnClickListener(this);
		
		Drawable drawable = getResources().getDrawable(R.drawable.toolbar_green_dark_background);
		setBackgroundDrawable(drawable);
		//setPadding(0, 10, 0, 10);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_go_station:
			
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(mContext, ListeStationAlentourActivity.class);
			mContext.startActivity(intent);
			
			break;
			
		case R.id.btn_go_list:
			
			Intent intent_list = new Intent();
			intent_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_list.setClass(mContext, ListeStationVelibActivity.class);
			mContext.startActivityForResult(intent_list, EcranAccueilActivity.REQUEST_CODE_ACTIVITY_LIST);
			
			break;

		default:
			break;
		}
		
	}
	
	
	public static void setHighLight(Context context, int var){
		ImageButton imgbtn = null;
		
		if(!(context instanceof Activity)){
			Log.w(context, "context n'est pas une activite !");
			return;
		}
		switch(var){
		case ToolBar.HIGHLIGHT_GPS_STATION :
			Log.d(context, "activation du bouton GPS");
			imgbtn = ((ImageButton) ((Activity) context).findViewById(R.id.btn_go_station));
			if(imgbtn != null){
				imgbtn.setBackgroundColor(Color.rgb(255,127,39));
			}
			break;
		case ToolBar.HIGHLIGHT_LIST :
			Log.d(context, "activation du bouton liste");
			imgbtn = ((ImageButton) ((Activity) context).findViewById(R.id.btn_go_list));
			if(imgbtn != null){
				imgbtn.setBackgroundColor(Color.rgb(255,127,39));
			}
			break;
			
		default :
			Log.w(context, "setHighLight : cas non gere : " + var);
		}
	}

}
