package com.cyou.mobopick.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.CardAppPagerAdapter;
import com.cyou.mobopick.adapter.RhythmAdapter;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.domain.AppModelListResult;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.net.AppTimelineRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.AnimatorUtils;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.ColorUtils;
import com.cyou.mobopick.util.LogUtils;
import com.cyou.mobopick.util.RhythmManager;
import com.cyou.mobopick.volley.MyVolley;
import com.handmark.pulltorefresh.extras.viewpager.PullToRefreshViewPager;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AppTimelineFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AppTimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppTimelineFragment extends BaseFragment implements PullToRefreshBase.OnRefreshListener2<ViewPager>, RecyclerView.OnItemTouchListener, ViewPager.OnPageChangeListener, NetworkRequestListener<AppModelListResult>,PullToRefreshBase.OnPullEventListener<ViewPager> {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = AppTimelineFragment.class.getSimpleName();
    @InjectView(R.id.pager)
    protected PullToRefreshViewPager mPullToRefreshViewPager;
    protected ViewPager mViewPager;
    @InjectView(R.id.pager_backgroud)
    protected View pagerBackgroud;
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    GestureDetectorCompat gestureDetector;
    LinearLayoutManager layoutManager;
    RhythmAdapter adapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private CardAppPagerAdapter cardAdapter;
    private List<AppModel> appModels = new ArrayList<AppModel>();
    private int pageCurrent = 1;
    private AppModelListResult result;

    protected TextView dayText;
    protected TextView monthWeekText;
    protected ImageButton toHeadButton;
    private View actionbarDivider;

    public AppTimelineFragment() {
        // Required empty public constructor
    }

    public static AppTimelineFragment newInstance() {
        AppTimelineFragment fragment = new AppTimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        LogUtils.d(TAG, "bar = %s", bar);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dayText = (TextView) bar.getCustomView().findViewById(R.id.text_time_first);
        monthWeekText = (TextView) bar.getCustomView().findViewById(R.id.text_time_second);
        toHeadButton = (ImageButton) bar.getCustomView().findViewById(R.id.btn_rocket_to_head);
        actionbarDivider = bar.getCustomView().findViewById(R.id.actionbar_divider);
        toHeadButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_app_timeline, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setShowProgress(true);
        mViewPager = mPullToRefreshViewPager.getRefreshableView();
        mViewPager.setOnPageChangeListener(this);
        mPullToRefreshViewPager.setOnRefreshListener(this);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rhythmManager = new RhythmManager(layoutManager, recyclerView);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHorizontalScrollBarEnabled(false);
        recyclerView.setHorizontalFadingEdgeEnabled(false);
        recyclerView.setScrollBarDefaultDelayBeforeFade(100);
        recyclerView.setActivated(false);
        // allows for optimizations if all items are of the same size:
        recyclerView.setHasFixedSize(true);
        recyclerView.smoothScrollToPosition(0);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(this);
        mPullToRefreshViewPager.setOnPullEventListener(this);
        gestureDetector =
                new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener());
        cardAdapter = new CardAppPagerAdapter(getActivity().getSupportFragmentManager(), appModels);
        mViewPager.setAdapter(cardAdapter);
        adapter = new RhythmAdapter(appModels);
//        adapter.setItemWidth(getActivity().getWindowManager().getDefaultDisplay().getWidth() / RhythmManager.ITEM_COUNT);
        recyclerView.setAdapter(adapter);
        load(pageCurrent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        gestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

    }

    private void load(int pageCurrent) {
          MyVolley.getRequestQueue().add(new AppTimelineRequest(this, pageCurrent));

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_rocket_to_head) {
            mViewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onPageScrolled(int position, float v, int positionOffsetPixels) {
//        Log.i("xx","v ==" + v + "\ti2=" + positionOffsetPixels + "\ti =" + position);
        float nextCompent = 1.0f * Math.abs(positionOffsetPixels) / DeviceInfo.screenWidth;
        float currentCompent = 1.0f - nextCompent;
        String nextColorStr = null;
        if (positionOffsetPixels > 0 && position + 1 < mViewPager.getAdapter().getCount()) {
            nextColorStr = AppTheme.getBgColorString(position + 1);
        } else if (position - 1 > 0) {
            nextColorStr = AppTheme.getBgColorString(position - 1);
        }
        String curColorStr = AppTheme.getBgColorString(position);
        if (nextColorStr == null) {
            nextColorStr = "#000000";
        }
        int mixedColor = ColorUtils.mixColor(Color.parseColor(curColorStr), Color.parseColor(nextColorStr), currentCompent);
        pagerBackgroud.setBackgroundColor(mixedColor);
        bar.setBackgroundDrawable(new ColorDrawable(mixedColor));
    }


    @Override
    public void onPageSelected(final int position) {

        AppTheme.setCurBgColorStr(position);
        adapter.setCurrentPosition(position,layoutManager, recyclerView);
        AppModel appModel = appModels.get(position);
        dayText.setText(appModel.getCreateDay());
        toHeadButton.setVisibility(View.VISIBLE);
        monthWeekText.setText(appModel.getCreateMonthWeek());
        if (appModel.isTodayOrYesterday()) {
            AnimatorUtils.animViewFadeOut(actionbarDivider);
            AnimatorUtils.animViewFadeOut(toHeadButton);
        } else {
            AnimatorUtils.animViewFadeIn(actionbarDivider);
            AnimatorUtils.animViewFadeIn(toHeadButton);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mPullToRefreshViewPager.onRefreshComplete();
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
        setShowProgress(false);
        //TODO add error prompt
    }

    @Override
    public void onResponse(AppModelListResult response) {
        if (response.pageCurrent == 1) {
            setShowProgress(false);
            result = response;
            appModels.clear();
            appModels.addAll(response.appModels);
            cardAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            onPageSelected(0);
        } else if (!result.equals(response)) {
            appModels.addAll(response.appModels);
            cardAdapter.notifyDataSetChanged();
            adapter.notifyDataSetChanged();
            result = response;
        }
        mPullToRefreshViewPager.onRefreshComplete();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ViewPager> refreshView) {
        load(1);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ViewPager> refreshView) {
        if (result != null && result.hasMore()) {
            load(result.pageCurrent + 1);
        }
    }

    @Override
    public void onPullEvent(PullToRefreshBase<ViewPager> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
        if (state == PullToRefreshBase.State.RELEASE_TO_REFRESH) {
            refreshView.onRefreshComplete();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = recyclerView.findChildViewUnder(e.getX(), e.getY());

            if (view == null) {
                return super.onSingleTapConfirmed(e);
            }
            final int clickedChild = recyclerView.indexOfChild(view) + layoutManager.findFirstVisibleItemPosition();
            mViewPager.setCurrentItem(clickedChild, true);
            return true;
        }

        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }
    }

    RhythmManager rhythmManager;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyVolley.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return request instanceof AppTimelineRequest;
            }
        });
    }
}
