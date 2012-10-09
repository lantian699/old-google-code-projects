package ilarele.planner.AnPlanTest1;

import java.util.Calendar;

public class Task
{
	private String name = "dummy";
	private String description;
	private Calendar start_date;
	private int duration;
	private int uid;
	
	public Task(int unique_id)
	{
		this.uid = unique_id;
		this.name = "task_nr" + unique_id;
		this.start_date = Calendar.getInstance();
		this.start_date.set(110, 9, 15);
		this.duration = 1;
	}
	
	public Task(int unique_id, Calendar start)
	{
		this.uid = unique_id;
		this.name = "task_nr" + unique_id;
		this.start_date = start;
		this.duration = 1;
	}
	
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Calendar getStartDate()
	{
		return start_date;
	}
	public void setStartDate(Calendar startDate)
	{
		start_date = startDate;
	}
	/**
	 * 
	 * @return Task's duration, in days. 
	 */
	public int getDuration()
	{
		return duration;
	}
	/**
	 * 
	 * @param duration Task's duration, in days.
	 */
	public void setDuration(int duration)
	{
		this.duration = duration;
	}
	public void setUid(int uid)
	{
		this.uid = uid;
	}
	public int getUid()
	{
		return uid;
	}
}
