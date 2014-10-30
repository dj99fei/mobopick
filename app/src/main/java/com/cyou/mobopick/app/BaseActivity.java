
package com.cyou.mobopick.app;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import com.baidu.mobstat.StatService;
import com.cyou.mobopick.domain.Version;
import com.cyou.mobopick.fragment.UpdateDialogFragment;
import com.google.analytics.tracking.android.EasyTracker;

import butterknife.ButterKnife;

/**
 * Created by chengfei on 14-9-22.
 */
public class BaseActivity extends ActionBarActivity implements BaseActivityFeature,
        OnClickListener {


    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        ButterKnife.inject(this);
        if (Version.needToNotify()) {
            UpdateDialogFragment fragment = UpdateDialogFragment.newInstance();
            fragment.show(getSupportFragmentManager(), null);
        }

    }


    @Override
    public void setShowProgress(boolean show) {
        
    }

    @Override
    public void setShowProgress(View showView, View hideView) {
        
    }

    @Override
    public void onClick(View v) {
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStop(this);
    }

    @Override
    public View getContentView() {
        return null;
    }

    @Override
    public View getProgressView() {
        return null;
    }

    @Override
    public int getFragmentContainerId() {
        return 0;
    }

    @Override
    public boolean hasDestroyed() {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
