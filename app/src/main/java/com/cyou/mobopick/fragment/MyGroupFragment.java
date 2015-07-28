package com.cyou.mobopick.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.net.GroupDetailRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.SharedPreferencesHelper;
import com.cyou.mobopick.volley.MyVolley;

/**
 * Created by chengfei on 14/11/11.
 */
public class MyGroupFragment extends BaseFragment implements NetworkRequestListener<Group> {


    private Group myGroup;

    public static MyGroupFragment newInstance() {
        return new MyGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_group, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));

//        EventBus.getDefault().register(this);

    }

    private void load() {
        String groupId = SharedPreferencesHelper.getInstance().withModule(R.string.module_group).withKey(R.string.key_my_group_id).get(String.class, "");
        setShowProgress(true);
        if (TextUtils.isEmpty(groupId)) {
            setShowProgress(false);
            getChildFragmentManager().beginTransaction().replace(R.id.content_frame, NoGroupFragment.newInstance()).commit();
        } else {
            MyVolley.getRequestQueue().add(new GroupDetailRequest(new Group(groupId), this));
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        setShowProgress(false);
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Group response) {
        this.myGroup = response;
        setShowProgress(false);
        getChildFragmentManager().beginTransaction().replace(R.id.content_frame, MyGroupDetailFragment.newInstance(myGroup)).commit();
    }

//    public void onEventMainThread(AddGroupEvent event) {
//
//        Group group = event.getGroup();
//        if (group != null) {
//            getChildFragmentManager().beginTransaction().replace(R.id.content_frame, MyGroupDetailFragment.newInstance(group)).commit();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        EventBus.getDefault().unregister(this);
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
}
