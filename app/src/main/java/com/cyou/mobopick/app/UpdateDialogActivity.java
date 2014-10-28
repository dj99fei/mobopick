package com.cyou.mobopick.app;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.cyou.mobopick.R;
import com.cyou.mobopick.fragment.UpdateDialogFragment;

/**
 * Created by chengfei on 14-10-21.
 */
public class UpdateDialogActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Dialog);
        FrameLayout layout = new FrameLayout(this);
        layout.setId(R.id.content_frame);
        UpdateDialogFragment fragment = UpdateDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), null);
    }
}
