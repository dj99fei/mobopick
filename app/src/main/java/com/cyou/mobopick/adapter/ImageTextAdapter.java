package com.cyou.mobopick.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.ImageText;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by chengfei on 14/10/29.
 */
public class ImageTextAdapter extends RecyclerView.Adapter<ImageTextAdapter.ViewHolder> implements View.OnClickListener {

    private List<ImageText> imageTexts;

    public ImageTextAdapter(List<ImageText> iamgeTexts) {
        this.imageTexts = iamgeTexts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_image, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
       ImageText imageText = imageTexts.get(i);
       Picasso.with(MyApplication.getInstance()).load(imageText.getImageUrl()).into(viewHolder.imageView);

       viewHolder.imageView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return imageTexts != null ? imageTexts.size() : 0;
    }

    @Override
    public void onClick(View view) {

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.item_image)
        ImageView imageView;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

}
