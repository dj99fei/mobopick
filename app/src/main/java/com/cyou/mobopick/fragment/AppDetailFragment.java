package com.cyou.mobopick.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.net.AppDetailRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.volley.MyVolley;

/**
 * Created by chengfei on 14-10-24.
 */
public class AppDetailFragment extends BaseFragment implements NetworkRequestListener<AppModel> {

    private AppModel appModel;

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
        AppDetailRequest request = new AppDetailRequest(String.valueOf(appModel.id), this);
        MyVolley.getRequestQueue().add(request);
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(AppModel response) {
        appModel = response;
    }
}
