package com.alstom.lean.all.activities;





import com.alstom.lean.all.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class StepActivity extends Activity implements OnClickListener{
	
	public static final int RESULT_CODE_TERMINATE = 3;
	private Button btn_terminate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_step);
		
		btn_terminate = (Button)findViewById(R.id.cancelsavesendbar_send);
		
		btn_terminate.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.cancelsavesendbar_send:
			
			setResult(RESULT_CODE_TERMINATE);
			finish();
			
			break;

		default:
			break;
		}
		
	}

}
