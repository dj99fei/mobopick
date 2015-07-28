package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;

import com.cyou.mobopick.fragment.CreateGroupFragment;

/**
 * Created by chengfei on 14/11/11.
 */
public class CreateGroupActivity extends CommonActivity {

    @Override
    public Fragment getFragment() {
        return CreateGroupFragment.newInstance();
    }


}
