package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.cyou.mobopick.R;
import com.cyou.mobopick.app.AddGroupActivity;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.util.Constant;

/**
 * Created by chengfei on 14/11/11.
 */
public class MyGroupDetailFragment extends GroupDetailFragment {


    public static MyGroupDetailFragment newInstance(Group group) {
        MyGroupDetailFragment fragment = new MyGroupDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constant.INTENT_KEY.GROUP, group);
        fragment.setArguments(args);
        return fragment;
    }

    public static MyGroupDetailFragment newInstance(String groupId) {
        MyGroupDetailFragment fragment = new MyGroupDetailFragment();
        Bundle args = new Bundle();
        args.putString(Constant.INTENT_KEY.GROUP_ID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_group) {
            getActivity().startActivity(new Intent(getActivity(), AddGroupActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
