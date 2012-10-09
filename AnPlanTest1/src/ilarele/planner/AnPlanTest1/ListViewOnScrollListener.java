package ilarele.planner.AnPlanTest1;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class ListViewOnScrollListener implements OnScrollListener
{

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		Log.d("log", "onScrollStateChanged " + scrollState);
		
	
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount)
	{
		Log.d("log", "onScroll " + firstVisibleItem + ", " + visibleItemCount + "/" + totalItemCount);
	
	}

}
