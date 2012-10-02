package com.alstom.lean.all.fragments;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.alstom.lean.all.R;
import com.alstom.lean.all.adapters.TaskListAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.views.TaskListCellView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class TaskListFragment extends Fragment implements OnItemClickListener, OnClickListener{

	private ListView taskListView;
	private TaskListAdapter adapter;
	private List<Task> listTasks;
	private Project project;
	private DatabaseHelper dataHelper;
	private TaskListManager taskListManager;
	private Button btn_finding;
	private Task task_select;
	
	
	public TaskListFragment(Project project, DatabaseHelper helper, TaskListManager manager){
		this.project = project;
		this.dataHelper = helper;
		this.taskListManager = manager;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    try {
			Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
			QueryBuilder<Task, ?> queryBuilder = taskDao.queryBuilder();
			queryBuilder.where().eq(Task.TABLE_TASK_COLUMN, project.getName());
			PreparedQuery<Task> preparedQuery = queryBuilder.prepare();
			listTasks = taskDao.query(preparedQuery);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.fragment_task_list, container, false);
	    
	    taskListView = (ListView) rootView.findViewById(R.id.task_list_view);
	    adapter = new TaskListAdapter(getActivity(), listTasks, null);
	    
	    taskListView.setAdapter(adapter);
	    taskListView.setOnItemClickListener(this);
	    
	    btn_finding = (Button)rootView.findViewById(R.id.btn_add_finding);
	    btn_finding.setOnClickListener(this);
	    return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		this.task_select = listTasks.get(position);
		ImageView flash = (ImageView) view.findViewById(R.id.flash);		
		flash.setVisibility(View.VISIBLE);
			
		Object tag = parent.getTag();
		if(tag != null){
			
			((TaskListCellView) tag).findViewById(R.id.flash).setVisibility(View.INVISIBLE);
		}
		
		parent.setTag(view);
//		System.out.println("layout = " + view + " "+view.getParent());
		TaskDetailFragment fragment = new TaskDetailFragment(getActivity(), taskListManager, listTasks.get(position), dataHelper);
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_detail_container_2, fragment).commit();
		
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_add_finding:
			
			Fragment fragment = new AddFindingFragment(task_select);
			getActivity().getSupportFragmentManager().beginTransaction()
			.replace(R.id.activity_detail_container_2, fragment).commit();
			
			break;

		default:
			break;
		}
		
	}
}
