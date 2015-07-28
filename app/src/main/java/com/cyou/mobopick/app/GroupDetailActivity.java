package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;

import com.cyou.mobopick.fragment.GroupDetailFragment;
import com.cyou.mobopick.util.Constant;

/**
 * Created by chengfei on 14/11/12.
 */
public class GroupDetailActivity extends CommonActivity {

    @Override
    public Fragment getFragment() {
        String groupId = getIntent().getStringExtra(Constant.INTENT_KEY.GROUP_ID);
        return GroupDetailFragment.newInstance(groupId);
    }
}
