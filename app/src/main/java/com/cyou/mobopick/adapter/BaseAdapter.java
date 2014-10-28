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

    private List<T> data;
    private Context context;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder holder = null;
        if (convertView == null) {
            holder = getViewHolder();
            convertView = inflate();
            convertView.setTag(holder);
        }
        holder = (BaseViewHolder) convertView.getTag();
        setViews(holder,position);
        return convertView;
    }

    protected abstract View inflate();

    protected abstract BaseViewHolder getViewHolder();

    public abstract void setViews(BaseViewHolder holder, int position);

    public static class BaseViewHolder {
        public BaseViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


}
