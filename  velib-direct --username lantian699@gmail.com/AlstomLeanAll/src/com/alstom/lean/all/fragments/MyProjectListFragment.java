package com.alstom.lean.all.fragments;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.alstom.lean.all.R;
import com.alstom.lean.all.ActivityListFragment.Callbacks;
import com.alstom.lean.all.activities.MyProjectModeTabletActivity;
import com.alstom.lean.all.adapters.MyProjectAdapter;
import com.alstom.lean.all.dummy.DummyContent;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.DatabaseHelper;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Project;

public class MyProjectListFragment extends Fragment implements OnItemClickListener {

	public static final String NAME_BUNDLE_LIST_FACTORY = "listFactory";
	private ListView list_myproject;
	private MyProjectAdapter adapterMyProject;
	private static ArrayList<Factory> listFactory;
	private int mActivatedPosition = ListView.INVALID_POSITION;
	
//	private Callbacks mCallbacks = activityCallbacks;
	private Project project;
	private DatabaseHelper dataHelper;

   /* public interface Callbacks {

        public void onItemSelected(int position);
    }
    
    private static Callbacks activityCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(int position) {
        }
    };
*/
    public MyProjectListFragment(Project project) { 	
    	
    	this.project = project;
    	this.dataHelper = DatabaseHelper.getInstance(getActivity());
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
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
    	// TODO Auto-generated method stub
  //  	mCallbacks.onItemSelected(position);
	   
	   FragmentManager supportManager = getActivity().getSupportFragmentManager();

	   switch (position) {
	case 1:
		DetailSectionFragment fragment = new DetailSectionFragment();
        
        supportManager.beginTransaction()
                .replace(R.id.activity_detail_container_1, fragment)
                .commit();
        
        
        AssistanceRequestFragment fragmentDA = new AssistanceRequestFragment();
        supportManager.beginTransaction()
        .replace(R.id.activity_detail_container_2, fragmentDA)
        .commit();
		
		
		break;
		
	case 2:
		
		TaskListFragment taskFragment = new TaskListFragment(project, dataHelper);
        supportManager.beginTransaction().replace(R.id.activity_detail_container_1, taskFragment).commit();
        
        
        Fragment removeFragment = supportManager.findFragmentById(R.id.activity_detail_container_2);
        if(removeFragment != null)
		supportManager.beginTransaction().remove(removeFragment).commit();
		
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


}