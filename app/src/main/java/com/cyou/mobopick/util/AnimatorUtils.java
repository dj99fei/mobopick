package com.cyou.mobopick.util;


import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ArgbEvaluator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

public class AnimatorUtils {
    private static final String TAG = AnimatorUtils.class.getSimpleName();

    public static Animator animViewFadeIn(View paramView) {
        return animViewFadeIn(paramView, 200L, new float[]{ViewCompat.getAlpha(paramView), 1.0F}, null);
    }

    public static Animator animViewFadeIn(View paramView, long paramLong, float[] values, Animator.AnimatorListener paramAnimatorListener) {
        if (values == null) {
            values = new float[]{0.0F, 1.0F};
        }
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", values);
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static Animator animViewFadeOut(View paramView) {
        return animViewFadeOut(paramView, 200L, new float[]{ViewCompat.getAlpha(paramView), 0.0f}, null);
    }

    public static Animator animViewFadeOut(View paramView, long paramLong, float[] values,  Animator.AnimatorListener paramAnimatorListener) {
        if (values == null) {
            values = new float[]{1.0F, 0.0F};
        }
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramView, "alpha", values);
        localObjectAnimator.setDuration(paramLong);
        if (paramAnimatorListener != null)
            localObjectAnimator.addListener(paramAnimatorListener);
        localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static void animateHeartbeat(final View paramView) {
        ValueAnimator localValueAnimator1 = ValueAnimator.ofFloat(new float[]{1.0F, 1.5F});
        localValueAnimator1.setInterpolator(new OvershootInterpolator());
        localValueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator) {
                float f = ((Float) paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
                paramView.setScaleX(f);
                paramView.setScaleY(f);
            }
        });
        ValueAnimator localValueAnimator2 = ValueAnimator.ofFloat(new float[]{1.5F, 1.0F});
        localValueAnimator2.setInterpolator(new DecelerateInterpolator());
        localValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator paramAnonymousValueAnimator) {
                float f = ((Float) paramAnonymousValueAnimator.getAnimatedValue()).floatValue();
                paramView.setScaleX(f);
                paramView.setScaleY(f);
            }
        });
        AnimatorSet localAnimatorSet = new AnimatorSet();
        localAnimatorSet.play(localValueAnimator2).after(localValueAnimator1);
        localAnimatorSet.setDuration(200L);
        localAnimatorSet.start();
    }


    public static void cancelAnimation(View paramView) {
        Animation localAnimation = paramView.getAnimation();
        if (localAnimation != null) {
            localAnimation.cancel();
            paramView.clearAnimation();
        }
    }

    public static ScaleAnimation getIconScaleAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6) {
        ScaleAnimation localScaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
        localScaleAnimation.setInterpolator(new DecelerateInterpolator());
        localScaleAnimation.setDuration(200L);
        return localScaleAnimation;
    }

    public static TranslateAnimation getIconTranslateAnimation(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
        TranslateAnimation localTranslateAnimation = new TranslateAnimation(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        localTranslateAnimation.setInterpolator(new DecelerateInterpolator());
        localTranslateAnimation.setDuration(200L);
        return localTranslateAnimation;
    }

    public static Animator moveScrollViewToX(View paramView, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofInt(paramView, "scrollX", new int[]{paramInt1});
        localObjectAnimator.setDuration(paramInt2);
        localObjectAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        localObjectAnimator.setStartDelay(paramInt3);
        if (paramBoolean)
            localObjectAnimator.start();
        return localObjectAnimator;
    }

    public static void showBackgroundColorAnimation(View paramView, int paramInt1, int paramInt2, int paramInt3) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofInt(paramView, "backgroundColor", new int[]{paramInt1, paramInt2});
        localObjectAnimator.setDuration(paramInt3);
        localObjectAnimator.setEvaluator(new ArgbEvaluator());
        localObjectAnimator.start();
    }

    public static Animator showUpAndDownBounce(View view,int start, int mid, int end, int duration, int startDelay) {
//        float start = ViewCompat.getTranslationY(view);
        LogUtils.d(TAG, "start animation from %s, to %s, to %s", start, mid, end);
        ObjectAnimator first = ObjectAnimator.ofFloat(view, "translationY", start, mid);
        first.setInterpolator(new OvershootInterpolator());
//        first.setDuration(duration);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        ObjectAnimator second = ObjectAnimator.ofFloat(view, "translationY", mid, end);
        second.setInterpolator(new OvershootInterpolator());
//        second.setDuration(duration);
        set.playSequentially(first, second);
        set.start();
        return set;
    }
}