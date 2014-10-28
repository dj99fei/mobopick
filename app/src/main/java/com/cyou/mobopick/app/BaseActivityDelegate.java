package com.cyou.mobopick.app;

import android.os.Bundle;
import android.view.View;


/**
 * This delegate is to avoid redundant code;
 *
 * @author fei.cheng
 */
public class BaseActivityDelegate implements BaseActivityFeature, ActivityLifecycleCallbacks {

    protected BaseActivity activity;

    private Crossfader crossfader;
    private boolean destroyed;

    public BaseActivityDelegate(BaseActivity activity) {
        this.activity = activity;
        this.crossfader = new Crossfader(this);
    }

    @Override
    public void setShowProgress(final boolean show) {
        crossfader.setShowProgress(show);
    }

    @Override
    public void setShowProgress(final View showView, final View hideView) {
        crossfader.setShowProgress(showView, hideView);
    }

    @Override
    public View getContentView() {
        return activity.getContentView();
    }

    @Override
    public View getProgressView() {
        return activity.getProgressView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onDestory() {
        destroyed = true;
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public int getFragmentContainerId() {
        return activity.getFragmentContainerId();
    }

    @Override
    public boolean hasDestroyed() {
        return destroyed;
    }

}
