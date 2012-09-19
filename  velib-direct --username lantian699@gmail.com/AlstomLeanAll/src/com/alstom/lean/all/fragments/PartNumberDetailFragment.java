package com.alstom.lean.all.fragments;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.ImageDisplayActivity;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.activities.PartNumTabletActivity;
import com.alstom.lean.all.activities.TaskFragmentActivity;
import com.alstom.lean.all.fragments.MyProjectListFragment.Callbacks;
import com.alstom.lean.all.model3d.Model3DTurbineActivity;
import com.alstom.lean.all.pdfviewer.PdfViewerActivity;

public class PartNumberDetailFragment extends  ListFragment{
	
	public static final String RESOURCE_ID = "resourceID";

	private static String PDF_GT26_PLAN_2D = "gt26_plan_2d.pdf";
	
	private Context context;
	private String partNum;
	private ArrayAdapter<String> listPartAdapter;
	private Callbacks mCallbacks;
	private int mActivatedPosition;
	
	public PartNumberDetailFragment(){
		
	}

	public PartNumberDetailFragment(Context context, String partNum){
		
		this.context = context;
		this.partNum = partNum;
		
	}
	
	/* public interface Callbacks {

	        public void onItemSelected(int position);
	    }
	    
	 private static Callbacks activityCallbacks = new Callbacks() {
	        @Override
	        public void onItemSelected(int position) {
	        }
	    };*/
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       
		
		listPartAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, getData());
		
		setListAdapter(listPartAdapter);
        
    }
	
/*	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks. " +activity);
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = activityCallbacks;
    }
    
  
    
    
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
        	getListView().setItemChecked(mActivatedPosition, false);
        } else {
        	getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
	*/
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
//		mCallbacks.onItemSelected(position);
		switch (position) {
		
			
		case 2:
	
			if(context instanceof TaskFragmentActivity || context instanceof MyProjectModeTabletActivity){
				
				boolean save = copyFile(PDF_GT26_PLAN_2D, Environment.getExternalStorageDirectory().getPath()+"/"+PDF_GT26_PLAN_2D);
				Uri uri = Uri.parse("file:///mnt/sdcard/"+PDF_GT26_PLAN_2D);
//				Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gt26_plan_2d);
				if(save && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

					 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
					 intent.setClass(context, PdfViewerActivity.class);
					 startActivity(intent);
			 
				}
		
			}
			break;
			
		case 3:
			
			Intent intent = new Intent();
			intent.setClass(context, ImageDisplayActivity.class);
			intent.putExtra(RESOURCE_ID, R.drawable.gt26_vue_en_coupe);
			startActivity(intent);
			break;
			
		case 4:
			
			Intent intent_4 = new Intent();
			intent_4.setClass(context, ImageDisplayActivity.class);
			intent_4.putExtra(RESOURCE_ID, R.drawable.gt26_model);
			startActivity(intent_4);
			
			break;
			
		case 5:
			
	//		if(context instanceof TaskFragmentActivity){
			Intent in = new Intent();
			in.setClass(context, Model3DTurbineActivity.class);
			startActivity(in);
	/*		}else if(context instanceof MyProjectModeTabletActivity){
				Intent in = new Intent();
				
				in.setClass(context, PartNumTabletActivity.class);
				startActivity(in);
				
			}*/
			
			
			break;

		default:
			break;
		}
		
		
	}
	
	private ArrayList<String> getData(){
		
		ArrayList<String> listPartNumber = new ArrayList<String>();
		listPartNumber.add("Description : " + partNum);
		listPartNumber.add("Doc");
		listPartNumber.add("2D Plan");
		listPartNumber.add("GT26 Sectional View");
		listPartNumber.add("GT26 model view");
		listPartNumber.add("3D Model");
		listPartNumber.add("Start time : 2012/09/17 12:00");
		listPartNumber.add("End time : 2012/09/19 13:00");
		
		return listPartNumber;
		
	}
	
	

	public boolean copyFile(String from, String to) {

	    try {
	        int bytesum = 0;
	        int byteread = 0;
	       File oldfile = new File(to);
	   
	        if(!oldfile.exists()){
	            InputStream inStream = getResources().getAssets().open(from);
	            OutputStream fs = new BufferedOutputStream(new FileOutputStream(to));
	            byte[] buffer = new byte[8192];
	            while ((byteread = inStream.read(buffer)) != -1) {
	                bytesum += byteread;
	                fs.write(buffer, 0, byteread);
	            }
	            inStream.close();
	            fs.close();
	        }
	        return true;
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return false;
	    }
	}

   

}
