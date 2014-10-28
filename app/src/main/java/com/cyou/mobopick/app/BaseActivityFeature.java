package com.cyou.mobopick.app;

import android.view.View;

/**
 * The features that all the activities in this application should have.
 *
 * @author fei.cheng
 */
public interface BaseActivityFeature extends CrossfadeFeature {

    View getContentView();

    View getProgressView();

    int getFragmentContainerId();

    boolean hasDestroyed();
}
