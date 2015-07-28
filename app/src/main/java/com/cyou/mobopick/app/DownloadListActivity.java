package com.cyou.mobopick.app;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.cyou.mobopick.fragment.DownloadListFragment;

/**
 * Created by chengfei on 14/11/5.
 */
public class DownloadListActivity extends CommonActivity {
    @Override
    public Fragment getFragment() {
        return DownloadListFragment.newInstance();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
