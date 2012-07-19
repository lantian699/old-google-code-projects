package velib.views;

import essai.cnam.ListeStationAlentourActivity;
import essai.cnam.ListeStationVelibActivity;
import essai.cnam.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.view.View.OnClickListener;

public class ToolBar extends LinearLayout implements OnClickListener{
	
	public static final int HIGHLIGHT_GPS_STATION= 0;
	public static final int HIGHLIGHT_LIST = 1;
	
	private Context context;

	public ToolBar(Context context, AttributeSet attrs) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.toolbar, this);
		
		findViewById(R.id.btn_go_station).setOnClickListener(this);
		findViewById(R.id.btn_go_list).setOnClickListener(this);
		
		Drawable drawable = getResources().getDrawable(R.drawable.toolbar_green_dark_background);
		setBackgroundDrawable(drawable);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.btn_go_station:
			
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setClass(context, ListeStationAlentourActivity.class);
			context.startActivity(intent);
			
			break;
			
		case R.id.btn_go_list:
			
			Intent intent_list = new Intent();
			intent_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_list.setClass(context, ListeStationVelibActivity.class);
			context.startActivity(intent_list);
			
			break;

		default:
			break;
		}
		
	}

}
