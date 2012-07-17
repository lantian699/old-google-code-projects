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
	private List<InfoStation> listInfoStation;
	
	
	
	/* CONSTRUCTEUR */
	public AdapterListPreferStation(Context context, List<InfoStation> listInfoStation, Runnable screenCallback) {
		super();
		
		this.context = context;
		this.listInfoStation = listInfoStation;
		this.myScreenRefreshCallback = screenCallback;
	
	}

	
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		PreferStationCellView cell = (PreferStationCellView) convertView;

		if (cell == null) {
			cell = new PreferStationCellView(context);
		}
		
		InfoStation infoStationPrefer = (InfoStation) listInfoStation.get(position);
		cell.setData(infoStationPrefer,  myScreenRefreshCallback);
		
		return cell;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listInfoStation.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listInfoStation.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
