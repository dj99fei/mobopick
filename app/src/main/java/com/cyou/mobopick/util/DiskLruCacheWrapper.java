package com.cyou.mobopick.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.okhttp.internal.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by chengfei on 14-10-14.
 */
public class DiskLruCacheWrapper implements ImageLoader.ImageCache {

    private static final String TAG = DiskLruCacheWrapper.class.getSimpleName();
    private DiskLruCache diskLruCache;

    public DiskLruCacheWrapper(DiskLruCache diskLruCache) {
        this.diskLruCache = diskLruCache;
    }

    @Override
    public Bitmap getBitmap(String url) {
        try {
            return BitmapFactory.decodeStream(diskLruCache.get(convertKeyToPath(url)).getInputStream(0));
        } catch (IOException e) {
            LogUtils.e(TAG, e);
        }
        return null;
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, diskLruCache.edit(convertKeyToPath(url)).newOutputStream(0)) ;
        } catch (IOException e) {
            LogUtils.e(TAG, e);
        }
    }

    public String convertKeyToPath(String key) {

        StringBuilder pathBuilder = new StringBuilder(key);
        try {
            pathBuilder.append(File.separator)
                .append(URLEncoder.encode(key, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            LogUtils.e(TAG, e);
        }
        return pathBuilder.toString();
    }
}
