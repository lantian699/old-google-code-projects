package ilarele.planner.AnPlanTest1;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TaskAdapter extends ArrayAdapter<Task>
{
	private LayoutInflater m_inflater;
	private Project project;
	private int layout_item_id;
	private int min = 0;
	
	
	public TaskAdapter(Context context, int layout_item_id,
			List<Task> items_list, Project prj)
	{
		super(context, layout_item_id, items_list);
		this.layout_item_id = layout_item_id;
		this.project = prj;
		m_inflater = LayoutInflater.from(context);
	}

	@Override
	public boolean hasStableIds()
	{
		return true;
	}

	@Override
	public long getItemId(int position)
	{
		return getItem(position).getUid();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Task crt_task = getItem(position);
		if (convertView == null)
		{
			convertView = m_inflater.inflate(layout_item_id, parent, false);
		}

		// GUI
		TextView task_name = (TextView) convertView.findViewById(R.id.task_name);
		task_name.setText(crt_task.getName());
		ImageButton task_pic = (ImageButton) convertView.findViewById(R.id.task_pic);
		
		// min size
		int task_pic_dp = getDP(project.getPadding(crt_task.getStartDate()));
		int task_min = getDP(task_name.getWidth()) + task_pic_dp + getDP(task_pic.getWidth()) + 100;
		
		// padding
		RelativeLayout.LayoutParams pic_layout = new RelativeLayout.LayoutParams(
				getDP(50), getDP(10));
		pic_layout.addRule(RelativeLayout.CENTER_VERTICAL,
				RelativeLayout.TRUE);
		pic_layout.leftMargin = task_pic_dp;
		task_pic.setLayoutParams(pic_layout);
		
		if (task_min > min)
			min = task_min;
		else
			task_name.setMinWidth(min);

		return convertView;
	}
	private int getDP(int pix)
	{
		return (int) (getContext().getResources().getDisplayMetrics().density * pix);
	}
}
