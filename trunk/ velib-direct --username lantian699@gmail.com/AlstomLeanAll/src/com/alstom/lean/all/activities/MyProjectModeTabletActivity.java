package com.alstom.lean.all.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alstom.lean.all.R;
import com.alstom.lean.all.flashage.CaptureFlashageActivity;
import com.alstom.lean.all.fragments.AssistanceRequestFragment;
import com.alstom.lean.all.fragments.DetailSectionFragment;
import com.alstom.lean.all.fragments.MyProjectListFragment;
import com.alstom.lean.all.managers.TaskListManager;
import com.alstom.lean.all.models.Factory;
import com.alstom.lean.all.models.Project;


public class MyProjectModeTabletActivity extends FragmentActivity  {//implements MyProjectListFragment.Callbacks{//, PartNumberDetailFragment.Callbacks{

	
    private boolean mTwoPane;
	private Project project;
	private static TaskListManager taskListManager = new TaskListManager();;
    public static final String NAME_BUNDLE_LIST_FACTORY = "listFactory";	
    private static ArrayList<Factory> listFactory;
 
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        project = (Project) getIntent().getSerializableExtra(ProjectListActivity.INTENT_EXTRA_PROJECT_NAME);
        
        if (findViewById(R.id.activity_detail_container_0) != null) {
            mTwoPane = true;
           /* ((MyProjectListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.activity_list))
                    .setActivateOnItemClick(true);*/
            MyProjectListFragment fragment = new MyProjectListFragment(project, taskListManager);
            
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_detail_container_0, fragment)
            .commit();
            
        }
        
        simulationFactory();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	// TODO Auto-generated method stub
    	
    	getMenuInflater().inflate(R.menu.actionbar_menu, menu);
    	return true;
    }

    public static TaskListManager getTaskListManager(){
    	
    	return taskListManager;
    }
    
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {

		
		switch (item.getItemId()) {
		case R.id.menu_map:
			
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LocationUsineActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			Bundle bundle = new Bundle();
			bundle.putSerializable(NAME_BUNDLE_LIST_FACTORY, listFactory);	
			intent.putExtras(bundle);
			
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
		
		
		return super.onOptionsItemSelected(item);
	}
    
    

    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	
		switch (resultCode) {
		
			
		case CaptureFlashageActivity.RESULT_CODE_CAPTURE_ACTIVITY:

			String res = data.getStringExtra(CaptureFlashageActivity.BAR_CODE_RESULT);
			System.out.println("RESULT_CODE_CAPTURE_ACTIVITY " +res);
			taskListManager.notifyBarcodeChange(res);
			
			
			
			break;

		default:
			break;
		}
		
	}

    
    /*@Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
         //   Bundle arguments = new Bundle();
         //   arguments.putString(ActivityDetailFragment.ARG_ITEM_ID, id);
        //    ActivityDetailFragment fragment = new ActivityDetailFragment();
       //     fragment.setArguments(arguments);
            
            DetailSectionFragment fragment = new DetailSectionFragment();
            
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_detail_container_1, fragment)
                    .commit();
            
            
            AssistanceRequestFragment fragmentDA = new AssistanceRequestFragment();
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_detail_container_2, fragmentDA)
            .commit();
            
            
            taskListManager = new TaskListManager();;
			TreatmentSectionFragment fragmentTreat = new TreatmentSectionFragment(taskListManager );
            
            getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_detail_container_3, fragmentTreat)
            .commit();
            
            
           

        } else {
            Intent detailIntent = new Intent(this, TaskFragmentActivity.class);
           
            startActivity(detailIntent);
        }
    }*/
    
    
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
