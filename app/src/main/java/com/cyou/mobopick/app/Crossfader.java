
package com.cyou.mobopick.app;

import android.view.View;

import com.cyou.mobopick.MyApplication;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * 控制loading spanner 和content view 的切换
 * 
 * @author fei.cheng
 */
public class Crossfader implements CrossfadeFeature {

    private BaseActivityFeature activityFeature;
    private int animTime;

    public Crossfader(BaseActivityFeature activityFeature) {
        super();
        this.activityFeature = activityFeature;
        animTime = MyApplication.getInstance().getResources()
                .getInteger(android.R.integer.config_shortAnimTime);
    }

    @Override
    public void setShowProgress(boolean show) {
        if (canUseProgress()) {
            final View showView = !show ? activityFeature.getContentView() : activityFeature
                    .getProgressView();
            final View hideView = !show ? activityFeature.getProgressView() : activityFeature
                    .getContentView();
            setShowProgress(showView, hideView);
        }
    }

    @Override
    public void setShowProgress(final View showView, final View hideView) {
        if (canUseProgress()) {

            final Animator showAnim = ObjectAnimator.ofFloat(showView, "alpha", 0f, 1f);
            showAnim.setDuration(animTime);
            showAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    showView.setVisibility(View.VISIBLE);
                }
            });
            showAnim.start();
            final Animator hideAnim = ObjectAnimator.ofFloat(hideView, "alpha", 1f, 0f);
            hideAnim.setDuration(animTime);
            hideAnim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    hideView.setVisibility(View.GONE);
                }
            });
            hideAnim.start();
        }
    }

    private boolean canUseProgress() {
        return activityFeature.getContentView() != null
                && activityFeature.getProgressView() != null;
    }

}
