package com.cyou.mobopick.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.ImageText;
import com.cyou.mobopick.fragment.ImageTextPagerFragment;
import com.cyou.mobopick.util.Constant;

import java.util.ArrayList;

/**
 * Created by chengfei on 14-10-24.
 *
 * @see com.cyou.mobopick.fragment.ImageTextFragment
 */
public class ImageTextActivity extends CommonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        ArrayList<ImageText> imageTexts = getIntent().getParcelableArrayListExtra(Constant.INTENT_KEY.IMAGE_TEXT_LIST);
        int position = getIntent().getIntExtra(Constant.INTENT_KEY.POSITION, 0);
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, ImageTextPagerFragment.newInstance(imageTexts, position)).commit();
    }

    @Override
    Fragment getFragment() {

        ArrayList<ImageText> imageTexts = getIntent().getParcelableArrayListExtra(Constant.INTENT_KEY.IMAGE_TEXT_LIST);
        int position = getIntent().getIntExtra(Constant.INTENT_KEY.POSITION, 0);
        return ImageTextPagerFragment.newInstance(imageTexts, position);
    }


}
