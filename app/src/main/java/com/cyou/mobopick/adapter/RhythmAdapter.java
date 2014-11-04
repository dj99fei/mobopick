package com.cyou.mobopick.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.util.AnimatorUtils;
import com.cyou.mobopick.util.LogUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.lucasr.twowayview.widget.ListLayoutManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * Created by chengfei on 14-10-8.
 */
public class RhythmAdapter extends RecyclerView.Adapter<RhythmAdapter.ListItemViewHolder> {

    private static final String TAG = RhythmAdapter.class.getSimpleName();
    private List<AppModel> mAppModels;
    private ImageLoader mImageLoader;
    private Context context;
    private DisplayImageOptions options;
    private float itemWidth;
    private float itemHeight;
    private int visibleCount;
    private Resources resource;
    public static final int ITEM_CACHED_SIZE = 10;
    private int currentPosition;
    private ListLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private float selectRatio =  0.35f;
    private float unselectRatio = 0.7f;
    private int [] bgs = new int[]{R.drawable.btn_rhythm_ly, R.drawable.btn_rhythm_gl, R.drawable.btn_rhythm_or,
        R.drawable.btn_rhythm_bl, R.drawable.btn_rhythm_ol, R.drawable.btn_rhythm_o, R.drawable.btn_rhythm_g,
        R.drawable.btn_rhythm_yl};
    public RhythmAdapter(List<AppModel> paramList) {
        this.context = MyApplication.getInstance();
        this.resource = context.getResources();
        this.mAppModels = paramList;
        this.options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new SimpleBitmapDisplayer()).build();
    }


    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.item_rhythm, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mAppModels.size();
    }


    @Override
    public void onBindViewHolder(final ListItemViewHolder viewHolder, final int position) {
        final RelativeLayout itemLayout = (RelativeLayout) viewHolder.itemView;
        final LinearLayout iconLayout = (LinearLayout) itemLayout.getChildAt(0);
//        RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, 120);
//        int itemMargin = resource.getDimensionPixelOffset(R.dimen.rhythm_item_padding);
//        itemLayoutParams.leftMargin = itemMargin;
//        itemLayoutParams.rightMargin = itemMargin;
//        itemLayout.setLayoutParams(itemLayoutParams);
        if (itemWidth <= 0) {
            itemLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    itemHeight = itemLayout.getMeasuredHeight();
                    itemLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                    itemWidth = itemLayout.getMeasuredWidth();
                    visibleCount = (int) (DeviceInfo.screenWidth / itemWidth + 1);
                    layout(iconLayout, viewHolder, position);
                    return true;
                }
            });
        } else {
            layout(iconLayout, viewHolder, position);
        }


    }

    int delta = (int) (itemHeight * 0.12f);

    private List<Target> targets = new ArrayList<Target>();

    private void layout(LinearLayout iconLayout, ListItemViewHolder viewHolder, int position) {
//        final RelativeLayout itemLayout = (RelativeLayout) viewHolder.itemView;
//        RelativeLayout.LayoutParams itemLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        int itemMargin = resource.getDimensionPixelOffset(R.dimen.rhythm_item_padding);
//        itemLayoutParams.leftMargin = itemMargin;
//        itemLayoutParams.rightMargin = itemMargin;
//        itemLayout.setLayoutParams(itemLayoutParams);

//        int newWidth = (int) itemWidth - 2 * resource.getDimensionPixelSize(R.dimen.rhythm_icon_padding);
//        iconLayout.setLayoutParams(new RelativeLayout.LayoutParams(newWidth, resource.getDimensionPixelSize(R.dimen.rhythm_item_height) - 2 * resource.getDimensionPixelSize(R.dimen.rhythm_icon_padding)));
//        int j = newWidth - 2 * resource.getDimensionPixelSize(R.dimen.rhythm_icon_padding);
//        ViewGroup.LayoutParams localLayoutParams = viewHolder.icon.getLayoutParams();
//        localLayoutParams.width = j;
//        localLayoutParams.height = j;
//        viewHolder.icon.setLayoutParams(localLayoutParams);
        LogUtils.d(TAG, "layout position: %s", position);
        if (position == currentPosition) {
            AnimatorUtils.showUpAndDownBounce(viewHolder.itemView, (int) (itemHeight * unselectRatio), (int)( Math.max(Math.abs(currentPosition - position) * delta + itemHeight * selectRatio, itemHeight * selectRatio)), (int) (itemHeight * selectRatio), 1000, 0);

        } else {
            AnimatorUtils.showUpAndDownBounce(viewHolder.itemView, (int) (itemHeight * unselectRatio), (int)( Math.max(Math.abs(currentPosition - position) * delta + itemHeight * selectRatio, itemHeight * selectRatio)),(int) (itemHeight * unselectRatio), 1000, 0);
        }
//        MyTarget myTarget = new MyTarget(viewHolder.itemView, position);
//        targets.add(myTarget);
//        Picasso.with(context).load(bgs[position % bgs.length]).into(myTarget);
        iconLayout.setBackgroundResource(bgs[position % bgs.length]);
        Picasso.with(context).load(mAppModels.get(position).getIconUrl()).into(viewHolder.icon);
    }



    public void setItemWidth(float itemWidth) {
        this.itemWidth = itemWidth;
    }

    public final static class ListItemViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.image_icon)
        ImageView icon;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public void setCurrentPosition(final int currentPosition, LinearLayoutManager layoutManager, final RecyclerView recyclerView) {

        int base = -1;
        try {
            int first = layoutManager.findFirstCompletelyVisibleItemPosition();
            int offset = 0;
            if ( currentPosition > first) {
                offset = 2;
            } else if (currentPosition < first){
                offset = 2;
            }
            base = (int) ((currentPosition - (first  + offset)) * itemWidth);
        } catch (Exception e) {
        }
        if (base == -1) {
            base = (int) (itemWidth * (currentPosition - this.currentPosition));
        }
//        recyclerView.smoothScrollToPosition(Math.max(0, currentPosition - 3));
//        recyclerView.smoothScrollBy((int) (itemWidth * (currentPosition - this.currentPosition)), 0);
        recyclerView.smoothScrollBy(base, 0);
        this.currentPosition = currentPosition;
//        recyclerView.smoothScrollToPosition(currentPosition - currentPosition % (layoutManager.findLastVisibleItemPosition() - layoutManager.findFirstVisibleItemPosition()));
        delta = (int) (itemHeight * 0.12f);
        notifyDataSetChanged();
//        recyclerView.setAdapter(this);
    }


    private class MyTarget implements Target {

        private View bgView;
        private int position;

        private MyTarget(View bgView, int position) {
            this.bgView = bgView;
            this.position = position;
        }

        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            if (bgView != null) {
                bgView.setBackgroundDrawable(new BitmapDrawable(bitmap));
            }
            targets.remove(this);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            if (bgView != null) {
                bgView.setBackgroundResource(bgs[position % bgs.length]);
            }
            targets.remove(this);
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    }

}
