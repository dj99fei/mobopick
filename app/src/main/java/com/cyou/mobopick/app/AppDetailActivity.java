package com.cyou.mobopick.app;

import android.os.Bundle;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.fragment.AppDetailFragment;
import com.cyou.mobopick.util.Constant;

/**
 * Created by chengfei on 14-10-24.
 */
public class AppDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        AppModel appModel = getIntent().getParcelableExtra(Constant.INTENT_KEY.APPMODEL);

        AppDetailFragment fragment = AppDetailFragment.newInstance(appModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commitAllowingStateLoss();

    }


}
