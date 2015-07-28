package com.cyou.mobopick.app;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.cyou.mobopick.R;
import com.cyou.mobopick.util.AppTheme;

/**
 * Created by chengfei on 14-10-25.
 */
public abstract class CommonActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));
        setContentView(getContentViewId());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, getFragment()).disallowAddToBackStack().commit();
    }

    public abstract Fragment getFragment();

    String getTag() {
        return getComponentName().getShortClassName();
    }

    int getContentViewId() {
        return R.layout.activity_common;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (!getComponentName().getClassName().equals(MainActivity.class.getName()) && item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
