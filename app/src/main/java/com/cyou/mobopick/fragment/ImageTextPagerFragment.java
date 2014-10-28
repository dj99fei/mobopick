package com.cyou.mobopick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.ImageText;
import com.cyou.mobopick.util.Constant;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * Created by chengfei on 14-10-24.
 */
public class ImageTextPagerFragment extends BaseFragment {


    public static ImageTextPagerFragment newInstance(ArrayList<ImageText> imageTexts) {
        ImageTextPagerFragment fragment = new ImageTextPagerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constant.INTENT_KEY.IMAGE_TEXT_LIST, imageTexts);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectView(R.id.pager)
    protected ViewPager pager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pager_image_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        getActivity().getApplication().getTheme().

    }
}
