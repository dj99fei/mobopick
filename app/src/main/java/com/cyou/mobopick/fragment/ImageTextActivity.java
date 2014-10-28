package com.cyou.mobopick.fragment;

import android.os.Bundle;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.BaseActivity;
import com.cyou.mobopick.domain.ImageText;
import com.cyou.mobopick.util.Constant;

import java.util.ArrayList;

/**
 * Created by chengfei on 14-10-24.
 * @see com.cyou.mobopick.fragment.ImageTextFragment
 */
public class ImageTextActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ArrayList<ImageText> imageTexts = getIntent().getParcelableArrayListExtra(Constant.INTENT_KEY.IMAGE_TEXT_LIST);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ImageTextPagerFragment.newInstance(imageTexts)).commit();
    }

}
