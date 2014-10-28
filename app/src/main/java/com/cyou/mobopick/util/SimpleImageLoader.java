package com.cyou.mobopick.util;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.volley.MyVolley;
import com.squareup.okhttp.internal.DiskLruCache;

import java.io.IOException;

/**
 * Created by chengfei on 14-10-14.
 */
public class SimpleImageLoader extends ImageLoader {

    private static final String TAG = SimpleImageLoader.class.getSimpleName();
    private DiskLruCacheWrapper diskLruCacheWrapper;
    /**
     * Constructs a new ImageLoader.
     *
     * @param queue      The RequestQueue to use for making image requests.
     * @param imageCache The cache to use as an L1 cache.
     */
    private SimpleImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
        DiskLruCache diskLruCache = null;
        try {
            diskLruCache = DiskLruCache.open(MyVolley.getCacheDir(MyApplication.getInstance(), "image"), 1, 1, Integer.MAX_VALUE);
        } catch (IOException e) {
            LogUtils.e(TAG, e);
        }
        diskLruCacheWrapper = new DiskLruCacheWrapper(diskLruCache);
    }


}
