package com.cyou.mobopick.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by chengfei on 14-9-22.
 */
public abstract  class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> data;
    protected Context context;

    public BaseAdapter(Context context) {
        this.context = context;
    }

    @Override

    public int getCount() {
        return data == null ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data == null ? null : data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public T getItemAt(int position) {
        return getCount() == 0 ? null : data.get(position);
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            convertView = inflate();
            holder = getViewHolder(convertView);
            convertView.setTag(holder);
        }
        holder = (BaseViewHolder) convertView.getTag();
        bindViews(holder, position);
        return convertView;
    }

    protected abstract View inflate();

    protected abstract BaseViewHolder getViewHolder(View itemView);

    public abstract void bindViews(BaseViewHolder holder, int position);

    public static class BaseViewHolder {
        public View itemView;
        public BaseViewHolder(View view) {
            ButterKnife.inject(this, view);
            this.itemView = view;
        }
    }


}
