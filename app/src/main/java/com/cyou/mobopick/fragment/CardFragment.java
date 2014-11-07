package com.cyou.mobopick.fragment;

import android.content.Intent;
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
import com.cyou.mobopick.bus.AppInstallEvent;
import com.cyou.mobopick.bus.DownloadEvent;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.providers.DownloadManager;
import com.cyou.mobopick.providers.downloads.DownloadService;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.LogUtils;
import com.cyou.mobopick.view.HtmlTextView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

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
    private EventBus eventBus;

    DownloadManager downloadManager;
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
        downloadManager = new DownloadManager(getActivity().getContentResolver(),
                getActivity().getPackageName());
        startDownloadService();
        eventBus = EventBus.getDefault();

    }

    private void startDownloadService() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), DownloadService.class);
        getActivity().startService(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        eventBus.register(this);
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
//        ((GradientDrawable)mDownloadImageView.getBackground()).setColor(appModel.getThemeColor());
        rootView.setOnClickListener(this);
        Picasso.with(getActivity()).load(appModel.getCoverImageUrl()).into(mCoverImageView, callback);
        appModel.setActionImage(mDownloadImageView);

    }



    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.download) {
            appModel.handleAction(downloadManager);
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

    public void onEventMainThread(DownloadEvent event) {
        LogUtils.d(TAG, "received event url , appmodel downloadurl :%s\n%s", event.getUrl(), appModel.downloadUrl);
        if (event.getUrl().equals(appModel.downloadUrl)) {
            appModel.setActionImage(mDownloadImageView, true);
        }
    }

    public void onEventMainThread(AppInstallEvent event) {
        LogUtils.d(TAG, "%s", event.getPackageName());
        LogUtils.d(TAG, "%s", appModel.packageName);
        if (event.getPackageName().contains(appModel.packageName)) {
            appModel.setActionImage(mDownloadImageView, true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        eventBus.unregister(this);
    }
}

