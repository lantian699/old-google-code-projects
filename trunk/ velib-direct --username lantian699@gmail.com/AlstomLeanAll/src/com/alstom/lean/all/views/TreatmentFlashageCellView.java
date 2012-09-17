package com.alstom.lean.all.views;



import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.PartNumberDetailActivity;
import com.google.zxing.client.android.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;;


public class TreatmentFlashageCellView extends LinearLayout implements OnClickListener{
	
	public static final String EXTRA_NAME_PART_NUMBER = "partnumber";

	public static final int REQUEST_CODE_FLASH = 0;
	public static final int REQUEST_CODE_PHOTO = 1;
	
	private Activity context;
	private ImageView btn_flashage;
	private ImageView btn_photo;
	private LinearLayout view_photo;
	private LinearLayout view_flash;
	private Button btn_part_num;
	private EditText edit_part_num;
	

	public TreatmentFlashageCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.treatment_flashage_cell_view, this);
	    
	    this.context = (Activity) context;
	    
	    btn_flashage = (ImageView)findViewById(R.id.btn_flashage);
	    btn_photo = (ImageView)findViewById(R.id.btn_photo);
	    view_photo = (LinearLayout)findViewById(R.id.view_photo);
	    view_flash = (LinearLayout)findViewById(R.id.view_flash);
	    btn_part_num = (Button)findViewById(R.id.btn_part_number);
	    edit_part_num = (EditText)findViewById(R.id.edit_text_part_num);
	    
	    
	    btn_flashage.setOnClickListener(this);
	    btn_photo.setOnClickListener(this);
	    btn_part_num.setOnClickListener(this);
	    
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
			context.startActivityForResult(intent, REQUEST_CODE_FLASH);
			
			break;
			
		case R.id.btn_photo:
			Intent intent_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			
			context.startActivityForResult(intent_photo, REQUEST_CODE_PHOTO);
			
			
		case R.id.btn_part_number:
			
			String partNumber = edit_part_num.getText().toString();
			if(partNumber != ""){
				
				Intent intent_part_num = new Intent();
				intent_part_num.setClass(context, PartNumberDetailActivity.class);
				intent_part_num.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_part_num.putExtra(EXTRA_NAME_PART_NUMBER, partNumber);
				context.startActivity(intent_part_num);
				
				
			}
			
			break;

		default:
			break;
		}
		
	}
}
