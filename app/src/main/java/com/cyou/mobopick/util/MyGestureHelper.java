package com.cyou.mobopick.util;


import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.fragment.BaseFragment;


public class MyGestureHelper implements OnGestureListener, OnTouchListener {
    public GestureDetector mGestureDetector;
    private BaseFragment activity;

    public MyGestureHelper(BaseFragment activity, View v) {
	this.activity = activity;
	mGestureDetector = new GestureDetector(MyApplication.getInstance(), this);
	mGestureDetector.setIsLongpressEnabled(true);
	setTouchListener(((ViewGroup) ((ViewGroup) v).getChildAt(0)));
    }

    public MyGestureHelper(BaseFragment activity, View[] vlist) {
	this.activity = activity;
	mGestureDetector = new GestureDetector(MyApplication.getInstance(), this);
	mGestureDetector.setIsLongpressEnabled(true);
    }

    public static void newInstance(BaseFragment activity, View[] vlist) {
	new MyGestureHelper(activity, vlist);
    }

    public static void newInstance(BaseFragment activity, View v) {
	new MyGestureHelper(activity, v);
    }

    @Override
    public boolean onDown(MotionEvent e) {
	return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
	return activity.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
	return false;
    }

    private void setTouchListener(View v) {
	if (v instanceof ViewGroup) {
	    ViewGroup p = (ViewGroup) v;
	    p.setOnTouchListener(this);
	    for (int i = 0; i < p.getChildCount(); i++) {
		setTouchListener(((ViewGroup) v).getChildAt(i));
	    }
	} else {
	    v.setOnTouchListener(this);
	}

    }

    float mLastTouchX = 0;
    int mActivePointerId = 0;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
	boolean handled = mGestureDetector.onTouchEvent(event);
//	int action = event.getAction() & MotionEventCompat.ACTION_MASK;
//	switch (action) {
//	    case MotionEvent.ACTION_DOWN:
//		mLastTouchX = event.getX();
//		mActivePointerId = MotionEventCompat.getPointerId(event, 0);
//		break;
//	    case MotionEvent.ACTION_MOVE:
//		final float x = event.getX();
//		final float dx = x - mLastTouchX;
//		if (dx > 200) {
//		    return true;
//		}
//	}
	return handled;
    }

}