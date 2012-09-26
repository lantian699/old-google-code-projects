package com.alstom.lean.all.views;


import java.util.ArrayList;

import org.vudroid.R.id;

import com.alstom.lean.all.R;
import com.alstom.lean.all.fragments.StepFragment;
import com.alstom.lean.all.fragments.TaskMesureFragment;
import com.alstom.lean.all.models.Task;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SelectTaskListCellView extends LinearLayout{
	
	private CheckBox cb_task;
	private TextView select_task_label;
	private Button btn_add_subtask;
	private FragmentActivity context;
	private ListView listSmallTask;
	private ArrayAdapter<String> listAdapter;
	

	public SelectTaskListCellView(Context context) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.select_task_cell_view, this);
	    
	    this.context = (FragmentActivity) context;
	    
	    cb_task = (CheckBox)findViewById(R.id.cb_task);
	    select_task_label = (TextView)findViewById(R.id.select_task_label);
	    listSmallTask = (ListView)findViewById(R.id.list_small_task);
	    btn_add_subtask = (Button)findViewById(R.id.btn_add_subtask);
    	
	    cb_task.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

				if(isChecked){
					listSmallTask.setVisibility(View.VISIBLE);
				}else{
					listSmallTask.setVisibility(View.GONE);
				}
				
			}
		});	
	    
	    
	    btn_add_subtask.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				final CharSequence[] items = {"Mesurement", "Visual inspection", "Finding"};

				AlertDialog.Builder builder = new AlertDialog.Builder(SelectTaskListCellView.this.context);
				builder.setTitle("Choose the task type");
				builder.setItems(items, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {

				    	switch (item) {
						case 0:
							
							TaskMesureFragment mesureFragment = new TaskMesureFragment(SelectTaskListCellView.this.context, null);
							
							SelectTaskListCellView.this.context.getSupportFragmentManager().beginTransaction()
							.replace(R.id.activity_detail_container_2, mesureFragment).commit();
							
							break;

						default:
							break;
						}
				    	
				    }
				});
				AlertDialog alert = builder.create();
				alert.show();
				
			}
		});
	    
	}
	
	
	public void setData(final Task task, final int position){
		
		select_task_label.setText(task.getName());
		
		listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, getData());

	    listSmallTask.setAdapter(listAdapter);
		
	}
	
	public ArrayList<String> getData(){
		
		ArrayList<String> listName = new ArrayList<String>();
		
		listName.add("visual inspect Rator 1");
		listName.add("mesure Rator 1");
		listName.add("visual inspect Rator 2");
		listName.add("mesure Rator 2");
		
		
		return listName;
	}
		

}
