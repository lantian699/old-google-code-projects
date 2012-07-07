package velib.model;

import java.util.ArrayList;

import velib.views.PreferStationCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterListPreferStation extends BaseAdapter{
	
	private Context context;
	private Runnable myScreenRefreshCallback;

	/* CONSTRUCTEUR */
	public AdapterListPreferStation(Context context, ArrayList<String> listeTournees, Runnable screenCallback) {
		super();
		this.context = context;
	
		myScreenRefreshCallback = screenCallback;
	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		PreferStationCellView cell = (PreferStationCellView) convertView;

		if (cell == null) {
			cell = new PreferStationCellView(context);
		}

		
		//cell.setData(tournee, activer, myScreenRefreshCallback);

		return cell;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
