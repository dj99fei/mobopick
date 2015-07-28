package com.cyou.mobopick.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.view.ProgressImageView;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/11.
 */
public class GroupAdapter extends BaseAdapter<Group> {

    public GroupAdapter(Context context, List<Group> data) {
        super(context);
        this.data = data;
    }

    @Override
    protected View inflate() {
        return View.inflate(context, R.layout.item_group, null);
    }

    @Override
    protected BaseViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViews(BaseViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        Group group = getItemAt(position);
        viewHolder.appNumText.setText(String.valueOf(group.apkNum));
        viewHolder.desText.setText(group.description);
        viewHolder.memberNumText.setText(String.valueOf(group.memeberNum));
        viewHolder.myAppInGroupNumText.setText(String.valueOf(group.myApkNumInGroup));
        viewHolder.imageView.setProgress(group.matchedRate);
        viewHolder.nameText.setText(group.groupName);
    }

    protected static class ViewHolder extends BaseViewHolder {

        @InjectView(R.id.group_app_num)
        protected TextView appNumText;

        @InjectView(R.id.group_des)
        protected TextView desText;

        @InjectView(R.id.group_member_num)
        protected TextView memberNumText;

        @InjectView(R.id.group_image)
        protected ProgressImageView imageView;

        @InjectView(R.id.group_my_app_in_group_num)
        protected TextView myAppInGroupNumText;

        @InjectView(R.id.group_name)
        protected TextView nameText;

        public ViewHolder(View view) {

            super(view);
        }
    }


}
