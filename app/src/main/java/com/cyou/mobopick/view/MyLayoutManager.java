package com.cyou.mobopick.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import org.lucasr.twowayview.widget.ListLayoutManager;

import java.lang.reflect.Field;

/**
 * Created by chengfei on 14-10-23.
 */
public class MyLayoutManager extends ListLayoutManager {
    private Context context;
    public MyLayoutManager(Context context, Orientation orientation) {
        super(context, orientation);
        this.context = context;
    }

    @Override
    public void startSmoothScroll(RecyclerView.SmoothScroller smoothScroller) {
//        LinearSmoothScroller scroller = (LinearSmoothScroller) smoothScroller;

        try {
            Field field = smoothScroller.getClass().getSuperclass().getSuperclass().getDeclaredField("mRecyclingAction");
            field.setAccessible(true);
            android.support.v7.widget.RecyclerView.SmoothScroller.Action action = (RecyclerView.SmoothScroller.Action) field.get(smoothScroller);
            if (action.getDuration() > 0) {
                action.setDuration(400);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        super.startSmoothScroll(smoothScroller);
    }
}
