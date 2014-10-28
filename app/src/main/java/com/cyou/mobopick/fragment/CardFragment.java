package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.AppDetailActivity;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.LogUtils;
import com.cyou.mobopick.view.HtmlTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;

/**
 * Created by chengfei on 14-10-6.
 */
public class CardFragment extends BaseFragment {

    private static final String TAG = CardFragment.class.getSimpleName();
    private static int height;
    protected AppModel appModel;
    @InjectView(R.id.image_bottom_edge)
    protected ImageView mBottomEdgeImageView;
    @InjectView(R.id.text_bravos)
    protected TextView mBravoNumText;
    @InjectView(R.id.box_card)
    protected RelativeLayout mCardLayout;
    @InjectView(R.id.image_cover)
    protected ImageView mCoverImageView;
    @InjectView(R.id.text_digest)
    protected HtmlTextView mDigestText;
    private Callback callback = new Callback() {
        @Override
        public void onSuccess() {
            if (mDigestText == null) {
                return;
            }
            mDigestText.setText(appModel.content);
//            mBravoNumText.setVisibility(View.VISIBLE);
            if (height <= 0) {
                mCoverImageView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mCoverImageView.getViewTreeObserver().removeOnPreDrawListener(this);
                        CardFragment.height = mCoverImageView.getMeasuredHeight();
                        LogUtils.d(TAG, "real height %s", CardFragment.height);
                        resizeImageView();
//                        mBravoNumText.setVisibility(View.VISIBLE);
                        mDigestText.setText(appModel.content);
                        return true;
                    }
                });
            }
        }

        @Override
        public void onError() {

        }
    };
    @InjectView(R.id.text_tags)
    protected TextView mTagText;
    @InjectView(R.id.text_title)
    protected TextView mTitleText;
    @InjectView(R.id.download)
    protected ImageView mDownloadImageView;
    @InjectView(R.id.root)
    protected View rootView;

    public CardFragment() {

    }

    public static Fragment getInstance(AppModel appModel) {
        Fragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.INTENT_KEY.APPMODEL, appModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            appModel = getArguments().getParcelable(Constant.INTENT_KEY.APPMODEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mTitleText.setText(this.appModel.title);
        this.mTagText.setText(this.appModel.tags);
        this.mBravoNumText.setText("  " + this.appModel.upNum);
        this.mDownloadImageView.setOnClickListener(this);
        if (height <= 0) {
//            this.mBravoNumText.setVisibility(View.GONE);
            LogUtils.d(TAG, "height hasn't got");
        } else {
            resizeImageView();
            LogUtils.d(TAG, "height has got %s", height);
            this.mDigestText.setText(appModel.content);
        }
        rootView.setOnClickListener(this);
        Picasso.with(getActivity()).load(appModel.getCoverImageUrl()).into(mCoverImageView, callback);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download) {
            Uri uri = Uri.parse(appModel.downloadUrl);
            Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(downloadIntent);
        }
        if (v.getId() == R.id.root) {
            Intent intent = new Intent(getActivity(), AppDetailActivity.class);
            intent.putExtra(Constant.INTENT_KEY.APPMODEL, appModel);
            getActivity().startActivity(intent);
        }
    }

    private void resizeImageView() {
        ViewGroup.LayoutParams lp = mCoverImageView.getLayoutParams();
        lp.height = height;
    }
}

