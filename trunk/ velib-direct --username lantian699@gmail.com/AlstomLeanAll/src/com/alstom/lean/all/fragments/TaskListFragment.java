package com.alstom.lean.all.fragments;

import java.sql.SQLException;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.adapters.TaskListAdapter;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Task;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class TaskListFragment extends Fragment implements OnItemClickListener{

	private ListView taskListView;
	private TaskListAdapter adapter;
	private List<Task> listTasks;
	private Project project;
	private DatabaseHelper dataHelper;
	
	
	public TaskListFragment(Project project, DatabaseHelper helper){
		this.project = project;
		this.dataHelper = helper;
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
	    
	    return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		TaskDetailFragment fragment = new TaskDetailFragment(getActivity(), null, listTasks.get(position), dataHelper);
		getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_detail_container_2, fragment).commit();
		
	}
}
