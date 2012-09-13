package com.alstom.power.sep.views;

import com.alstom.power.sep.R;
import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;;


public class TreatmentFlashageCellView extends LinearLayout implements OnClickListener{

	private Activity context;
	private ImageView btn_flashage;
	private LinearLayout view_photo;
	private LinearLayout view_flash;
	

	public TreatmentFlashageCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.treatment_flashage_cell_view, this);
	    
	    this.context = (Activity) context;
	    
	    btn_flashage = (ImageView)findViewById(R.id.btn_flashage);
	    view_photo = (LinearLayout)findViewById(R.id.view_photo);
	    view_flash = (LinearLayout)findViewById(R.id.view_flash);
	    
	    
	    btn_flashage.setOnClickListener(this);
	    
	}
	
	
	public void setData(int position){
		
		if(position == 0){
			view_photo.setVisibility(View.GONE);
			view_flash.setVisibility(View.VISIBLE);
		}
		else if(position == 1){
			view_flash.setVisibility(View.GONE);
			view_photo.setVisibility(View.VISIBLE);
		}
		
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_flashage:
			
			Intent intent = new Intent();
			intent.setClass(context, CaptureActivity.class);
			//intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
			context.startActivityForResult(intent, 0);
			
			break;

		default:
			break;
		}
		
	}
}
