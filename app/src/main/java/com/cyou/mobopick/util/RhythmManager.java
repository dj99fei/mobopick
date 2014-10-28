package com.cyou.mobopick.util;

import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;

import com.cyou.mobopick.MyApplication;

import org.lucasr.twowayview.widget.ListLayoutManager;

/**
 * Created by chengfei on 14-10-13.
 */
public class RhythmManager {

    private ListLayoutManager layoutManager;
    private RecyclerView recyclerView;
    public static final int ITEM_COUNT = 5;
    private static final String TAG = RhythmManager.class.getSimpleName();


    public RhythmManager(ListLayoutManager layoutManager, RecyclerView recyclerView) {
        this.layoutManager = layoutManager;
        this.recyclerView = recyclerView;
    }

    public void select(final int position, final float base) {
//        adapter.getItemWidth()
//        for (int i = 0 ;i < ITEM_COUNT + 1; i ++) {
//            int firstVisiblePosition = layoutManager.getFirstVisiblePosition();
//            LogUtils.d(TAG, "position = %s", (i + firstVisiblePosition));
//            if (recyclerView.getChildAt(i + firstVisiblePosition) != null) {
//                AnimatorUtils.showUpAndDownBounce(recyclerView.getChildAt(i + firstVisiblePosition),
//                        Math.max(Math.abs(position - i - layoutManager.getFirstVisiblePosition()) * (int) (base/ 6.0f), 10), 180,0);
//            }
//        }


//        for (int i = layoutManager.getFirstVisiblePosition(); i < layoutManager.getFirstVisiblePosition() + ITEM_COUNT; i++) {
//            if (recyclerView.getChildAt(i) != null) {
//                AnimatorUtils.showUpAndDownBounce(recyclerView.getChildAt(i),
//                        Math.max(Math.abs(position - i - layoutManager.getFirstVisiblePosition()) * (int) (base/ 6.0f), 10), 180,0);
//            }
//        }
//        new Handler().postDelayed(new Runnable() {
//            public void run() {
//                for (int i = layoutManager.getFirstVisiblePosition(); i < layoutManager.getFirstVisiblePosition() + ITEM_COUNT; i++) {
//
//                    if (i != position) {
//                        final int other = i;
//                        LogUtils.d(TAG, "position = %s ", other);
//                        if (recyclerView.getChildAt(other) != null) {
//
//                            AnimatorUtils.showUpAndDownBounce(recyclerView.getChildAt(other), (int) (base * 0.8f) , 350, 0);
//                        }
//                    }
//                }
//            }
//        }, 200L);
//        if (recyclerView.getChildAt(position) != null) {
//            AnimatorUtils.showUpAndDownBounce(recyclerView.getChildAt(position), 10, 350, 0);
//        }
//        vibrate(20l);
    }
    private void vibrate(long paramLong)
    {
        ((Vibrator) MyApplication.getInstance().getSystemService(Context.VIBRATOR_SERVICE)).vibrate(new long[]{0L, paramLong}, -1);
    }
}
