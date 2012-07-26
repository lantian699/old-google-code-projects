package essai.cnam;

import android.os.Bundle;
import android.widget.TextView;
import velib.activitymodel.FirstScreenActivity;

public class AProposActivity extends FirstScreenActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 setContentView(R.layout.apropos);
	        ((TextView)findViewById(R.id.dialog_text)).setText(R.string.dialog_content);
	}

}
