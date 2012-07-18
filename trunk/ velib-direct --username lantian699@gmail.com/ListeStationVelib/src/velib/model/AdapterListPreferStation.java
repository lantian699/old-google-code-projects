package velib.model;

import java.util.ArrayList;
import java.util.List;

import velib.views.PreferStationCellView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AdapterListPreferStation extends BaseAdapter{
	
	private Context context;
	private Runnable myScreenRefreshCallback;
	private List<StationVelib> listStation;
	
	
	
	/* CONSTRUCTEUR */
	public AdapterListPreferStation(Context context, List<StationVelib> listStation, Runnable screenCallback) {
		super();
		
		this.context = context;
		this.listStation = listStation;
		this.myScreenRefreshCallback = screenCallback;
	
	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		PreferStationCellView cell = (PreferStationCellView) convertView;

		if (cell == null) {
			cell = new PreferStationCellView(context);
		}
		
		StationVelib station = listStation.get(position);
		cell.setData(station,  myScreenRefreshCallback);
		
		return cell;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listStation.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listStation.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
