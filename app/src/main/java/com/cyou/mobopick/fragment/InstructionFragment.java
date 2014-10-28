package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.MainActivity;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.SharedPreferencesHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

/**
 * Created by chengfei on 14-10-21.
 */
public class InstructionFragment extends BaseFragment implements ViewPager.OnPageChangeListener {

    @InjectView(R.id.pager)
    protected ViewPager pager;
    private int indicators[] = new int[]{R.drawable.indicator_point_blue, R.drawable.indicator_point_blue_dark, R.drawable.indicator_point_green,
            R.drawable.indicator_point_red};
    @InjectView(R.id.indicator1)
    protected ImageView indicatorImage1;
    @InjectView(R.id.indicator2)
    protected ImageView indicatorImage2;
    @InjectView(R.id.indicator3)
    protected ImageView indicatorImage3;
    @InjectView(R.id.indicator4)
    protected ImageView indicatorImage4;
    private ImageView[] indicatorImages = new ImageView[4];
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_instruction, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pager.setAdapter(new ScreenSlidePagerAdapter(getChildFragmentManager()));
        pager.setOnPageChangeListener(this);
        indicatorImages[0] = indicatorImage1;
        indicatorImages[1] = indicatorImage2;
        indicatorImages[2] = indicatorImage3;
        indicatorImages[3] = indicatorImage4;
        setIndicator(0);
    }

    private void setIndicator(int position) {
        for (int i = 0; i < indicatorImages.length; i++) {
            if (i == position) {
                indicatorImages[i].setImageResource(indicators[i]);
            } else {
                indicatorImages[i].setImageResource(R.drawable.indicator_point_gray);
            }
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i2) {

    }

    @Override
    public void onPageSelected(int i) {
        setIndicator(i);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        private int images[] = new int[]{R.drawable.instruction_1, R.drawable.instruction_2, R.drawable.instruction_3, R.drawable.instruction_4};
        private int desIds[] = new int[]{R.string.instruction_1, R.string.instruction_2, R.string.instruction_3, R.string.instruction_4};

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.newInstance(images[position], desIds[position], position == images.length - 1);
        }

        @Override
        public int getCount() {
            return images.length;
        }


    }

    public static class ScreenSlidePageFragment extends BaseFragment {

        @InjectView(R.id.image)
        protected ImageView imageView;
        @InjectView(R.id.start_btn)
        protected Button startButton;
        @InjectView(R.id.text)
        protected TextView desText;
        private boolean isLast;
        private int imageId;
        private int desId;

        public static ScreenSlidePageFragment newInstance(int imageId, int desId, boolean isLast) {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle arguments = new Bundle();
            arguments.putInt(Constant.INTENT_KEY.DES_ID, desId);
            arguments.putInt(Constant.INTENT_KEY.IMAGE_ID, imageId);
            arguments.putBoolean(Constant.INTENT_KEY.IS_LAST, isLast);
            fragment.setArguments(arguments);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_screen_slide, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            if (isLast) {
                startButton.setVisibility(View.VISIBLE);
            }
            startButton.setOnClickListener(this);
            Picasso.with(getActivity()).load(imageId).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
//                    int bottom = imageView.getDrawable().getBounds().bottom;
//                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) desText.getLayoutParams();
//                    lp.topMargin = bottom + 20;
                    desText.setText(desId);
                }

                @Override
                public void onError() {

                }
            });

        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            imageId = getArguments().getInt(Constant.INTENT_KEY.IMAGE_ID);
            desId = getArguments().getInt(Constant.INTENT_KEY.DES_ID);
            isLast = getArguments().getBoolean(Constant.INTENT_KEY.IS_LAST);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.start_btn) {
                getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
                SharedPreferencesHelper.getInstance().withModule(R.string.module_default).withKey(R.string.key_first_open).setData(boolean.class, false).commit();
            }
        }
    }

}