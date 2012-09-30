package com.alstom.lean.all.adapters;

import java.sql.SQLException;
import java.util.List;

import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Request;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.models.VisualInspection;
import com.alstom.lean.all.views.AssistanceRequestCellView;
import com.alstom.lean.all.views.TaskDetailCellView;
import com.alstom.lean.all.views.TaskListCellView;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class TaskDetailAdapter extends BaseAdapter {

	
	
	private static final int TASK_DETAIL_COUNT = 1;
	private Context context;
	private DatabaseHelper dataHelper;
	private Task task;
	private List<Mesurement> listMesure;
	private List<VisualInspection> listVI;

	
	public  TaskDetailAdapter(Context context, Task task, DatabaseHelper helper){
		
		this.context = context;
		this.task = task;
		this.dataHelper = helper;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return TASK_DETAIL_COUNT;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup group) {
		// TODO Auto-generated method stub
		TaskDetailCellView view = (TaskDetailCellView) convertView;
		if (view == null) {
			view = new TaskDetailCellView(context, dataHelper);
		}
		
		if(task.getType().equals(TaskListCellView.TASK_TYPE_MESURE)){
			
			
			 try {
					Dao<Mesurement, ?> mesureDao = dataHelper.getDao(Mesurement.class);
					QueryBuilder<Mesurement, ?> queryBuilder = mesureDao.queryBuilder();
					queryBuilder.where().eq(Mesurement.TABLE_MESURE_COLUMN_PARENT, task.getName());
					PreparedQuery<Mesurement> preparedQuery = queryBuilder.prepare();
					listMesure = mesureDao.query(preparedQuery);
					
					
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				view.setData(task,listMesure.get(position), null, position);
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_VI)){
			
			try {
				Dao<VisualInspection, ?> inspectionDao = dataHelper.getDao(VisualInspection.class);
				QueryBuilder<VisualInspection, ?> queryBuilder = inspectionDao.queryBuilder();
				queryBuilder.where().eq(VisualInspection.TABLE_INSPECTION_COLUMN_PARENT, task.getName());
				PreparedQuery<VisualInspection> preparedQuery = queryBuilder.prepare();
				listVI = inspectionDao.query(preparedQuery);
				
				view.setData(task,null, listVI.get(position), position);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_FINDING)){
			
		}
		
		
		
		

		return view;
	}

}
