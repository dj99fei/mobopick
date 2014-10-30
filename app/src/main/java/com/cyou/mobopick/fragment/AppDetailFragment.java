package com.cyou.mobopick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.EmojiCommentAdapter;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.net.AppDetailRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.volley.MyVolley;
import com.squareup.picasso.Picasso;

import org.lucasr.twowayview.TwoWayLayoutManager;
import org.lucasr.twowayview.widget.ListLayoutManager;

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
    GestureDetectorCompat gestureDetector;
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

    private List<AppModel.EmojiComment> emojiComments = new ArrayList<AppModel.EmojiComment>();
    private EmojiCommentAdapter emojiCommentAdapter;

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
        gestureDetector =
                new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener());

    }

    private void initRecyclerView(RecyclerView recyclerView) {
//        recyclerView.setItemViewCacheSize(10);
        ListLayoutManager layoutManager = new ListLayoutManager(getActivity(), TwoWayLayoutManager.Orientation.HORIZONTAL);
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
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
//            View view = imageSetRecyclerView.findChildViewUnder(e.getX(), e.getY());
//            if (view == null) {
//                return super.onSingleTapConfirmed(e);
//            }
//            final int clickedChild = imageSetRecyclerView.indexOfChild(view);
//            return true;
            return false;
        }
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }
    }
}
