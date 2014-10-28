package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;

import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.fragment.AppDetailFragment;
import com.cyou.mobopick.util.Constant;

/**
 * Created by chengfei on 14-10-24.
 */
public class AppDetailActivity extends CommonActivity {

    @Override
    Fragment getFragment() {
        AppModel appModel = getIntent().getParcelableExtra(Constant.INTENT_KEY.APPMODEL);
        return  AppDetailFragment.newInstance(appModel);
    }

}
