package com.cyou.mobopick.app;

import android.os.Bundle;

/**
 * @author fei.cheng
 */
public interface ActivityLifecycleCallbacks {
    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onStop();

    void onDestory();

    void onPause();

    void onResume();
}
