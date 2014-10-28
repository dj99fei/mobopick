package com.cyou.mobopick.view;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class RhythmLayout extends HorizontalScrollView
{
    public static final int ITEMS_DISPLAY_NUM = 7;
    public static final int ITEM_BOUNCE_DURATION = 350;
    public static final int ITEM_SWIPE_BOUNCE_DURATION = 180;
    public static final int ITEM_TOP_MIN_TRANSLATION_Y = 10;
    public static final int RHYTHM_SCROLLER_SCROLL_DURATION = 300;
    public static int mEdgeSizeForShiftRhythm = 48;
    public static int mItemInitTranslationHeight;
    private int currentMoveItemPosition = -1;
    private int lastDisplayItemPosition = -1;
    private Handler mHandler = new Handler();
    private LinearLayout mLinearLayout;
    private RhythmAdapter mRhythmAdapter;
//    private ShiftRhythmMonitorTimer mTimer;
    private int rhythmIntervalHeight;
    private IRhythmItemListener rhythmItemListener;
    private float rhythmItemWidth;
    private int screenWidth;
    private int scrollRhythmStartDelayTime = 0;

    public RhythmLayout(Context paramContext)
    {
      super(paramContext);
//      init();
    }

    public RhythmLayout(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramContext, paramAttributeSet);
//      init();
    }

    private List<View> getAllRhythmViews()
    {
      ArrayList localArrayList = new ArrayList();
      int i = this.mLinearLayout.getChildCount();
      for (int j = 0; j < i; j++)
        localArrayList.add(this.mLinearLayout.getChildAt(j));
      return localArrayList;
    }

    private DisplayMetrics getScreenDisplayMetrics()
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      ((Activity)getContext()).getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
      return localDisplayMetrics;
    }

//    private List<View> getVisibleRhythmViews()
//    {
//      ArrayList localArrayList = new ArrayList();
//      if (this.mLinearLayout == null)
//        return localArrayList;
//      int i = getFirstVisibleItemPosition();
//      if (this.mLinearLayout.getChildCount() < 7);
//      for (int j = this.mLinearLayout.getChildCount(); ; j = i + 7)
//      {
//        for (int k = i; k < j; k++)
//          localArrayList.add(this.mLinearLayout.getChildAt(k));
//        break;
//      }
//    }

