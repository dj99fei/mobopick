package com.cyou.mobopick.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;

import com.cyou.mobopick.R;

/**
 * Created by chengfei on 14-10-25.
 */
public abstract  class CommonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
        setContentView(getContentViewId());
        getSupportFragmentManager().beginTransaction().add(R.id.content_frame, getFragment()).disallowAddToBackStack().commit();
    }

    abstract Fragment getFragment();

    String getTag() {
        return getComponentName().getShortClassName();
    }

    int getContentViewId() {
        return R.layout.activity_common;
    }

}
