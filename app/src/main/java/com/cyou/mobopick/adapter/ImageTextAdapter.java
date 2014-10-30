package com.cyou.mobopick.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * Created by chengfei on 14/10/29.
 */
public class ImageTextAdapter {


    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(itemView);
        }
    }



}
