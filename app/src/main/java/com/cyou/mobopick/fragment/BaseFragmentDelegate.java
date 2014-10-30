package com.cyou.mobopick.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.AppDetailActivity;
import com.cyou.mobopick.app.BaseActivity;
import com.cyou.mobopick.app.BaseActivityFeature;
import com.cyou.mobopick.app.BlankActivity;
import com.cyou.mobopick.app.Crossfader;
import com.cyou.mobopick.app.EntryActivity;
import com.cyou.mobopick.app.FragmentLifecycleCabllbacks;
import com.cyou.mobopick.app.MainActivity;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.MyGestureHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * This delegate is to avoid redundant code;
 *
 * @author fei.cheng
 */
public class BaseFragmentDelegate implements BaseFragmentFeature, FragmentLifecycleCabllbacks {

    private BaseFragment fragment;
    Bundle bundle = new Bundle();
    private BaseActivity activity;
    private Crossfader crossfader;
    private boolean destroyed;
    private static final List<String> flipToBackMask;


    static {
        flipToBackMask = new ArrayList<String>();
        flipToBackMask.add(BlankActivity.class.getName());
        flipToBackMask.add(MainActivity.class.getName());
        flipToBackMask.add(EntryActivity.class.getName());
        flipToBackMask.add(AppDetailActivity.class.getName());
    }
    public BaseFragmentDelegate(BaseFragment fragment) {
        this.fragment = fragment;
        this.activity = (BaseActivity) fragment.getActivity();
        this.crossfader = new Crossfader(this);
    }

    @Override
    public View getContentView() {
        return ((BaseFragmentFeature) fragment).getContentView();
    }

    @Override
    public View getProgressView() {
        return ((BaseFragmentFeature) fragment).getProgressView();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ButterKnife.inject(fragment, view);
        if (getContentView() != null) {
            getContentView().setVisibility(View.GONE);
        }
        if (savedInstanceState != null) {
            bundle = savedInstanceState.getBundle(Constant.KEY_RESULT);
            bundle = bundle == null ? new Bundle() : bundle;
        }
        if (fragment != null && !flipToBackMask.contains(activity.getComponentName().getClassName())) {
            View v = fragment.getView();
            if (v != null) {
                MyGestureHelper.newInstance(fragment, v);
            }
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        Ln.d("onActivivtyCreated");
    }

    @Override
    public void onAttach(Activity activity) {
//        Ln.d("onAttach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return null;
    }

    @Override
    public void onDestroyView() {
//        Ln.d("onDestroyView");
        ButterKnife.reset(fragment);
    }

    @Override
    public void onDetach() {
//        Ln.d("onDetach");
    }

    @Override
    public void onStart() {
//        requestQueue.rouse(this);
    }

    @Override
    public void onStop() {
//        Ln.d("fragment stoped,rm the listener");
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        //	mRequestManager = WelifeRequestManager.getInstance(WelifeApplication.getInstance());
        //	mRequestSets = Lists.newArrayList();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (bundle != null) {
//            outState.putBundle(Constants.KEY_RESULT, bundle);
        }
    }

    @Override
    public Bundle getBundle() {
        return bundle;
    }

    @Override
    public void setShowProgress(boolean show) {
        crossfader.setShowProgress(show);
    }

    @Override
    public void setShowProgress(View showView, View hideView) {
        crossfader.setShowProgress(showView, hideView);
    }


    @Override
    public void onDestory() {
        this.destroyed = true;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }


    @Override
    public BaseActivityFeature getActivityFeature() {
        return (BaseActivityFeature) activity;
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.content_frame;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        final int GESTURE_MOVE_X = 200;
        if (e2 == null || e1 == null) {
            return false;
        }
        if (e2.getX() - e1.getX() > GESTURE_MOVE_X && Math.abs(e2.getY() - e1.getY()) < GESTURE_MOVE_X) {
            if (!flipToBackMask.contains(activity.getComponentName().getClassName())) {
                activity.finish();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasDestroyed() {
        return destroyed;
    }

}
