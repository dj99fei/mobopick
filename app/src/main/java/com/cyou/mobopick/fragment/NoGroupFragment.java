package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.AddGroupActivity;
import com.cyou.mobopick.domain.DeviceInfo;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/11.
 */
public class NoGroupFragment extends BaseFragment{

    @InjectView(R.id.no_group_des)
    protected TextView noGroupDesText;
    @InjectView(R.id.action_add_group)
    protected View addGroupView;
    public static NoGroupFragment newInstance() {
        return new NoGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_no_group, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        noGroupDesText.setText(Html.fromHtml(String.format(getString(R.string.no_group_des, DeviceInfo.appCount))));
        addGroupView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.action_add_group) {
            getActivity().startActivity(new Intent(getActivity(), AddGroupActivity.class));
        }
    }
}
