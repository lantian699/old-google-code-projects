package com.alstom.lean.all.fragments;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.crypto.spec.PSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alstom.lean.all.R;
import com.alstom.lean.all.activities.ImageDisplayActivity;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.adapters.MyProjectAdapter;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.model3d.Model3DTurbineActivity;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Project;
import com.alstom.lean.all.pdfviewer.PdfViewerActivity;
import com.alstom.lean.all.views.MyProjectCellView;
import com.alstom.lean.all.views.TaskListCellView;

public class MyProjectListFragment extends Fragment implements OnItemClickListener {

	public static final String NAME_BUNDLE_LIST_FACTORY = "listFactory";
	private ListView list_myproject;
	private MyProjectAdapter adapterMyProject;
	private static ArrayList<Factory> listFactory;
	private int mActivatedPosition = ListView.INVALID_POSITION;
	
//	private Callbacks mCallbacks = activityCallbacks;
	private Project project;
	private DatabaseHelper dataHelper;
	private TaskListManager taskListManager;
	public static String PDF_GT26_PLAN_2D = "gt26_plan_2d.pdf";
	public static final String RESOURCE_ID = "resourceID";
   /* public interface Callbacks {

        public void onItemSelected(int position);
    }
    
    private static Callbacks activityCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int position) {
        }
    };
*/
    public MyProjectListFragment(Project project, TaskListManager manager) { 	
    	
    	this.project = project;
    	this.dataHelper = DatabaseHelper.getInstance(getActivity());
    	this.taskListManager = manager;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		listFactory = simulationFactory();
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_myproject, container, false);
        list_myproject = (ListView) rootView.findViewById(R.id.list_myproject);
        list_myproject.setOnItemClickListener(this);
        adapterMyProject = new MyProjectAdapter(getActivity(),project);
        list_myproject.setAdapter(adapterMyProject);
        return rootView;
    }
    
   /* private ArrayList<Project> simulationOfProjects() {

		listProjects.clear();
		
		for(int i=0; i<20; i++){
			
			Project project = new Project();
			project.setName(getString(R.string.project)+i);
			project.setType("Type "+i);
			
			listProjects.add(project);
			
		}
		
		return listProjects;
		
	}*/
    
   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = activityCallbacks;
    }
    */
    
   @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
    	// TODO Auto-generated method stub
  //  	mCallbacks.onItemSelected(position);
	   
	   FragmentManager supportManager = getActivity().getSupportFragmentManager();

	   Object tag = adapterView.getTag();
	   if(tag != null){

		   ((MyProjectCellView)tag).setBackgroundColor(Color.WHITE);
	   }
	   view.setBackgroundResource(R.drawable.list_activated_holo);
	   adapterView.setTag(view);
	   
	   switch (position) {
	case 1:
		
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_1)).setVisibility(View.VISIBLE);
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_2)).setVisibility(View.VISIBLE);
		((LinearLayout) getActivity().findViewById(R.id.activity_detail_container_hide)).setVisibility(View.GONE);
		
		DetailSectionFragment fragment = new DetailSectionFragment(project, dataHelper);
        
        supportManager.beginTransaction()
                .replace(R.id.activity_detail_container_1, fragment)
                .commit();
        
        
        AssistanceRequestFragment fragmentDA = new AssistanceRequestFragment();
        supportManager.beginTransaction()
        .replace(R.id.activity_detail_container_2, fragmentDA)
        .commit();
		
		
		break;
		
	case 2:
		
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_1)).setVisibility(View.VISIBLE);
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_2)).setVisibility(View.VISIBLE);
		((LinearLayout) getActivity().findViewById(R.id.activity_detail_container_hide)).setVisibility(View.GONE);
		
		TaskListFragment taskFragment = new TaskListFragment(project, dataHelper, taskListManager);
        supportManager.beginTransaction().replace(R.id.activity_detail_container_1, taskFragment).commit();
        
        
        Fragment removeFragment = supportManager.findFragmentById(R.id.activity_detail_container_2);
        if(removeFragment != null)
		supportManager.beginTransaction().remove(removeFragment).commit();
		
		break;
		
	case 3 :
		
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_1)).setVisibility(View.GONE);
		((FrameLayout) getActivity().findViewById(R.id.activity_detail_container_2)).setVisibility(View.GONE);
		((LinearLayout) getActivity().findViewById(R.id.activity_detail_container_hide)).setVisibility(View.VISIBLE);
		
		ComponentListFragment cpFragment = new ComponentListFragment(project, dataHelper);
        supportManager.beginTransaction().replace(R.id.activity_detail_sub_container_1, cpFragment).commit();
		
		break;
		
	case 4:
		
		final CharSequence[] items = {"2D Plan", "GT26 Sectional View", "GT26 model view", "3D Model"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("DOCUMENTS");
		builder.setItems(items, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        Toast.makeText(getActivity(), items[item], Toast.LENGTH_SHORT).show();
		        
		        switch (item) {
				case 0:
					
					boolean save = copyFile(PDF_GT26_PLAN_2D, Environment.getExternalStorageDirectory().getPath()+"/"+PDF_GT26_PLAN_2D, getResources());
					Uri uri = Uri.parse("file:///mnt/sdcard/"+PDF_GT26_PLAN_2D);
//					Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.gt26_plan_2d);
					if(save && Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){

						 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
						 intent.setClass(getActivity(), PdfViewerActivity.class);
						 startActivity(intent);
				 
					}
					
					break;
					
				case 1:
					
					Intent intent = new Intent();
					intent.setClass(getActivity(), ImageDisplayActivity.class);
					intent.putExtra(RESOURCE_ID, R.drawable.gt26_vue_en_coupe);
					startActivity(intent);
					break;

				case 2:
					
					Intent intent_model = new Intent();
					intent_model.setClass(getActivity(), ImageDisplayActivity.class);
					intent_model.putExtra(RESOURCE_ID, R.drawable.gt26_model);
					startActivity(intent_model);
					
					break;
					
				case 3:
					
					Intent in = new Intent();
					in.setClass(getActivity(), Model3DTurbineActivity.class);
					startActivity(in);
					
					break;
					
				default:
					break;
				}
		        
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
		
		break;
		

	default:
		break;
	}
    	

    }
    
   
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        list_myproject.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
        	list_myproject.setItemChecked(mActivatedPosition, false);
        } else {
        	list_myproject.setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
    
    public static ArrayList<Factory>  simulationFactory(){
		
		listFactory = new ArrayList<Factory>();
		listFactory.clear();
		
		Factory factory1 = new Factory();
		factory1.setLatitude(48.893376);
		factory1.setLongitude(2.287474);
		factory1.setName("Alstom");
		factory1.setAddress("3 Avenue Andr¨¦ Malraux, 92300 Levallois-Perret");
		listFactory.add(factory1);
		
		Factory factory2 = new Factory();
		factory2.setLatitude(48.896352);
		factory2.setLongitude(2.273505);
		factory2.setName("Alstom Power Industries");
		factory2.setAddress("3 Avenue Andr¨¦ Malraux 92300 Levallois-Perret");
		listFactory.add(factory2);
		
		
		Factory factory3 = new Factory();
		factory3.setLatitude(48.719919);
		factory3.setLongitude(2.267003);
		factory3.setName("Alstom Power Systems");
		factory3.setAddress("12 Rue Jean Bart 91300 Massy");
		listFactory.add(factory3);
		
		
		Factory factory4 = new Factory();
		factory4.setLatitude(48.914265);
		factory4.setLongitude(2.332149);
		factory4.setName("Alstom ITC");
		factory4.setAddress("48 Rue Albert Dhalenne 93400 Saint-Ouen");
		listFactory.add(factory4);
		
		return listFactory;
	}

    public static boolean copyFile(String from, String to, Resources res) {

	    try {
	        int bytesum = 0;
	        int byteread = 0;
	       File oldfile = new File(to);
	   
	        if(!oldfile.exists()){
	            InputStream inStream = res.getAssets().open(from);
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