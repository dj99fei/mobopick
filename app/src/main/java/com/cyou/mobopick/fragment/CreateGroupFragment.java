package com.cyou.mobopick.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.net.CreateGroupRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.AppTheme;
import com.cyou.mobopick.util.Validator;
import com.cyou.mobopick.volley.MyVolley;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/11.
 */
public class CreateGroupFragment extends BaseFragment implements NetworkRequestListener {


    @InjectView(R.id.group_name)
    protected EditText groupNameEdit;
    @InjectView(R.id.group_des)
    protected EditText groupDesEdit;
    @InjectView(R.id.done)
    protected Button doneButton;
    public static CreateGroupFragment newInstance() {
        return new CreateGroupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_group, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBar();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackgroundDrawable(new ColorDrawable(AppTheme.getCurBgColor()));
        doneButton.setOnClickListener(this);
    }

    private void setActionBar() {
//        bar.setTitle(R.string.create_new_group);
        bar.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP | ActionBar.DISPLAY_SHOW_HOME);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.done) {
            String groupName = groupNameEdit.getText().toString();
            String groupDes = groupDesEdit.getText().toString();
            Validator validator = new Validator();
            try {
                validator.notEmpty(groupDes, R.string.group)
                        .notEmpty(groupDes, R.string.group_description);
                Group group = new Group(groupName, groupDes);
                MyVolley.getRequestQueue().add(new CreateGroupRequest(group, this));
            } catch (Validator.ValidateException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        Group group = (Group) response;
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, GroupDetailFragment.newInstance(group)).commit();
    }
}
