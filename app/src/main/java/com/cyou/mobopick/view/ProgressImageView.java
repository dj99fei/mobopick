package com.cyou.mobopick.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.cyou.mobopick.R;

/**
 * Created by chengfei on 14/11/11.
 */
public class ProgressImageView extends ImageView {

    private static Bitmap colorBitmap;
    private static Bitmap grayBitmap;



    private int progress;

    public ProgressImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (colorBitmap == null || colorBitmap.isRecycled()) {
            colorBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android_green);
        }
//        if (grayBitmap == null || grayBitmap.isRecycled()) {
//            grayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_android_gray);
//        }
        setImageBitmap(Bitmap.createBitmap(colorBitmap, 0, (int) (0.7f * colorBitmap.getHeight()), colorBitmap.getWidth(), (int) (0.3f * colorBitmap.getHeight())));
    }

    public void setProgress(int progress) {
        this.progress = progress;
        setImageBitmap(makeTransparent(colorBitmap));
    }

    public Bitmap makeTransparent(Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap transformedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        int[] allpixels = new int[transformedBitmap.getHeight() * transformedBitmap.getWidth()];
        source.getPixels(allpixels, 0, transformedBitmap.getWidth(), 0, 0, transformedBitmap.getWidth(), transformedBitmap.getHeight());
        transformedBitmap.setPixels(allpixels, 0, width, 0, 0, width, height);
        int toHeight = (int) (((100 - progress) / 100.0f) * transformedBitmap.getHeight());
        for (int i = 0; i < toHeight * transformedBitmap.getWidth(); i++) {
            allpixels[i] = Color.alpha(Color.TRANSPARENT);
        }

        transformedBitmap.setPixels(allpixels, 0, transformedBitmap.getWidth(), 0, 0, transformedBitmap.getWidth(), transformedBitmap.getHeight());
        return transformedBitmap;
    }
}
