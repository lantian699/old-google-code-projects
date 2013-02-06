package essai.cnam;

import java.util.List;

import velib.model.StationVelib;
import velib.views.ListPrincipalCellView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.actionbarsherlock.app.SherlockActivity;

public class AdapterListPrincipal extends BaseAdapter{

	private SherlockActivity context;
	private List<StationVelib> listStation;
	
	public AdapterListPrincipal(SherlockActivity context, List<StationVelib> listStation){
		
		this.context = context;
		this.listStation = listStation;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		ListPrincipalCellView cell = (ListPrincipalCellView) convertView;
		
		if(cell == null){
			cell = new ListPrincipalCellView(context);
		}
		
		StationVelib station = getItem(position);
		
		cell.setData(station);
		
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
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
