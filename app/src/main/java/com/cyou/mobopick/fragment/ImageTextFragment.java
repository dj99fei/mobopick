package com.cyou.mobopick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.ImageText;
import com.cyou.mobopick.util.Constant;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by chengfei on 14-10-24.
 * 图文页面。用于展现大图。
 * 图片的url地址从intent中获取，对应的key值为{@link com.cyou.mobopick.util.Constant.INTENT_KEY#IMAGE_URL}
 * 支持图片底部显示文字遮罩: 通过{@link #hasText} 区分是否有文字描述，默认是没有的。
 * 如果有文字描述则通过{@link com.cyou.mobopick.util.Constant.INTENT_KEY#TEXT}传入
 */
public class ImageTextFragment extends BaseFragment {
    private boolean hasText;

    @InjectView(R.id.image)
    protected ImageViewTouch imageView;
    @InjectView(R.id.text)
    protected TextView textView;
    private ImageText imageText;


    public static ImageTextFragment newInstance(ImageText imageText) {
        Bundle args = new Bundle();
        args.putParcelable(Constant.INTENT_KEY.IMAGE_TEXT, imageText);
        ImageTextFragment fragment = new ImageTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageText = getArguments().getParcelable(Constant.INTENT_KEY.IMAGE_TEXT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_image_text, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        Picasso.with(getActivity()).load(imageText.getImageUrl()).into(imageView);
        textView.setText(imageText.text);
    }

}
