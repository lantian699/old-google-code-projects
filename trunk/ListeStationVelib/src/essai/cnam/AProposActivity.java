package essai.cnam;

import velib.activitymodel.FirstScreenActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

public class AProposActivity extends FirstScreenActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apropos);
		
	    ((TextView)findViewById(R.id.dialog_text)).setText(R.string.dialog_content);
	}

	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			
			this.finish();
			
			break;

		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}
}
