package ilarele.planner.AnPlanTest1;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.TextView;

public class AnPlanTest1 extends ListActivity implements
		OnScrollChangedListener
{

	// menu entries
	private static final int MENU_ADD_TASK = 0;

	// GUI
	private TaskAdapter task_adapter;
	private MyHorizontalScroll timescale_scroll_d;
	private MyHorizontalScroll timescale_scroll_w;
	private MyHorizontalScroll timescale_scroll_m;
	private MyHorizontalScroll timescale_scroll_y;
	private MyHorizontalScroll listview_h_scroll;

	private TextView timescale_d;
	private TextView timescale_w;
	private TextView timescale_m;
	private TextView timescale_y;
	
	
	
	
	// content
	private Project project;



	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		project = new Project();
		task_adapter = new TaskAdapter(this, R.layout.task_layout, getTasks(), project);

		setListAdapter(task_adapter);
		addDummyTasks(30);

		getListView().setOnScrollListener(new ListViewOnScrollListener());
		
		initScrollBars();
		initTimescales();
	}



	private void addDummyTasks(int n)
	{
		for (int i = 0; i < n; i++)
		{
			Calendar start = Calendar.getInstance();
			start.set(110, 9, 1 + i);
			Task new_dummy = new Task(getTasks().size(), start);
			getTasks().add(new_dummy);
			task_adapter.notifyDataSetChanged();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0, MENU_ADD_TASK, 0, R.string.menu_add_task).setIcon(
				android.R.drawable.ic_menu_add);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		super.onOptionsItemSelected(item);
		switch (item.getItemId())
		{
		case MENU_ADD_TASK:
			Task new_dummy = new Task(getTasks().size());
			getTasks().add(new_dummy);
			task_adapter.notifyDataSetChanged();
			return true;
		}

		return false;
	}

	private ArrayList<Task> getTasks()
	{
		return project.getAllTasks();
	}

	@Override
	public void onScrollChanged()
	{
		Log.d("log", "onScrollChanged");

	}

	
	private void initScrollBars()
	{
		// init Scroll
		timescale_scroll_d = (MyHorizontalScroll) findViewById(R.id.scroll_timescale_day);
		timescale_scroll_w = (MyHorizontalScroll) findViewById(R.id.scroll_timescale_week);
		timescale_scroll_m = (MyHorizontalScroll) findViewById(R.id.scroll_timescale_month);
		timescale_scroll_y = (MyHorizontalScroll) findViewById(R.id.scroll_timescale_year);
		
		listview_h_scroll = (MyHorizontalScroll) findViewById(R.id.list_scroll);
		
		// set observers
		timescale_scroll_d.addObserver(listview_h_scroll);
		timescale_scroll_d.addObserver(timescale_scroll_w);
		timescale_scroll_d.addObserver(timescale_scroll_m);
		timescale_scroll_d.addObserver(timescale_scroll_y);

		timescale_scroll_w.addObserver(listview_h_scroll);
		timescale_scroll_w.addObserver(timescale_scroll_d);
		timescale_scroll_w.addObserver(timescale_scroll_m);
		timescale_scroll_w.addObserver(timescale_scroll_y);
		
		
		timescale_scroll_m.addObserver(listview_h_scroll);
		timescale_scroll_m.addObserver(timescale_scroll_d);
		timescale_scroll_m.addObserver(timescale_scroll_w);
		timescale_scroll_m.addObserver(timescale_scroll_y);
		
		timescale_scroll_y.addObserver(listview_h_scroll);
		timescale_scroll_y.addObserver(timescale_scroll_d);
		timescale_scroll_y.addObserver(timescale_scroll_w);
		timescale_scroll_y.addObserver(timescale_scroll_m);
		
		listview_h_scroll.addObserver(timescale_scroll_d);
		listview_h_scroll.addObserver(timescale_scroll_w);
		listview_h_scroll.addObserver(timescale_scroll_m);
		listview_h_scroll.addObserver(timescale_scroll_y);
	}
	

	private void initTimescales()
	{
		// init timescales
		timescale_d = (TextView) findViewById(R.id.timescale_day);
		timescale_w = (TextView) findViewById(R.id.timescale_week);
		timescale_m = (TextView) findViewById(R.id.timescale_month);
		timescale_y = (TextView) findViewById(R.id.timescale_year);
		
		
		// fill Timescales
		
		Calendar start = project.getStartDate();
		int start_m = start.get(Calendar.MONTH);
		int start_d = start.get(Calendar.DAY_OF_MONTH);

		Log.d("log", "prj start " + start_d + " / " +start_m);
		Calendar end = project.getFinishDate();
		int end_m = end.get(Calendar.MONTH);
		int end_d = end.get(Calendar.DAY_OF_MONTH);
		Log.d("log", "prj end " + end_d + " / " + end_m);

		int start_d_of_m = start_d;
		int end_d_of_m = end_d;
		
		
		StringBuffer aux_d = new StringBuffer();
		StringBuffer aux_m = new StringBuffer();
		
		for (int i = start_m; i <= end_m; i++)
		{
		
				
			if (i == end_m)
				end_d_of_m = end_d;
			else
				end_d_of_m = ConstantValues.getDays(i, start.get(Calendar.YEAR));
				
			int nr_days_in_m = end_d_of_m - start_d_of_m;
			for (int k = 0; k < nr_days_in_m; k++)
			{
				if (k != nr_days_in_m/2)
				{
					aux_m.append("      ");
				}
				else
				{
				if (i >= 10)
					aux_m.append("  " + i + "   ");
				else
					aux_m.append("  " + i + "    ");
				}
			}
			for (int j = start_d_of_m; j <= end_d_of_m; j++)
			{
				if (j >= 10)
					aux_d.append("  " + j + "  |");
				else
					aux_d.append("  " + j + "   |");
			}
			start_d_of_m = 0;
		}
		
		timescale_d.setText(aux_d.toString());
		timescale_m.setText(aux_m.toString());
	}

}
