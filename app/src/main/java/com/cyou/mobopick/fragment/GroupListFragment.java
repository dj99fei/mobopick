package com.cyou.mobopick.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.adapter.GroupAdapter;
import com.cyou.mobopick.app.CreateGroupActivity;
import com.cyou.mobopick.app.GroupDetailActivity;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.net.GroupListRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.util.Constant;
import com.cyou.mobopick.volley.MyVolley;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/11.
 */
public class GroupListFragment extends BaseFragment implements NetworkRequestListener<List<Group>>, AdapterView.OnItemClickListener {

    @InjectView(R.id.list)
    protected ListView listView;

    public static GroupListFragment newInstance() {
        return new GroupListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_group_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setActionBar();
    }

    private void setActionBar() {

    }

    private List<Group> groups = new ArrayList<>();
    private GroupAdapter adapter;


    @Override

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new GroupAdapter(getActivity(), groups);
        listView.setAdapter(adapter);
//        listView.setAdapter(new GroupAdapter(getActivity(), generateData()));
//        listView.setAdapter(new GroupAdapter(getActivity(), groups));
        listView.setOnItemClickListener(this);
        setShowProgress(true);
        load();
    }

    private void load() {

        MyVolley.getRequestQueue().add(new GroupListRequest(this));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add, menu);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        setShowProgress(false);
        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(List<Group> response) {
        groups.clear();
        groups.addAll(response);
        adapter.notifyDataSetChanged();
        setShowProgress(false);
    }

    private List<Group> generateData() {
        List<Group> groups = new ArrayList<>();
        Group group1 = new Group();
        group1.apkNum = 100;
        group1.description = "We are just different from others !!!!";
        group1.groupName = "Artistic";
        group1.memeberNum = 10;
        group1.myApkNumInGroup = 10;
        group1.matchedRate = 90;
        groups.add(group1);

        Group g2 = group1.cloneGroup();
        g2.matchedRate = 80;

        Group g3 = g2.cloneGroup();
        g3.matchedRate = 60;

        Group g4 = g3.cloneGroup();
        g4.matchedRate = 50;

        Group g5 = g3.cloneGroup();
        g5.matchedRate = 40;

        Group g6 = g3.cloneGroup();
        g6.matchedRate = 30;

        Group g7 = g3.cloneGroup();
        g7.matchedRate = 20;

        groups.add(g2);
        groups.add(g3);
        groups.add(g4);
        groups.add(g5);
        groups.add(g6);
        groups.add(g7);
        return groups;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_group) {
            getActivity().startActivity(new Intent(getActivity(), CreateGroupActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
        Group group = (Group) adapterView.getAdapter().getItem(i - listView.getHeaderViewsCount());
        intent.putExtra(Constant.INTENT_KEY.GROUP_ID, group.groupId);
        getActivity().startActivity(intent);
    }
}
