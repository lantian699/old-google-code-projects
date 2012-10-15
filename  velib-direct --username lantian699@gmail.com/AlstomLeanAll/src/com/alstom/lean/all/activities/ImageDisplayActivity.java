package com.alstom.lean.all.activities;



import com.alstom.lean.all.fragments.MyProjectListFragment;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageDisplayActivity extends Activity{
	
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		
		
		
		int resID = getIntent().getIntExtra(MyProjectListFragment.RESOURCE_ID, -1);
		imageView = new ImageView(this);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		if(resID != -1){
	
			imageView.setImageResource(resID);
			
		}
		
		setContentView(imageView);
	}

}
