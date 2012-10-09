package ilarele.planner.AnPlanTest1;

import java.util.ArrayList;
import java.util.Calendar;

public class Project
{
	// Duration units
	public static final int DURATION_DAYS = 0;
	public static final int DURATION_WEEKS = 1;
	public static final int DURATION_MONTHS = 2;
	
	private static final int SIZE_DAYS = 56;
	private static final int SIZE_WEEKS = 15;
	private static final int SIZE_MONTHS = 4;
	private static final long MILLSECS_PER_DAY = 86400000;

	private ArrayList<Task> all_tasks;
	private Calendar start_date;
	private Calendar finish_date;
	private int time_units;

	public Project()
	{
		this.all_tasks = new ArrayList<Task>();
		this.time_units = DURATION_DAYS;
		this.start_date = Calendar.getInstance();
		this.start_date.set(110, 9, 1);
		this.finish_date = Calendar.getInstance();
		this.finish_date.set(110, 10, 1);
	}

	public void setAllTasks(ArrayList<Task> all_tasks)
	{
		this.all_tasks = all_tasks;
	}

	public ArrayList<Task> getAllTasks()
	{
		return all_tasks;
	}

	public int getDaySize()
	{
		switch (time_units)
		{
		case DURATION_DAYS:
			return SIZE_DAYS;
		case DURATION_WEEKS:
			return SIZE_WEEKS;
		case DURATION_MONTHS:
			return SIZE_MONTHS;
		default:
			return SIZE_DAYS;
		}
	}

	public int getPadding(Calendar task_start)
	{
		int days_diff = (int) ((task_start.getTimeInMillis() - start_date.getTimeInMillis())/ MILLSECS_PER_DAY);
		return days_diff * getDaySize();
	}

	public Calendar getStartDate()
	{
		return start_date;
	}

	public void setStartDate(Calendar startDate)
	{
		start_date = startDate;
	}

	public Calendar getFinishDate()
	{
		return finish_date;
	}

	public void setFinishDate(Calendar finishDate)
	{
		finish_date = finishDate;
	}
}
