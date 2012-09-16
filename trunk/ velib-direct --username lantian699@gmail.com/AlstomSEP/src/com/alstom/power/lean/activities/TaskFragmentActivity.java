package com.alstom.power.lean.activities;

import java.util.ArrayList;
import com.alstom.power.lean.fragments.AssistanceRequestFragment;
import com.alstom.power.lean.fragments.DetailSectionFragment;
import com.alstom.power.lean.fragments.TreatmentSectionFragment;
import com.alstom.power.lean.managers.TaskListManager;
import com.alstom.power.lean.model3d.Model3DTurbineActivity;
import com.alstom.power.lean.models.ModelObject;
import com.alstom.power.lean.pdfviewer.PdfViewerActivity;
import com.alstom.power.lean.views.TaskListCellView;
import com.alstom.power.lean.views.TreatmentFlashageCellView;
import com.alstom.power.lean.R;
import com.android.opgl.test.Run;



import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TaskFragmentActivity extends FragmentActivity implements ActionBar.TabListener {

	private SectionsPagerAdapter mSectionsPagerAdapter;
	private ViewPager mViewPager;
	private TaskListManager taskListManager;
	private static int pos_terminate = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_fragment);
       
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);    
            }
        });
        
     
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_fragment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	// TODO Auto-generated method stub
    	
    	switch (item.getItemId()) {
		case R.id.menu_pdf:
			Uri uri = Uri.parse("file:///sdcard/Download/2140ESI_8P_Post_BTS-DUT_BD.pdf");
	        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	        intent.setClass(this, PdfViewerActivity.class);
	        startActivity(intent);
			
			break;
			
		case R.id.menu_3d:
			
			Intent in = new Intent();
			in.setClass(this, Model3DTurbineActivity.class);
			startActivity(in);
			
			break;

		default:
			break;
		}
    	
    	return super.onOptionsItemSelected(item);
    }
   
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	
    	ArrayList<Fragment> listFragment = new ArrayList<Fragment>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            
            taskListManager = new TaskListManager();         
            listFragment.clear();
            listFragment.add(new DetailSectionFragment());
            listFragment.add(new AssistanceRequestFragment());
            listFragment.add(new TreatmentSectionFragment(taskListManager));
        }
        
        
        
       @Override
        public Fragment getItem(int position) {
    	   
    	   return listFragment.get(position);
      }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.title_detail).toUpperCase();
                case 1: return getString(R.string.title_DA).toUpperCase();
                case 2: return getString(R.string.title_treatment).toUpperCase();
            }
            return null;
        }

		
    }




	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
		if (mViewPager != null) {
			mViewPager.setCurrentItem(tab.getPosition());
		}
		
		
		
	}

	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	
	 @Override
		public void onActivityResult(int requestCode, int resultCode, Intent data) {
			// TODO Auto-generated method stub
			super.onActivityResult(requestCode, resultCode, data);
			
			switch (requestCode) {
			case TreatmentFlashageCellView.REQUEST_CODE_FLASH:
				System.out.println("REQUEST CODE FLASH");
				
				break;
				
			case TreatmentFlashageCellView.REQUEST_CODE_PHOTO:
				System.out.println("REQUEST CODE PHOTO");
				break;
				
			case TaskListCellView.REQUEST_CODE_STEP_LIST:
				
				taskListManager.notifyChange();

			default:
				break;
			}
			
			switch (resultCode) {
			case StepActivity.RESULT_CODE_TERMINATE:
				
				System.out.println("RESULT CODE TERMINATE");
				taskListManager.notifyChange();
				pos_terminate++;
				
				break;

			default:
				break;
			}
			
		}
   
	 public static int getPosTerminate(){
		 return pos_terminate;
	 }
		
	
}
