package com.alstom.lean.all.activities;



import com.alstom.lean.all.fragments.ComponentListFragment;
import com.alstom.lean.all.fragments.MyProjectListFragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageDisplayActivity extends Activity{
	
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		Uri uriImage = getIntent().getParcelableExtra(ComponentListFragment.IMAGE_RESSOURCE_URI);
		imageView = new ImageView(this);
		imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
		if(uriImage != null){
	
			imageView.setImageURI(uriImage);
			
		}
		
		setContentView(imageView);
	}

}
