
package com.cyou.mobopick.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.volley.toolbox.ImageLoader;
import com.cyou.mobopick.adapter.BaseAdapter;
import com.cyou.mobopick.util.ResUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class RhythmAdapter extends BaseAdapter
{
    private ResUtil R;
    private LayoutInflater inflater;
    private float itemWidth;
    private Activity mActivity;
//    private List<AppModel> mAppModels;
    private ImageLoader mImageLoader;
    private RhythmLayout mRhythmLayout;
    private DisplayImageOptions options;

    public RhythmAdapter(Activity paramActivity, RhythmLayout paramRhythmLayout
           /* List<AppModel> paramList*/)
    {
        super(paramActivity);
        this.mActivity = paramActivity;
        this.mRhythmLayout = paramRhythmLayout;
//        this.mAppModels = new ArrayList();
//        this.mAppModels.addAll(paramList);
        if (paramActivity != null)
            this.inflater = LayoutInflater.from(paramActivity);
        this.R = ResUtil.getInstance(paramActivity);
        this.options = new DisplayImageOptions.Builder().resetViewBeforeLoading(true)
                .cacheInMemory(true).cacheOnDisc(true).imageScaleType(ImageScaleType.EXACTLY)
                .bitmapConfig(Bitmap.Config.ARGB_8888).displayer(new SimpleBitmapDisplayer())
                .build();
//        this.mImageLoader = ImageLoader.getInstance();
    }

//    public List<AppModel> getAppModels()
//    {
//        return this.mAppModels;
//    }


    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
        RelativeLayout localRelativeLayout1 = (RelativeLayout) this.inflater.inflate(
                this.R.layoutId("adapter_rhythm_icon"), null);
        localRelativeLayout1.setLayoutParams(new RelativeLayout.LayoutParams((int) this.itemWidth,
                this.mActivity.getResources().getDimensionPixelSize(
                        this.R.dimenId("rhythm_item_height"))));
//        localRelativeLayout1.setTranslationY(this.itemWidth);
        RelativeLayout localRelativeLayout2 = (RelativeLayout) localRelativeLayout1.getChildAt(0);
        int i = (int) this.itemWidth
                - 2
                * this.mActivity.getResources().getDimensionPixelSize(
                        this.R.dimenId("rhythm_icon_margin"));
        localRelativeLayout2.setLayoutParams(new RelativeLayout.LayoutParams(i, this.mActivity
                .getResources().getDimensionPixelSize(this.R.dimenId("rhythm_item_height"))
                - 2
                * this.mActivity.getResources().getDimensionPixelSize(
                        this.R.dimenId("rhythm_icon_margin"))));
        ImageView localImageView = (ImageView) localRelativeLayout1.findViewById(this.R
                .viewId("image_icon"));
        int j = i
                - 2
                * this.mActivity.getResources().getDimensionPixelSize(
                        this.R.dimenId("rhythm_icon_margin"));
        ViewGroup.LayoutParams localLayoutParams = localImageView.getLayoutParams();
        localLayoutParams.width = j;
        localLayoutParams.height = j;
        localImageView.setLayoutParams(localLayoutParams);
//        this.mImageLoader.displayImage(localAppModel.getIconUrl(), localImageView, this.options);
        return localRelativeLayout1;
    }

    public void notifyDataSetChanged()
    {
        super.notifyDataSetChanged();
//        this.mRhythmLayout.invalidateData();
    }


    public void setItemWidth(float paramFloat)
    {
        this.itemWidth = paramFloat;
    }

    @Override
    protected View inflate() {
        return null;
    }

    @Override
    protected BaseViewHolder getViewHolder() {
        return null;
    }

    @Override
    public void setViews(BaseViewHolder holder, int position) {
        
    }
}
