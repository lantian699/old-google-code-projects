package com.alstom.lean.all;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class ActivityListActivity extends FragmentActivity
        implements ActivityListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_list);

        if (findViewById(R.id.activity_detail_container) != null) {
            mTwoPane = true;
            ((ActivityListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.activity_list))
                    .setActivateOnItemClick(true);
        }
    }

    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ActivityDetailFragment.ARG_ITEM_ID, id);
            ActivityDetailFragment fragment = new ActivityDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ActivityDetailActivity.class);
            detailIntent.putExtra(ActivityDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
}
