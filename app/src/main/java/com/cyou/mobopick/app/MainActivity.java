package com.cyou.mobopick.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.cyou.mobopick.R;
import com.cyou.mobopick.fragment.AppTimelineFragment;
import com.cyou.mobopick.fragment.DownloadListFragment;
import com.cyou.mobopick.fragment.MyGroupFragment;
import com.cyou.mobopick.fragment.NavigationDrawerFragment;

public class MainActivity extends CommonActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks, AppTimelineFragment.OnFragmentInteractionListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setActionBar();
        setContentView(R.layout.activity_main);
        mNavigationDrawerFragment = new NavigationDrawerFragment();
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
//
//        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

//        Request request = new CheckUpdateRequest(this);
//        MyVolley.getRequestQueue().add()
    }

//    @Override
//    int getContentViewId() {
//        return R.layout.activity_main;
//    }

    @Override
    public Fragment getFragment() {
        return AppTimelineFragment.newInstance();
    }

//    private void setActionBar() {
//        LogUtils.d(TAG, "setActionBar");
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        actionBar.setCustomView(R.layout.actionbar_time_line);
//    }

    @Override
    public void onNavigationDrawerItemSelected(int id) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.item_drawer_downloads:
//                startActivity(new Intent(this, DownloadListActivity.class));
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, DownloadListFragment.newInstance())
                        .commit();
                break;
            case R.id.item_drawer_dailypick:
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, AppTimelineFragment.newInstance())
                        .commit();
                break;
            case R.id.item_drawer_group:
                fragmentManager.beginTransaction().replace(R.id.content_frame, MyGroupFragment.newInstance()).commit();
                break;
        }

    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_downloads) {
            startActivity(new Intent(this, DownloadListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

