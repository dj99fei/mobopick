package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.EmojiCommentAdapter;
import com.cyou.mobopick.adapter.ImageTextAdapter;
import com.cyou.mobopick.app.ImageTextActivity;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.net.AppDetailRequest;
import com.cyou.mobopick.net.EmojiCommentRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.providers.DownloadManager;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.volley.MyVolley;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by chengfei on 14-10-24.
 */
public class AppDetailFragment extends BaseFragment implements NetworkRequestListener<AppModel>, RecyclerView.OnItemTouchListener {

    private AppModel appModel;
    @InjectView(R.id.recyclerview_image_set)
    protected RecyclerView imageSetRecyclerView;

    @InjectView(R.id.recyclerview_emoji_comment)
    protected RecyclerView emojiCommentRecyclerView;
    GestureDetectorCompat emojiCommentsGustreDetctor;
    GestureDetectorCompat iamgeSetGustreDetctor;
    @InjectView(R.id.text_app_name)
    protected TextView appnameText;
    @InjectView(R.id.text_size_and_view)
    protected TextView sizeAndViewText;
    @InjectView(R.id.icon)
    protected ImageView iconImage;
    @InjectView(R.id.text_appdes)
    protected TextView appdesText;
    @InjectView(R.id.text_tigs)
    protected TextView tigsText;
    @InjectView(R.id.download)
    protected ImageView downloadImage;

    private List<AppModel.EmojiComment> emojiComments = new ArrayList<AppModel.EmojiComment>();
    private EmojiCommentAdapter emojiCommentAdapter;
    private DownloadManager downloadManager;

    public static AppDetailFragment newInstance(AppModel appModel) {
        AppDetailFragment fragment = new AppDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.INTENT_KEY.APPMODEL, appModel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appModel = getArguments().getParcelable(Constant.INTENT_KEY.APPMODEL);
        downloadManager = new DownloadManager(getActivity().getContentResolver(), getActivity().getPackageName());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_app_detail, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setShowProgress(true);
        AppDetailRequest request = new AppDetailRequest(String.valueOf(appModel.id), this);
        MyVolley.getRequestQueue().add(request);
        initRecyclerView(imageSetRecyclerView);
        initRecyclerView(emojiCommentRecyclerView);
        emojiCommentAdapter = new EmojiCommentAdapter(emojiComments);
        emojiCommentRecyclerView.setAdapter(emojiCommentAdapter);
        emojiCommentsGustreDetctor =
                new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener(emojiCommentRecyclerView));
        iamgeSetGustreDetctor = new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener(imageSetRecyclerView));
        downloadImage.setOnClickListener(this);
//        ((GradientDrawable)downloadImage.getBackground()).setColor(appModel.getThemeColor());
        appModel.setActionImage(downloadImage);
    }

    private void initRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setItemViewCacheSize(10);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setHorizontalFadingEdgeEnabled(false);
        // allows for optimizations if all items are of the same size:
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(AppModel response) {
        appModel = response;
        setViewData();
        setShowProgress(false);
    }

    private void setViewData() {
        Picasso.with(getActivity()).load(appModel.getIconUrl()).into(iconImage);
        appnameText.setText(appModel.title);
        tigsText.setText(appModel.tags);
        sizeAndViewText.setText(appModel.getSizeAndViews());
        appdesText.setText(appModel.content);
        emojiComments.clear();
        emojiComments.addAll(appModel.getEmobjiComment());
        emojiCommentAdapter.notifyDataSetChanged();
        appModel.getImageText();
        imageSetRecyclerView.setAdapter(new ImageTextAdapter(appModel.getImageTexts()));
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (recyclerView.getId() == R.id.recyclerview_emoji_comment) {
            emojiCommentsGustreDetctor.onTouchEvent(motionEvent);
        } else if (recyclerView.getId() == R.id.recyclerview_image_set) {
            iamgeSetGustreDetctor.onTouchEvent(motionEvent);
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener implements NetworkRequestListener<AppModel.EmojiComment> {

        private RecyclerView recyclerView;

        private RecyclerViewDemoOnGestureListener(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
            final int clickedChild = recyclerView.indexOfChild(view) + ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
//            return true;
            if (recyclerView.getId() == R.id.recyclerview_emoji_comment) {
                AppModel.EmojiComment comment = emojiComments.get(clickedChild);
                StringBuilder builder = new StringBuilder(EmojiCommentRequest.URL);
                builder.append(comment.interfaceName);
                builder.append("/");
                builder.append(appModel.id);
                MyVolley.getRequestQueue().add(new EmojiCommentRequest(comment, builder.toString(), this));
                ImageView emojiImageView = (ImageView) view.findViewById(R.id.image);
//                emojiImageView.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_overal));
                AnimatorSet set1 = new AnimatorSet();
                set1.setDuration(200);
                set1.playTogether(ObjectAnimator.ofFloat(emojiImageView, "scaleX", 1.0f, 1.2f), ObjectAnimator.ofFloat(emojiImageView, "scaleY", 1.0f, 1.2f));
                AnimatorSet set2 = new AnimatorSet();
                set2.setDuration(100);
                set2.playTogether(ObjectAnimator.ofFloat(emojiImageView, "scaleX", 1.2f, 1.0f), ObjectAnimator.ofFloat(emojiImageView, "scaleY", 1.2f, 1.0f));
//                ObjectAnimator animator = ;
                AnimatorSet set = new AnimatorSet();
                set.setInterpolator(new OvershootInterpolator());
                set.playSequentially(set1, set2);
                set.start();
                return true;
            }

            if (recyclerView.getId() == R.id.recyclerview_image_set) {
                Intent intent = new Intent(getActivity(), ImageTextActivity.class);
                intent.putExtra(Constant.INTENT_KEY.POSITION, clickedChild);
                intent.putParcelableArrayListExtra(Constant.INTENT_KEY.IMAGE_TEXT_LIST, (ArrayList<? extends android.os.Parcelable>) appModel.getImageTexts());
                startActivity(intent);
                return true;
            }
            return false;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }

        @Override
        public void onResponse(AppModel.EmojiComment response) {
            response.totalNum = response.totalNum + 1;
            recyclerView.getAdapter().notifyDataSetChanged();
            Toast.makeText(getActivity(), "success", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.download) {
            appModel.handleAction(downloadManager);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyVolley.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request instanceof AppDetailRequest;
            }
        });
    }
}
