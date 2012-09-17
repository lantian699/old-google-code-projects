package com.alstom.lean.all.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.alstom.lean.all.ActivityDetailActivity;
import com.alstom.lean.all.ActivityDetailFragment;
import com.alstom.lean.all.ActivityListFragment;
import com.alstom.lean.all.R;
import com.alstom.lean.all.fragments.DetailSectionFragment;
import com.alstom.lean.all.fragments.MyProjectListFragment;


public class MyProjectModeTabletActivity extends FragmentActivity
        implements MyProjectListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        if (findViewById(R.id.activity_detail_container_1) != null) {
            mTwoPane = true;
            ((MyProjectListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.activity_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(int position) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
         //   arguments.putString(ActivityDetailFragment.ARG_ITEM_ID, id);
        //    ActivityDetailFragment fragment = new ActivityDetailFragment();
       //     fragment.setArguments(arguments);
            
            DetailSectionFragment fragment = new DetailSectionFragment();
            
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_detail_container_1, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ActivityDetailActivity.class);
           
            startActivity(detailIntent);
        }
    }
}
