package velib.model;

import java.util.List;

import velib.views.PreferStationCellView;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

public class AdapterListPreferStation extends BaseAdapter{
	
	private Activity context;
	private Runnable myScreenRefreshCallback;
	private List<StationVelib> listStation;
	private ListView listView;
	
	
	/* CONSTRUCTEUR */
	public AdapterListPreferStation(Activity context, List<StationVelib> listStation, ListView listView,  Runnable screenCallback) {
		super();
		
		this.context = context;
		this.listStation = listStation;
		this.myScreenRefreshCallback = screenCallback;
		this.listView = listView;
	
	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		PreferStationCellView cell = (PreferStationCellView) convertView;

		if (cell == null) {
			cell = new PreferStationCellView(context, listView);
		}
		
		StationVelib station = getItem(position);
		cell.setData(station,  myScreenRefreshCallback);
		
		return cell;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listStation.size();
	}

	@Override
	public StationVelib getItem(int position) {
		// TODO Auto-generated method stub
		return listStation.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
