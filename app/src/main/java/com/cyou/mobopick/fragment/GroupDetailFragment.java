package com.cyou.mobopick.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.GroupAppAdapter;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.net.GroupDetailRequest;
import com.cyou.mobopick.net.JoinGroupRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.util.SharedPreferencesHelper;
import com.cyou.mobopick.volley.MyVolley;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/12.
 */
public class GroupDetailFragment extends BaseFragment implements AbsListView.OnScrollListener, NetworkRequestListener {

    @InjectView(R.id.list)
    protected ListView listView;
    @InjectView(R.id.radio_group)
    protected RadioGroup raidoGroup;
    protected TextView memberNumText;
    protected TextView groupNameText;
    protected Button joinButton;
    protected TextView groupDesText;
    protected TextView appNumText;
    protected TextView myAppNumText;

    private Group group;

    public static GroupDetailFragment newInstance(Group group) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.INTENT_KEY.GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }

    protected String groupId;

    public static GroupDetailFragment newInstance(String groupId) {
        GroupDetailFragment fragment = new GroupDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.INTENT_KEY.GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        group = getArguments().getParcelable(Constant.INTENT_KEY.GROUP);
        groupId = getArguments().getString(Constant.INTENT_KEY.GROUP_ID);
        if (group != null && TextUtils.isEmpty(groupId)) {
            groupId = group.groupId;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_detail, container, false);
    }

    View header;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));
        header = View.inflate(getActivity(), R.layout.header_group_detail, null);
        memberNumText = (TextView) header.findViewById(R.id.group_member_num);
        groupNameText = (TextView) header.findViewById(R.id.group_name);
        joinButton = (Button) header.findViewById(R.id.group_join);
        joinButton.setOnClickListener(this);
        groupDesText = (TextView) header.findViewById(R.id.group_des);
        appNumText = (TextView) header.findViewById(R.id.group_app_num);
        myAppNumText = (TextView) header.findViewById(R.id.group_my_app_in_group_num);

//        getActivity().getContentResolver().registerContentObserver(
//                Downloads.ALL_DOWNLOADS_CONTENT_URI, true, new ContentObserver(null) {
//                    @Override
//                    public void onChange(boolean selfChange, Uri uri) {
//                        super.onChange(selfChange, uri);
//                    }
//                });

        raidoGroup.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));
        listView.addHeaderView(header);
        listView.setOnScrollListener(this);
//        listView.setAdapter(new GroupAppAdapter(getActivity(), generateData()));
        header.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));
        if (group != null) {
            setData(group);
        } else {
            load();
        }
    }

    private void load() {
        setShowProgress(true);
        GroupDetailRequest request = new GroupDetailRequest(new Group(groupId), this);
        request.setShouldCache(false);
        MyVolley.getRequestQueue().add(request);
    }

    private void setActionBar() {
        bar.setTitle(R.string.group);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (visibleItemCount == 0) return;
        if (firstVisibleItem > 0) {
            ViewCompat.setTranslationY(raidoGroup, 0);
            return;
        }

        int top = header.getTop();
        int tabsHeight = raidoGroup.getMeasuredHeight();
        int headerViewHeight = header.getMeasuredHeight();
        int delta = headerViewHeight - tabsHeight;
        ViewCompat.setTranslationY(raidoGroup, Math.max(0, delta + top));
    }


    private List<AppModel> generateData() {
        List<AppModel> appModels = new ArrayList<>();
        AppModel appModel = new AppModel();
        appModel.content = "Mobogenie provides you with the ...";
        appModel.title = "Mobogenie";
        appModel.installedMemberNum = 20;
        appModel.groupMemberNum = 30;
        appModel.packageName = "com.mobogenie";
        appModels.add(appModel);
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        appModels.add(appModel.cloneAppModel());
        return appModels;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
        setShowProgress(false);
    }

    @Override
    public void onResponse(Object response) {
        group = (Group) response;
        setData(group);
        setShowProgress(false);
    }

    Timer timer = new Timer();
    private Handler handler = new Handler();

    public void setData(Group group) {
        setShowProgress(false);
        timer.cancel();
        timer = new Timer();
        final GroupAppAdapter adapter = new GroupAppAdapter(getActivity(), group.appModels);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }, 0, 2000);
        listView.setAdapter(adapter);
        memberNumText.setText(getString(R.string.member_count, group.memeberNum));
        myAppNumText.setText(String.valueOf(group.myApkNumInGroup));
        appNumText.setText(String.valueOf(group.apkNum));
        groupNameText.setText(group.groupName);
        groupDesText.setText(group.description);
        if (SharedPreferencesHelper.getInstance().withModule(R.string.module_group).withKey(R.string.key_my_group_id).get(String.class, "").equals(group.groupId)) {
            joinButton.setBackgroundResource(R.drawable.bg_btn_done_d);
            joinButton.setText(R.string.joined);
            joinButton.setEnabled(false);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.group_join) {
            setShowProgress(true);
            MyVolley.getRequestQueue().add(new JoinGroupRequest(groupId, new NetworkRequestListener<Group>() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setShowProgress(false);
                    Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Group response) {
                    setShowProgress(false);
                    setData(response);
                    joinButton.setBackgroundResource(R.drawable.bg_btn_done_d);
                    joinButton.setText(R.string.joined);
                    joinButton.setEnabled(false);
                }
            }));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timer != null) {
            timer.cancel();
        }
        MyVolley.getRequestQueue().cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                if (request instanceof GroupDetailRequest) {
                    return true;
                }
                return false;
            }
        });
    }

//    @InjectView(R.id.group_app_action_image)
//    protected ImageView groupActionImageView;
//    @InjectView(R.id.group_app_action_text)
//    protected TextView groupActionText;

//    public void onEventMainThread(DownloadFinishedEvent event) {
//        View view = listView.findViewWithTag(event.getUrl());
//        GroupAppAdapter.ViewHolder viewHolder = (GroupAppAdapter.ViewHolder) view.getTag(R.id.tag_id);
//        AppModel appModel = (AppModel) view.getTag(R.id.tag_app);
//
//        view.findViewById(R.id.group_app_action_image);
//        if (event.getUrl().equals(appModel.downloadUrl)) {
//            appModel.setActionImage(viewHolder.groupActionImageView, true);
//            progressBar.setVisibility(View.INVISIBLE);
//            mDownloadImageView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void onEventMainThread(AppInstallEvent event) {
//        if (event.getPackageName().contains(appModel.packageName)) {
//            appModel.setActionImage(mDownloadImageView, true);
//        }
//    }
//
//    public void onEventMainThread(DownloadStartEvent event) {
//        if (event.getUrl().equals(appModel.downloadUrl)) {
////            appModel.setActionImage(mDownloadImageView, true);
//            mDownloadImageView.setVisibility(View.INVISIBLE);
//            progressBar.setVisibility(View.VISIBLE);
//
//        }
//    }


    @Override
    public View getContentView() {
        return getView().findViewById(R.id.cross_fader_content2);
    }

    @Override
    public View getProgressView() {
        return getView().findViewById(R.id.cross_fader_loading);
    }
}
