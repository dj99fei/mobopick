package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;

import com.cyou.mobopick.fragment.DownloadListFragment;

/**
 * Created by chengfei on 14/11/5.
 */
public class DownloadListActivity extends CommonActivity {
    @Override
    Fragment getFragment() {
        return DownloadListFragment.newInstance();
    }
}
