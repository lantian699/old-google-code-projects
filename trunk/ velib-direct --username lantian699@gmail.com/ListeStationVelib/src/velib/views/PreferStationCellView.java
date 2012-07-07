package velib.views;

import essai.cnam.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class PreferStationCellView extends LinearLayout{

	private Context context;

	public PreferStationCellView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		this.context = context;

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		inflater.inflate(R.layout.list_prefer_station, this);
		
		
	}
	
	
	
	

}
