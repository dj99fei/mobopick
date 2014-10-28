
package com.cyou.mobopick.fragment;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ProgressBar;

import com.baidu.mobstat.StatService;
import com.cyou.mobopick.R;
import com.cyou.mobopick.app.BaseActivityFeature;

import butterknife.InjectView;
import butterknife.Optional;

/**
 * All your common fragment should extends this base fragment,and override the
 * relevant methods
 * 
 * @author fei.cheng
 */
public class BaseFragment extends Fragment implements BaseFragmentFeature, View.OnClickListener {

    @Optional
    @InjectView(R.id.cross_fader_loading)
    protected ProgressBar loadingSpinner;
    @Optional
    @InjectView(R.id.cross_fader_content)
    protected View content;
    protected Resources resources;
    private BaseFragmentDelegate fragmentDelegate;

    protected ActionBar bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentDelegate.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentDelegate.onViewCreated(view, savedInstanceState);
    }

    public void setShowProgress(final boolean show) {
        fragmentDelegate.setShowProgress(show);
    }

    public void setShowProgress(final View showView, final View hideView) {
        fragmentDelegate.setShowProgress(showView, hideView);
    }

    @Override
    public View getContentView() {
        return content;
    }

    @Override
    public View getProgressView() {
        return loadingSpinner;
    }

    @Override
    public void onStart() {
        super.onStart();
        fragmentDelegate.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        fragmentDelegate.onStop();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentDelegate.onActivityCreated(savedInstanceState);
        bar = ((ActionBarActivity) getActivity()).getSupportActionBar();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        resources = activity.getResources();
        fragmentDelegate = new BaseFragmentDelegate(this);
        fragmentDelegate.onAttach(activity);

    }

    @Override
    public void onResume() {
        super.onResume();
        StatService.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        StatService.onPause(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentDelegate.onDestory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentDelegate.onDestroyView();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentDelegate.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentDelegate.onSaveInstanceState(outState);
    }

    @Override
    public Bundle getBundle() {
        return fragmentDelegate.getBundle();
    }

    @Override
    public BaseActivityFeature getActivityFeature() {
        return fragmentDelegate.getActivityFeature();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return fragmentDelegate.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean hasDestroyed() {
        return fragmentDelegate.hasDestroyed();
    }

    @Override
    public void onClick(View v) {

    }
}
