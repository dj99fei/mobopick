package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;

import com.cyou.mobopick.fragment.GroupListFragment;

/**
 * Created by chengfei on 14/11/11.
 */
public class AddGroupActivity extends CommonActivity {

    @Override
    public Fragment getFragment() {
        return GroupListFragment.newInstance();
    }
}
