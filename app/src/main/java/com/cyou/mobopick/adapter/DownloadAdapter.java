package com.cyou.mobopick.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.providers.DownloadManager;
import com.cyou.mobopick.providers.downloads.ui.DownloadItem;
import com.cyou.mobopick.util.LogUtils;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chengfei on 14/11/5.
 */
public class DownloadAdapter extends CursorAdapter {

    private static final String TAG = DownloadAdapter.class.getSimpleName();
    private Context context;
    private Cursor cursor;
    private Resources resources;
    private final int titleColumnId;
    private final int statusColumnId;
    private final int totalBytesColumnId;
    private final int currentBytesColumnId;
    private final int mediaTypeColumnId;
    private final int dateColumnId;
    private final int idColumnId;
    private final int descriptionColumnId;
    private final int packageColumnId;
    private final int localUriColumnId;

    private boolean editMode;
    public DownloadAdapter(Context context, Cursor c) {
        super(context, c);
        this.cursor = c;
        idColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_ID);
        titleColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_TITLE);
        statusColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_STATUS);
        totalBytesColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
        currentBytesColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
        mediaTypeColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE);
        dateColumnId = cursor
                .getColumnIndexOrThrow(DownloadManager.COLUMN_LAST_MODIFIED_TIMESTAMP);
        descriptionColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_DESCRIPTION);
        packageColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_PACKAGE);
        localUriColumnId = cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_LOCAL_URI);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        DownloadItem view = (DownloadItem) LayoutInflater.from(context).inflate(R.layout.item_download, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        view.setSelectListener(listener);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.titleText.setText(cursor.getString(titleColumnId));
        long id = cursor.getLong(idColumnId);
        view.setTag(R.id.tag_id, Long.valueOf(id));
        long currentBytes = cursor.getLong(currentBytesColumnId);
        long totalBytes = cursor.getLong(totalBytesColumnId);

        String localUriStr = cursor.getString(localUriColumnId);
        view.setTag(R.id.tag_local, localUriStr);
        view.setTag(R.id.tag_mime, cursor.getString(mediaTypeColumnId));
        String packageName = cursor.getString(packageColumnId);
        view.setTag(R.id.tag_package_name, packageName);
        int percent = (int) (currentBytes * 100 / totalBytes);
        viewHolder.percentText.setText(String.valueOf(percent + "%"));
        viewHolder.sizeText.setText(getSmartSize(totalBytes));
        int status = cursor.getInt(statusColumnId);
        view.setTag(R.id.tag_status, status);
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            LogUtils.d(TAG, "key : %s  value : %s", cursor.getColumnName(i), cursor.getString(i));
        }

        if (status == DownloadManager.STATUS_FAILED
                || status == DownloadManager.STATUS_SUCCESSFUL) {
            //complete
            viewHolder.percentText.setVisibility(View.GONE);
            viewHolder.actionText.setText(R.string.install);
            viewHolder.speedText.setVisibility(View.GONE);
            viewHolder.progressBar.setVisibility(View.GONE);
        } else {
            viewHolder.percentText.setVisibility(View.VISIBLE);
            viewHolder.actionText.setVisibility(View.VISIBLE);
            viewHolder.progressBar.setVisibility(View.VISIBLE);
            viewHolder.speedText.setVisibility(View.VISIBLE);
        }

        if (DeviceInfo.apps.contains(packageName)) {
            viewHolder.actionImage.setImageResource(R.drawable.ic_open);
            viewHolder.actionText.setText(R.string.open);
        } else if (status == DownloadManager.STATUS_PAUSED) {
            viewHolder.actionImage.setImageResource(R.drawable.ic_start);
            viewHolder.actionText.setText(R.string.resume);
            viewHolder.speedText.setVisibility(View.GONE);
        } else if (status == DownloadManager.STATUS_RUNNING) {
            viewHolder.actionImage.setImageResource(R.drawable.ic_pause);
            viewHolder.actionText.setText(R.string.pause);
            viewHolder.speedText.setVisibility(View.VISIBLE);
        } else if (status == DownloadManager.STATUS_SUCCESSFUL) {
            viewHolder.actionImage.setImageResource(R.drawable.ic_install);
            viewHolder.actionText.setText(R.string.install);
        } else if (status == DownloadManager.STATUS_FAILED) {
            viewHolder.actionImage.setImageResource(R.drawable.ic_error);
            viewHolder.actionText.setText(R.string.error);
        }
        if (editMode) {
            viewHolder.actionlayout.setVisibility(View.INVISIBLE);
            viewHolder.checkLayout.setVisibility(View.VISIBLE);
        } else {
            viewHolder.actionlayout.setVisibility(View.VISIBLE);
            viewHolder.checkLayout.setVisibility(View.INVISIBLE);
        }

        boolean indeterminate = status == DownloadManager.STATUS_PENDING;
        viewHolder.progressBar.setIndeterminate(indeterminate);
        viewHolder.progressBar.setProgress(percent);
        viewHolder.checkBox.setOnCheckedChangeListener(new OnCheckedChangedListener(id));
        if (listener != null) {
            viewHolder.checkBox.setChecked(listener.isDownloadSelected(id));
        }
        viewHolder.currentBytes = currentBytes;
        viewHolder.speedText.setText(getSpeed(viewHolder));
        Picasso.with(context).load(cursor.getString(descriptionColumnId)).placeholder(R.drawable.ic_app_default).into(viewHolder.iconImage);
    }

    protected class ViewHolder {
        @InjectView(R.id.item_image)
        public ImageView iconImage;
        @InjectView(R.id.item_action_image)
        public ImageView actionImage;
        @InjectView(R.id.item_download_title)
        public TextView titleText;
        @InjectView(R.id.item_action_text)
        public TextView actionText;
        @InjectView(R.id.item_download_percent)
        public TextView percentText;
        @InjectView(R.id.item_download_size)
        public TextView sizeText;
        @InjectView(R.id.item_download_speed)
        public TextView speedText;
        public View itemView;
        @InjectView(R.id.item_download_progress)
        public ProgressBar progressBar;
        @InjectView(R.id.item_action)
        public View actionlayout;
        @InjectView(R.id.item_download_check_layout)
        public View checkLayout;
        @InjectView(R.id.item_download_check)
        public CheckBox checkBox;

        public long currentBytes;
        public long lastNotify;
        public long notifyedBytes;
        public String speed = "0.0 Kb/s";

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            ButterKnife.inject(this, itemView);
        }
    }

    private String getSmartSize(long size) {
        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder result = new StringBuilder();
        if (size < 1024 * 1024 * 1024) {
            return result.append(df.format(Float.valueOf(size) / 1024 / 1024)).append(" M").toString();
        }
        return result.append(df.format(Float.valueOf(size) / 1024 / 1024 / 1024)).append(" G").toString();
    }

    public boolean isEditMode() {
        return editMode;
    }

    public void setEditMode(boolean editMode) {
        this.editMode = editMode;
        notifyDataSetChanged();
    }

    public void selectAll() {

    }

    public class OnCheckedChangedListener implements CheckBox.OnCheckedChangeListener {

        private long id;

        public OnCheckedChangedListener(long id) {
            this.id = id;
        }

        @Override
        public void onCheckedChanged(CompoundButton checkBox, boolean checked) {
            if (listener != null) {
                listener.onDownloadSelectionChanged(id, checked);
            }
        }
    }

    private DownloadItem.DownloadSelectListener listener;

    public void setListener(DownloadItem.DownloadSelectListener listener) {
        this.listener = listener;
    }

    public static interface OnItemCheckedListener {

        public void onCheckedChanged(CompoundButton checkBox, boolean checked, long id);

        public boolean isChecked(long id);
    }



    public String getSpeed(ViewHolder holder) {
        long currentBytes = holder.currentBytes;
        long now = System.currentTimeMillis();
        long deltaTime = Math.max((now - holder.lastNotify) / 1024, 1);
        long deltaSize = (currentBytes - holder.notifyedBytes) / 1000;
        if (deltaSize < 2) {
            return holder.speed;
        }
        float speed  = Float.valueOf(deltaSize) / deltaTime;
        DecimalFormat format = new DecimalFormat("0.0");
        holder.lastNotify = now;
        holder.notifyedBytes = currentBytes;
        holder.speed = format.format(speed) + " Kb/s";
        return holder.speed;
    }
}
