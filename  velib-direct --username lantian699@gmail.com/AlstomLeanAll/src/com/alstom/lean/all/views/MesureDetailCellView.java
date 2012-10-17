package com.alstom.lean.all.views;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alstom.lean.all.R;
import com.alstom.lean.all.fragments.MesureDocumentFragment;
import com.alstom.lean.all.managers.ChangeObserver;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Mesurement;
import com.alstom.lean.all.models.ModelObject;
import com.alstom.lean.all.models.Task;
import com.j256.ormlite.dao.Dao;
import com.picture.drawing.ui.navigation.activity.PictureDrawingActivity;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MesureDetailCellView extends LinearLayout{
	
	public static final String MESURE_TYPE_NUMBER = "MN";
	public static final String MESURE_TYPE_ATTACHMENT = "MA";
	private TextView description;
	private TextView unit;
	private TextView rule;
	private EditText edit_value;
	private TextView timeStamps;
	private String value;
	private Mesurement mesure;
	private DatabaseHelper dataHelper;
	private Dao<Mesurement, ?> mesureDao;
	private ImageView btn_mesure_photo;
	private FragmentActivity context;
	private Button btn_mesure_info;

	public MesureDetailCellView(final Context context, TaskListManager manager, DatabaseHelper helper) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    inflater.inflate(R.layout.mesure_detail_cell_view, this);
	    this.dataHelper = helper;
	    this.context = (FragmentActivity) context;
		
		
	    description = (TextView)findViewById(R.id.tx_md_description);
	    timeStamps = (TextView)findViewById(R.id.tx_md_time);
	    unit = (TextView)findViewById(R.id.tx_md_unit);
	    rule = (TextView)findViewById(R.id.tx_md_rule);
	    edit_value = (EditText)findViewById(R.id.edit_md_value);
	    btn_mesure_photo = (ImageView)findViewById(R.id.btn_mesure_photo);
	    btn_mesure_info = (Button)findViewById(R.id.btn_mesure_info);
	    btn_mesure_photo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent in = new Intent();
				in.setClass(context, PictureDrawingActivity.class);
				in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(in);
			}
		});

	    manager.registerAddMesureChangeObserver(new ChangeObserver() {
			
			@Override
			public void onChange(String res) {


				value = edit_value.getText().toString();
				
				if(value != null)
					mesure.setValue(value);
				mesure.setTimeStamp(timeStamps.getText().toString());
				
				try {
					mesureDao = dataHelper.getDao(Mesurement.class);
					mesureDao.update(mesure);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}		
			@Override
			public void onChange() {
			}

			@Override
			public void onChange(String res, ModelObject model) {
	
			}
		});
	    
	}
	
	
	public void setData(final Mesurement mesure, final int position, final Task task){
		
		this.mesure = mesure;
		
		final double low = Double.parseDouble(mesure.getLow());
		final double high = Double.parseDouble(mesure.getHigh());
		
		 edit_value.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {

					if(!s.toString().equals("")){
						double v = Double.parseDouble(s.toString());
						if(v < low || v> high){
							edit_value.setTextColor(Color.RED);
						}else if(v>low && v< high){
							edit_value.setTextColor(Color.BLACK);
						}
							
					}
					
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void afterTextChanged(Editable arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		
		
		
		
		description.setText(mesure.getDescription());
		unit.setText(mesure.getUnit());
		rule.setText("( " + mesure.getLow() + " < "+mesure.getHigh()+ " )");
		edit_value.setText(mesure.getValue());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd   hh:mm:ss");     
		Date curDate = new Date(System.currentTimeMillis());
		String str = formatter.format(curDate); 
		timeStamps.setText(str);
		
		if(mesure.getType().equals(MESURE_TYPE_NUMBER)){
			btn_mesure_photo.setVisibility(View.GONE);
			btn_mesure_info.setVisibility(View.VISIBLE);
		}else if(mesure.getType().equals(MESURE_TYPE_ATTACHMENT)){
			btn_mesure_photo.setVisibility(View.VISIBLE);
			btn_mesure_info.setVisibility(View.GONE);
		}
		
		
		btn_mesure_info.setOnClickListener(new OnClickListener() {
			
			private ArrayList<String> listFileName;
			private ArrayAdapter<String> adapterString;

			@Override
			public void onClick(View v) {
				
				final String root = Environment.getExternalStorageDirectory()+"/Alstom/Project "+task.getParentProject()+"/mesure "+mesure.getDescription();
				File file = new File(root);
				if(!file.exists())
					file.mkdirs();
				System.out.println("list name = " + file.getPath()+ "  " + file.listFiles());
					listFileName = new ArrayList<String>();
					for (File childFile : file.listFiles()) {
						listFileName.add(childFile.getName());
					}
					
					adapterString = new ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1,listFileName);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("DOCUMENTS");

					builder.setAdapter(adapterString, new DialogInterface.OnClickListener() {
					    public void onClick(DialogInterface dialog, int item) {
					    	
					        String fileName = adapterString.getItem(item);
					        Uri uri = Uri.parse("file://"+root+"/"+fileName);
					        System.out.println(uri.getPath());
					        if(fileName.endsWith("png" )|| fileName.endsWith("jpg")){
					        	
					        	Fragment fragment_document = new MesureDocumentFragment(uri, "image");
					        	context.getSupportFragmentManager().beginTransaction().replace(R.id.activity_mesure_detail_container_1, fragment_document).commit();
					        }else if(fileName.endsWith("pdf")){
					        	
					        	Fragment fragment_document = new MesureDocumentFragment(uri, "pdf");
					        	context.getSupportFragmentManager().beginTransaction().replace(R.id.activity_mesure_detail_container_1, fragment_document).commit();
					        }
					        
					        else{
					        	
					        	Toast.makeText(context, "File type non recognized !", Toast.LENGTH_SHORT).show();
					        }
					        
					        
					        
					    }
					});
					AlertDialog alert = builder.create();
					
					if(adapterString.getCount() == 0){
						Toast.makeText(context, "There is none of related documents", Toast.LENGTH_SHORT).show();
					}
					else	
					alert.show();
				
			}
		});
		
		
		
	}

}