//    private List<View> getVisibleRhythmViews(boolean paramBoolean1, boolean paramBoolean2)
//    {
//      ArrayList localArrayList = new ArrayList();
//      if (this.mLinearLayout == null)
//        return localArrayList;
//      int i = getFirstVisibleItemPosition();
//      if ((paramBoolean1) && (i > 0))
//        i--;
//      if (this.mLinearLayout.getChildCount() < 7);
//      for (int j = this.mLinearLayout.getChildCount(); ; j = i + 7)
//      {
//        if ((paramBoolean2) && (j < this.mLinearLayout.getChildCount()))
//          j++;
//        for (int k = i; k < j; k++)
//          localArrayList.add(this.mLinearLayout.getChildAt(k));
//        break;
//      }
//    }
//
//    private void init()
//    {
//      this.screenWidth = getScreenDisplayMetrics().widthPixels;
//      this.rhythmItemWidth = (this.screenWidth / 7.0F);
//      mEdgeSizeForShiftRhythm = getResources().getDimensionPixelSize(2131361880);
//      mItemInitTranslationHeight = (int)this.rhythmItemWidth;
//      this.rhythmIntervalHeight = (mItemInitTranslationHeight / 6);
//      this.mTimer = new ShiftRhythmMonitorTimer();
//      this.mTimer.startMonitor();
//    }
//
//    private void makeRhythmItems(int paramInt, List<View> paramList)
//    {
//      if (paramInt >= paramList.size());
//      do
//      {
//        return;
//        for (int i = 0; i < paramList.size(); i++)
//          updateRhythmItemHeight((View)paramList.get(i), Math.max(Math.abs(paramInt - i) * this.rhythmIntervalHeight, 10));
//      }
//      while (this.rhythmItemListener == null);
//      this.rhythmItemListener.onRhythmItemChanged(paramInt);
//    }
//
//    private Animator smoothScrollX(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
//    {
//      return AnimatorUtils.moveScrollViewToX(this, paramInt1, paramInt2, paramInt3, paramBoolean);
//    }
//
//    private void updateRhythmItemHeight(float paramFloat)
//    {
//      List localList = getVisibleRhythmViews();
//      int i = (int)(paramFloat / this.rhythmItemWidth);
//      if (i == this.currentMoveItemPosition);
//      while (i >= this.mLinearLayout.getChildCount())
//        return;
//      this.currentMoveItemPosition = i;
////      LogUtil.d("current moving item position is " + this.currentMoveItemPosition);
//      makeRhythmItems(i, localList);
//    }
//
//    private void vibrate(long paramLong)
//    {
//      ((Vibrator)getContext().getSystemService("vibrator")).vibrate(new long[] { 0L, paramLong }, -1);
//    }
//
//    public Animator bounceUpRhythmItem(int paramInt, boolean paramBoolean)
//    {
//      if (paramInt >= 0)
//        return bounceUpRhythmItem(this.mLinearLayout.getChildAt(paramInt), paramBoolean);
//      return null;
//    }
//
//    public Animator bounceUpRhythmItem(View paramView, boolean paramBoolean)
//    {
//      if (paramView != null)
//        return AnimatorUtils.showUpAndDownBounce(paramView, 10, 350, paramBoolean, true);
//      return null;
//    }
//
//    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
//    {
//      if (paramMotionEvent.getAction() == 2)
//      {
//        this.mTimer.monitorTouchPosition(paramMotionEvent.getX(), paramMotionEvent.getY());
//        float f2 = paramMotionEvent.getX();
////        LogUtil.d("dispatchTouchEvent", "move the x is " + f2 + " y is " + paramMotionEvent.getY());
//        updateRhythmItemHeight(f2);
//      }
//      do
//      {
//        do
//        {
//          do
//          {
//            return true;
//            if (paramMotionEvent.getAction() != 0)
//              break;
//            this.mTimer.monitorTouchPosition(paramMotionEvent.getX(), paramMotionEvent.getY());
//            float f1 = paramMotionEvent.getX();
////            LogUtil.d("dispatchTouchEvent", "down the x is " + f1);
//            updateRhythmItemHeight(f1);
//          }
//          while (this.rhythmItemListener == null);
//          this.rhythmItemListener.onStartSwipe();
//          return true;
//        }
//        while (paramMotionEvent.getAction() != 1);
//        this.mTimer.monitorTouchPosition(-1.0F, -1.0F);
//      }
//      while (this.currentMoveItemPosition < 0);
//      List localList = getVisibleRhythmViews();
//      int i = localList.size();
//      int j = this.currentMoveItemPosition;
//      View localView1 = null;
//      if (i > j)
//        localView1 = (View)localList.remove(this.currentMoveItemPosition);
//      final View localView2 = localView1;
//      this.mHandler.postDelayed(new Runnable()
//      {
//        public void run()
//        {
//          List localList = RhythmLayout.this.getAllRhythmViews();
//          for (int i = 0; i < localList.size(); i++)
//          {
//            View localView = (View)localList.get(i);
//            if (localView != localView2)
//              RhythmLayout.this.shootDownRhythmItem(localView, true);
//          }
//        }
//      }
//      , 200L);
//      if (this.rhythmItemListener != null)
//        this.rhythmItemListener.onSelected(getFirstVisibleItemPosition() + this.currentMoveItemPosition);
//      while (true)
//      {
//        this.currentMoveItemPosition = -1;
//        vibrate(20L);
//        return true;
//        bounceUpRhythmItem(localView1, true);
//      }
//    }
//
//    public int getFirstVisibleItemPosition()
//    {
//      if (this.mLinearLayout == null)
//      {
//        j = 0;
//        return j;
//      }
//      int i = this.mLinearLayout.getChildCount();
//      for (int j = 0; ; j++)
//      {
//        if (j >= i)
//          break label61;
//        View localView = this.mLinearLayout.getChildAt(j);
//        if (getScrollX() < localView.getX() + this.rhythmItemWidth / 2.0F)
//          break;
//      }
//      label61: return 0;
//    }
//
//    public LinearLayout getLayoutWrapper()
//    {
//      return this.mLinearLayout;
//    }
//
//    public float getRhythmItemWidth()
//    {
//      return this.rhythmItemWidth;
//    }
//
//    public void invalidateData()
//    {
//      int i = this.mLinearLayout.getChildCount();
//      if (i < this.mRhythmAdapter.getCount())
//        for (int j = i; j < this.mRhythmAdapter.getCount(); j++)
//          this.mLinearLayout.addView(this.mRhythmAdapter.getView(j, null, null));
//    }
//
//    protected void onDetachedFromWindow()
//    {
//      if (this.mTimer != null)
//      {
//        this.mTimer.reset();
//        this.mTimer = null;
//      }
//      super.onDetachedFromWindow();
//    }
//
//    public Animator scrollToPosition(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
//    {
//      return smoothScrollX((int)this.mLinearLayout.getChildAt(paramInt1).getX(), paramInt2, paramInt3, paramBoolean);
//    }
//
//    public Animator scrollToPosition(int paramInt1, int paramInt2, boolean paramBoolean)
//    {
//      return smoothScrollX((int)this.mLinearLayout.getChildAt(paramInt1).getX(), 300, paramInt2, paramBoolean);
//    }
//
//    public void setRhythmAdapter(RhythmAdapter paramRhythmAdapter)
//    {
//      if (this.mLinearLayout == null)
//        this.mLinearLayout = ((LinearLayout)getChildAt(0));
//      this.mRhythmAdapter = paramRhythmAdapter;
//      this.lastDisplayItemPosition = -1;
////      LogUtil.d("total child count is " + this.mLinearLayout.getChildCount());
//      ArrayList localArrayList = new ArrayList();
//      int i = this.mLinearLayout.getChildCount();
//      for (int j = 0; j < i; j++)
//        localArrayList.add(this.mLinearLayout.getChildAt(j));
//      for (int k = 0; k < i; k++)
//      {
//        View localView = (View)localArrayList.get(k);
//        this.mLinearLayout.removeView(localView);
//      }
//      this.mLinearLayout.invalidate();
//      this.mLinearLayout.requestLayout();
//      for (int m = 0; m < this.mRhythmAdapter.getCount(); m++)
//      {
//        this.mRhythmAdapter.setItemWidth(this.rhythmItemWidth);
//        this.mLinearLayout.addView(this.mRhythmAdapter.getView(m, null, null));
//      }
//    }
//
//    public void setRhythmItemListener(IRhythmItemListener paramIRhythmItemListener)
//    {
//      this.rhythmItemListener = paramIRhythmItemListener;
//    }
//
//    public void setScrollRhythmStartDelayTime(int paramInt)
//    {
//      this.scrollRhythmStartDelayTime = paramInt;
//    }
//
//    public Animator shootDownRhythmItem(int paramInt, boolean paramBoolean)
//    {
//      if ((paramInt >= 0) && (this.mLinearLayout != null) && (this.mLinearLayout.getChildCount() > paramInt))
//        return shootDownRhythmItem(this.mLinearLayout.getChildAt(paramInt), paramBoolean);
//      return null;
//    }
//
//    public Animator shootDownRhythmItem(View paramView, boolean paramBoolean)
//    {
//      if (paramView != null)
//        return AnimatorUtils.showUpAndDownBounce(paramView, mItemInitTranslationHeight, 350, paramBoolean, true);
////      LogUtil.w("faint, impossible!!!!!");
//      return null;
//    }
//
//    public void showRhythmAtPosition(int paramInt)
//    {
//      if (this.lastDisplayItemPosition == paramInt)
//        return;
//      Animator localAnimator1 = null;
//      Animator localAnimator2 = null;
//      Animator localAnimator3 = null;
//      AnimatorSet localAnimatorSet1 = null;
//      if ((this.lastDisplayItemPosition < 0) || (this.mRhythmAdapter.getCount() <= 7) || (paramInt <= 3))
//      {
//        localAnimator1 = scrollToPosition(0, this.scrollRhythmStartDelayTime, false);
//        localAnimator2 = bounceUpRhythmItem(paramInt, false);
//        localAnimator3 = shootDownRhythmItem(this.lastDisplayItemPosition, false);
//        localAnimatorSet1 = new AnimatorSet();
//        if ((localAnimator3 == null) || (localAnimator2 == null))
//          break label188;
//        localAnimatorSet1.playTogether(new Animator[] { localAnimator2, localAnimator3 });
//      }
//      while (true)
//      {
//        AnimatorSet localAnimatorSet2 = new AnimatorSet();
//        localAnimatorSet2.playSequentially(new Animator[] { localAnimator1, localAnimatorSet1 });
//        localAnimatorSet2.start();
//        this.lastDisplayItemPosition = paramInt;
//        return;
//        if (this.mRhythmAdapter.getCount() - paramInt <= 3)
//        {
//          localAnimator1 = scrollToPosition(-7 + this.mRhythmAdapter.getCount(), this.scrollRhythmStartDelayTime, false);
//          break;
//        }
//        localAnimator1 = scrollToPosition(paramInt - 3, this.scrollRhythmStartDelayTime, false);
//        break;
//        label188: if (localAnimator2 != null)
//          localAnimatorSet1.play(localAnimator2);
//        else if (localAnimator3 != null)
//          localAnimatorSet1.play(localAnimator3);
////        else
////          LogUtil.w("both are animator null " + this.lastDisplayItemPosition);
//      }
//    }
//
//    public void updateRhythmItemHeight(View paramView, int paramInt)
//    {
//      if (paramView != null)
//        AnimatorUtils.showUpAndDownBounce(paramView, paramInt, 180, true, true);
//    }
//
//    class ShiftRhythmMonitorTimer extends Timer
//    {
//      private final int CHECK_TIME_INTERVAL = 200;
//      private boolean canShift = true;
//      private TimerTask mTimerTask;
//      float x = -1.0F;
//      float y = -1.0F;
//
//      public ShiftRhythmMonitorTimer()
//      {
//      }
//
//      void monitorTouchPosition(float paramFloat1, float paramFloat2)
//      {
//        this.x = paramFloat1;
//        this.y = paramFloat2;
//        if ((paramFloat1 < 0.0F) || (paramFloat1 > RhythmLayout.mEdgeSizeForShiftRhythm) || (paramFloat1 < RhythmLayout.this.screenWidth - RhythmLayout.mEdgeSizeForShiftRhythm) || (paramFloat2 < 0.0F))
//          this.canShift = false;
//      }
//
//      void reset()
//      {
//        this.y = -1.0F;
//        this.x = -1.0F;
//        if (this.mTimerTask != null)
//        {
//          this.mTimerTask.cancel();
//          this.mTimerTask = null;
//        }
//        cancel();
//      }
//
//      void startMonitor()
//      {
//        if (this.mTimerTask == null)
//        {
//          this.mTimerTask = new TimerTask()
//          {
//            public void run()
//            {
//              if (RhythmLayout.ShiftRhythmMonitorTimer.this.canShift)
//              {
//                int i = RhythmLayout.this.getFirstVisibleItemPosition();
//                boolean bool1 = false;
//                int j = 0;
//                int k;
//                boolean bool2;
//                if ((RhythmLayout.ShiftRhythmMonitorTimer.this.x <= RhythmLayout.mEdgeSizeForShiftRhythm) && (RhythmLayout.ShiftRhythmMonitorTimer.this.x >= 0.0F))
//                  if (i - 1 >= 0)
//                  {
//                    k = i - 1;
//                    bool2 = true;
//                  }
//                while (true)
//                {
//                  final int m = k;
//                  final int n = j;
//                  final List localList = RhythmLayout.this.getVisibleRhythmViews(bool2, bool1);
//                  RhythmLayout.this.mHandler.post(new Runnable()
//                  {
//                    public void run()
//                    {
//                      RhythmLayout.this.makeRhythmItems(n, localList);
//                      RhythmLayout.this.scrollToPosition(m, 200, 0, true);
//                      RhythmLayout.this.vibrate(10L);
//                    }
//                  });
//                  do
//                  {
//                    return;
//                    k = i;
//                    j = 0;
//                    bool2 = false;
//                    bool1 = false;
//                    break;
//                  }
//                  while (RhythmLayout.ShiftRhythmMonitorTimer.this.x < RhythmLayout.this.screenWidth - RhythmLayout.mEdgeSizeForShiftRhythm);
//                  if (RhythmLayout.this.mLinearLayout.getChildCount() >= 1 + (i + 7))
//                  {
//                    k = i + 1;
//                    bool1 = true;
//                    j = 7;
//                    bool2 = false;
//                  }
//                  else
//                  {
//                    k = i;
//                    j = 6;
//                    bool2 = false;
//                    bool1 = false;
//                  }
//                }
//              }
//              RhythmLayout.ShiftRhythmMonitorTimer.access$202(RhythmLayout.ShiftRhythmMonitorTimer.this, true);
//            }
//          };
//          schedule(this.mTimerTask, 200L, 200L);
//        }
//      }
//    }
  }
