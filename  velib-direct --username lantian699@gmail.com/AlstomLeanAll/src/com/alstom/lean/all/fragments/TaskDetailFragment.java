package com.alstom.lean.all.fragments;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.MesureDetailActivity;
import com.alstom.lean.all.adapters.SectionedAdapter;
import com.alstom.lean.all.flashage.CaptureFlashageActivity;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Task;
import com.alstom.lean.all.views.TaskListCellView;
import com.j256.ormlite.dao.Dao;
import com.picture.drawing.ui.navigation.activity.PictureDrawingActivity;
import com.picture.drawing.ui.utils.BitmapHelper;

public class TaskDetailFragment extends Fragment implements OnClickListener{

	
	public static final int RESULT_CODE_TERMINATE = 3;
	public static final int REQUEST_CODE_FLASH = 0;

	public static final String TASK_NAME = "task";

	public static final int CAPTURE_CODE = 2;
	
	
	private static int pos_terminate=0;
	private Button btn_terminate;
	private Button btn_cancel;
	private FragmentActivity context;
	private TaskListManager taskListManager;
	private Task task;
	private ListView listViewTask;
	private DatabaseHelper dataHelper;
	private SectionedAdapter sectionedAdapter;
	
	private EditText edit_part_num;
	
	private TextView description;
	private TextView edit_unit;
	private TextView edit_start_date;
	private TextView edit_end_date;
	private TextView edit_status;
	
	private String part_num;
	
	
	private ImageView btn_barcode;
	private ImageButton btn_photo;
	private Button btn_mesure;
	private LinearLayout ll_td_unit;
	private List<Mesurement> listMesure;
	private String taskType;
	private Button btn_tag_photo;
	private ImageView photo_display;
	private int year;
	private int monthOfYear;
	private int dayOfMonth;
	private static Uri currentImageUri;
	private Dao<Task, ?> taskDao;

