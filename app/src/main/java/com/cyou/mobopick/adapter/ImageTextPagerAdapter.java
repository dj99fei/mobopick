package com.cyou.mobopick.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.cyou.mobopick.domain.ImageText;
import com.cyou.mobopick.fragment.ImageTextFragment;

import java.util.List;

/**
 * Created by chengfei on 14-10-6.
 */
public class ImageTextPagerAdapter extends FragmentStatePagerAdapter {


    private List<ImageText> imageTextList;
    public ImageTextPagerAdapter(FragmentManager paramFragmentManager, List<ImageText> imageTextList) {
        super(paramFragmentManager);
        this.imageTextList = imageTextList;
    }


    public int getCount() {
        return this.imageTextList.size();
    }

    public Fragment getItem(int paramInt) {
        ImageText imageText = imageTextList.get(paramInt);
        return ImageTextFragment.newInstance(imageText);
    }

}
