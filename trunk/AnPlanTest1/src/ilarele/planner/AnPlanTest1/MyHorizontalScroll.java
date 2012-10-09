package ilarele.planner.AnPlanTest1;

import java.util.ArrayList;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyHorizontalScroll extends HorizontalScrollView
{
	private ArrayList<View> observers; 
	
	public void addObserver(View obs)
	{
		observers.add(obs);
	}
	public MyHorizontalScroll(Context context, AttributeSet set)
	{
		super(context, set);
		observers = new ArrayList<View>();
	}
	
	@Override
	public void onScrollChanged(int x, int y, int old_x, int old_y)
	{
		super.onScrollChanged(x, y, old_x, old_y);
		Log.d("log", x + " " + y);
		notifyObservers(x, y);
	}

	private void notifyObservers(int x, int y)
	{
		for (View obs : observers)
		{
			obs.scrollTo(x, y);
		}
	}

}