	public TaskDetailFragment(Context context, TaskListManager manager, Task task, DatabaseHelper helper){
		
		this.context = (FragmentActivity) context;
		this.taskListManager = manager;
		this.task = task;
		this.dataHelper = helper;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    
	    Calendar calendar = Calendar.getInstance();
	    year = calendar.get(Calendar.YEAR);
	    monthOfYear = calendar.get(Calendar.MONTH);
	    dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	    
	    taskListManager.registerDisplayPhotoObserver(new ChangeObserver() {
			
			

			@Override
			public void onChange(String res) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onChange() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChange(String res, ModelObject model) {
				// TODO Auto-generated method stub
				
				Task task_res = (Task) model;
				
				File pictureFile = new File(res);
				
				if(task_res.getName().equals(task.getName())){
					Bitmap brackgroundPictureBitmap = BitmapHelper.retreiveBitmapFromPath(600, 200, pictureFile.getPath());
					photo_display.setImageBitmap(brackgroundPictureBitmap);
					
					try {
						taskDao = dataHelper.getDao(Task.class);
						task.setAttachment(res);
						taskDao.update(task);	
						
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
	    
	    taskListManager.registerBarcodeChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {
				edit_part_num.setText(res);
			}

			@Override
			public void onChange() {
			
			}

			@Override
			public void onChange(String res, ModelObject model) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	    
	    
	    
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
	    View rootView = inflater.inflate(R.layout.task_detail_cell_view, container, false);
	
	    description = (TextView)rootView.findViewById(R.id.tx_td_description);
	    edit_start_date = (TextView)rootView.findViewById(R.id.edit_td_start_date);
	    edit_end_date = (TextView)rootView.findViewById(R.id.edit_td_end_date);
	    edit_part_num = (EditText)rootView.findViewById(R.id.edit_td_part_num);
	    edit_status = (EditText)rootView.findViewById(R.id.edit_td_status);
	    edit_unit = (EditText)rootView.findViewById(R.id.edit_td_unit);
	    ll_td_unit = (LinearLayout)rootView.findViewById(R.id.ll_td_unit);
	    
	    btn_barcode = (ImageView)rootView.findViewById(R.id.btn_bar_code);
	    btn_photo = (ImageButton)rootView.findViewById(R.id.btn_take_photo);
	    btn_mesure = (Button)rootView.findViewById(R.id.btn_take_mesure);
	    btn_tag_photo = (Button)rootView.findViewById(R.id.btn_tag_photo);
	    
	    photo_display = (ImageView) rootView.findViewById(R.id.img_selected_photo);
	    
	    
	    
	    btn_terminate = (Button)rootView.findViewById(R.id.cancelsavesendbar_send);
		btn_cancel = (Button)rootView.findViewById(R.id.cancelsavesendbar_cancel);

		if(task.getType().equals(TaskListCellView.TASK_TYPE_MESURE)){
			btn_terminate.setVisibility(View.GONE);
			btn_cancel.setVisibility(View.GONE);
		}
		
		if(task.getAttachment()!= null){
		Bitmap brackgroundPictureBitmap = BitmapHelper.retreiveBitmapFromPath(600, 200, task.getAttachment());
		photo_display.setImageBitmap(brackgroundPictureBitmap);
		}
		
		description.setText(task.getName());
		edit_unit.setText(task.getType());
		edit_status.setText(task.getStatus());
		edit_start_date.setText(task.getBegin());
		edit_end_date.setText(task.getEnd());
		
		
		btn_terminate.setOnClickListener(this);
		btn_cancel.setOnClickListener(this);
		edit_start_date.setOnClickListener(this);
		edit_end_date.setOnClickListener(this);
		btn_barcode.setOnClickListener(this);
	    btn_photo.setOnClickListener(this);
	    btn_mesure.setOnClickListener(this);
	    btn_tag_photo.setOnClickListener(this);
	    
	    
		if(task.getType().equals(TaskListCellView.TASK_TYPE_MESURE)){
			
			
			btn_mesure.setVisibility(View.VISIBLE);	
			taskType = TaskListCellView.TASK_TYPE_MESURE;
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_VI)){
			
			
			btn_mesure.setVisibility(View.GONE);
			
			taskType = TaskListCellView.TASK_TYPE_VI;
			
		}else if(task.getType().equals(TaskListCellView.TASK_TYPE_FINDING)){
			btn_mesure.setVisibility(View.GONE);
			
			taskType = TaskListCellView.TASK_TYPE_FINDING;
		}
		
	
	    return rootView;
	}
	
	@Override
	public void onClick(View v) {
	
		switch (v.getId()) {
		case R.id.cancelsavesendbar_send:
			
			task.setBegin(edit_start_date.getText().toString());
			task.setEnd(edit_end_date.getText().toString());
			task.setStatus(edit_status.getText().toString());
			
			try{
				Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
				taskDao.update(task);
			}catch (Exception e) {
				// TODO: handle exception
			}
			getFragmentManager().beginTransaction().remove(this).commit();
		//	taskListManager.notifyAddMesureChange();
			break;
			
		case R.id.cancelsavesendbar_cancel:
			
			getFragmentManager().beginTransaction().remove(this).commit();
			break;
			
			
		case R.id.btn_bar_code:
			
			Intent intent = new Intent();
			intent.setClass(context, CaptureFlashageActivity.class);
			context.startActivityForResult(intent, REQUEST_CODE_FLASH);
			
			break;
			
		case R.id.btn_take_photo:
			
			Intent intent_take_photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			currentImageUri = createPictureInMediaStore();
			intent_take_photo.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_take_photo.putExtra(MediaStore.EXTRA_OUTPUT, currentImageUri);
			startActivityForResult(intent_take_photo, CAPTURE_CODE);

			break;
			
	
		case R.id.btn_take_mesure:
			
			
			task.setBegin(edit_start_date.getText().toString());
			task.setEnd(edit_end_date.getText().toString());
			task.setStatus(edit_status.getText().toString());
			
			try{
				Dao<Task, ?> taskDao = dataHelper.getDao(Task.class);
				taskDao.update(task);
			}catch (Exception e) {
				// TODO: handle exception
			}
			
			Intent intent_mesure = new Intent();
			intent_mesure.setClass(context, MesureDetailActivity.class);
			intent_mesure.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_mesure.putExtra(TASK_NAME, task);
			context.startActivity(intent_mesure);
	
			break;
			
		case R.id.btn_tag_photo:
			
			Intent in_tag = new Intent();
			in_tag.setClass(context, PictureDrawingActivity.class);
			context.startActivity(in_tag);
			
			break;
			
		case R.id.edit_td_start_date:
			
			new DatePickerDialog(getActivity(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					edit_start_date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
				}
			}, year, monthOfYear, dayOfMonth).show();
			
			break;
			
		case R.id.edit_td_end_date:
			
			new DatePickerDialog(getActivity(), new OnDateSetListener() {
				
				@Override
				public void onDateSet(DatePicker view, int year, int monthOfYear,
						int dayOfMonth) {
					// TODO Auto-generated method stub
					edit_end_date.setText(dayOfMonth+"/"+monthOfYear+"/"+year);
				}
			}, year, monthOfYear, dayOfMonth).show();
			
			break;
	
		default:
			break;
		}
		
	}
	
	
	public Uri createPictureInMediaStore() {
		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, "TAG PHOTO");
		values.put(MediaStore.Images.Media.DESCRIPTION, "ALSTOM");
		return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
	}
	
	public static Uri getCurrentImageUri(){
		
		return currentImageUri;
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		
		switch (resultCode) {
		case  Activity.RESULT_OK:
			
			if(requestCode == TaskDetailFragment.CAPTURE_CODE){
				
				System.out.println("haahahahahahahhaha");
				currentImageUri = TaskDetailFragment.getCurrentImageUri();
				String path = getRealPathFromURI(currentImageUri);
				taskListManager.notifyDisplayPhotoChange(path,task);
			}
			
			break;

		default:
			break;
		}
	}

	 public String getRealPathFromURI(Uri contentUri) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String path = cursor.getString(column_index);
			cursor.close();
			return path;
	 }

}
