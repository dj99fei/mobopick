package com.cyou.mobopick.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.providers.DownloadManager;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/12.
 */
public class GroupAppAdapter extends BaseAdapter<AppModel> {

    private DownloadManager downloadManager;
    public GroupAppAdapter(Context context, List<AppModel> data) {
        super(context);
        this.data = data;
        downloadManager = new DownloadManager(MyApplication.getInstance().getContentResolver(), MyApplication.getInstance().getPackageName());
    }

    @Override
    protected View inflate() {
        return View.inflate(context, R.layout.item_group_app, null);
    }

    @Override
    protected BaseViewHolder getViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindViews(BaseViewHolder holder, int position) {
        final ViewHolder viewHolder = (ViewHolder) holder;
        AppModel appModel = getItemAt(position);
        appModel.setActionImage(viewHolder.groupActionImageView, true);
        viewHolder.actionView.setTag(R.id.tag_id, position);
        if (appModel.isDownloading()) {
            viewHolder.actionView.setVisibility(View.INVISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
        }
        viewHolder.actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag(R.id.tag_id);
                AppModel appModel = getItemAt(position);
                appModel.handleAction(downloadManager);
                if (appModel.isDownloading()) {
                    viewHolder.actionView.setVisibility(View.INVISIBLE);
                    viewHolder.progressBar.setVisibility(View.VISIBLE);
                }

            }
        });
        viewHolder.appNameText.setText(appModel.getTitle());
        viewHolder.appNumText.setText(appModel.getInstallRateStr());
        appModel.setActionText(viewHolder.groupActionText, false);
        viewHolder.groupAppDesText.setText(appModel.getTags());
//        if (!TextUtils.isEmpty(appModel.iconUrl)) {
            Picasso.with(context).load(appModel.iconUrl).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).into(viewHolder.iconImage);
//        }
//        viewHolder.itemView.setTag(appModel.downloadUrl);
//        viewHolder.itemView.setTag(R.id.tag_id, viewHolder);
//        viewHolder.itemView.setTag(R.id.tag_app, appModel);
    }

    public class ViewHolder extends BaseViewHolder {

        @InjectView(R.id.group_app_name)
        protected TextView appNameText;
        @InjectView(R.id.group_app_installed_rate)
        protected TextView appNumText;
        @InjectView(R.id.group_app_action)
        protected View actionView;
        @InjectView(R.id.group_app_action_image)
        public ImageView groupActionImageView;
        @InjectView(R.id.group_app_action_text)
        public TextView groupActionText;
        @InjectView(R.id.group_app_des)
        protected TextView groupAppDesText;
        @InjectView(R.id.progress_bar)
        protected ProgressBar progressBar;
        @InjectView(R.id.group_app_image)
        protected ImageView iconImage;

        public ViewHolder(View view) {
            super(view);
        }
    }


}
