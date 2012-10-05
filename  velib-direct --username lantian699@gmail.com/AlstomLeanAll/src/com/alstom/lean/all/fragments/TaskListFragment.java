package com.alstom.lean.all.fragments;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.ImageDisplayActivity;
import com.alstom.lean.all.adapters.TaskListAdapter;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.model3d.Model3DTurbineActivity;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.pdfviewer.PdfViewerActivity;
import com.alstom.lean.all.views.TaskListCellView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

public class TaskListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener,OnClickListener{

	
	/*public static final String TASK_TYPE_MESUREMENT = "MESUREMENT";
	public static final String TASK_TYPE_VI = "VISUAL INSPECTION";
	public static final String TASK_TYPE_FINDING = "FINDING";
	*/
	private ListView taskListView;
	private TaskListAdapter adapter;
	private List<Task> listTasks;
	private Project project;
	private DatabaseHelper dataHelper;
	private TaskListManager taskListManager;
	private Button btn_add_task;
	private Task task_select;
	private Button btn_filter;
	private Dao<Task, ?> taskDao;


	
	public TaskListFragment(Project project, DatabaseHelper helper, TaskListManager manager){
		this.project = project;
		this.dataHelper = helper;
		this.taskListManager = manager;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    try {
			taskDao = dataHelper.getDao(Task.class);
			QueryBuilder<Task, ?> queryBuilder = taskDao.queryBuilder();
			queryBuilder.where().eq(Task.TABLE_TASK_COLUMN_PARENT_NAME, project.getName());
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
	    adapter = new TaskListAdapter(getActivity(), listTasks, taskListManager);
	    
	    taskListView.setAdapter(adapter);
	    taskListView.setOnItemClickListener(this);
	    
	    btn_add_task = (Button)rootView.findViewById(R.id.btn_add_task);
	    btn_add_task.setOnClickListener(this);
	    
	    btn_filter = (Button)rootView.findViewById(R.id.btn_filter);
	    btn_filter.setOnClickListener(this);
	    
	    taskListView.setOnItemLongClickListener(this);
	    
	    taskListManager.registerRefreshListChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {
				// TODO Auto-generated method stub
				try {
					listTasks = taskDao.queryForAll();
					adapter = new TaskListAdapter(getActivity(), listTasks, taskListManager);
					taskListView.setAdapter(adapter);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onChange() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChange(String res, ModelObject model) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    taskListManager.registerTaskFilterChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {
				// TODO Auto-generated method stub
				
				ArrayList<Task> listTaskFiltered = new ArrayList<Task>();
				for (Task task : listTasks) {
					
					if(task.getType().equals(res))
						listTaskFiltered.add(task);
					else if(res.equals("ALL"))
						listTaskFiltered.add(task);
				}
				
				adapter = new TaskListAdapter(getActivity(), listTaskFiltered, taskListManager);
				taskListView.setAdapter(adapter);
			}
			
			@Override
			public void onChange() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChange(String res, ModelObject model) {
				// TODO Auto-generated method stub
				
			}
		});
	    
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
	public boolean onItemLongClick(AdapterView<?> adapterView, View v, int position, long id) {
		
		final Task task_adapter = (Task) adapterView.getAdapter().getItem(position);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage("Are you sure you want to delete this task?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               
		        	   try {
						taskDao.delete(task_adapter);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        	   
		        	   taskListManager.notifyRefreshListChange("");
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		alert.show();
		
		return false;
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btn_add_task:
			
			final CharSequence[] items = {"MESUREMENT", "VISUAL INSPECTION", "FINDING"};
			
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle("TASK TYPE");
			builder.setItems(items, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getActivity(), items[item], Toast.LENGTH_SHORT).show();
			             
			        switch (item) {
					case 0:
						Fragment fragmentMesure = new AddTaskFragment(project,TaskListCellView.TASK_TYPE_MESURE,dataHelper, taskListManager);
						getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.activity_detail_container_2, fragmentMesure).commit();
						
						break;
						
					case 1:
						Fragment fragmentVi= new AddTaskFragment(project, TaskListCellView.TASK_TYPE_VI,dataHelper, taskListManager);
						getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.activity_detail_container_2, fragmentVi).commit();
						
						break;
						
					case 2:
						Fragment fragmentF= new AddTaskFragment(project, TaskListCellView.TASK_TYPE_FINDING,dataHelper, taskListManager);
						getActivity().getSupportFragmentManager().beginTransaction()
						.replace(R.id.activity_detail_container_2, fragmentF).commit();
						
						break;

					default:
						break;
					}
			        
			    }
			});
			AlertDialog alert = builder.create();
			alert.show();
			
			break;
			
		case R.id.btn_filter:
				
			final CharSequence[] items_filter = {"MESUREMENT", "VISUAL INSPECTION", "FINDING", "ALL"};
			
			AlertDialog.Builder builder_filter = new AlertDialog.Builder(getActivity());
			builder_filter.setTitle("TASK TYPE FILTER");
			builder_filter.setItems(items_filter, new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog, int item) {
			        Toast.makeText(getActivity(), items_filter[item], Toast.LENGTH_SHORT).show();
			             
			        switch (item) {
					case 0:
						taskListManager.notifyTaskFilterChange(TaskListCellView.TASK_TYPE_MESURE);
						
						break;
						
					case 1:
						taskListManager.notifyTaskFilterChange(TaskListCellView.TASK_TYPE_VI);
						
						break;
						
						
					case 2:
						
						taskListManager.notifyTaskFilterChange(TaskListCellView.TASK_TYPE_FINDING);
						break;

					case 3:
						
						taskListManager.notifyTaskFilterChange("ALL");
						break;
					default:
						break;
					}
			        
			    }
			});
			AlertDialog alert_filter = builder_filter.create();
			alert_filter.show();
				
				break;

		default:
			break;
		}
		
	}

	

	
}
