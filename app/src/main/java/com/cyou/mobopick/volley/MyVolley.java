package com.cyou.mobopick.volley;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NoCache;
import com.cyou.mobopick.R;
import com.navercorp.volleyextensions.cache.universalimageloader.disc.impl.UniversalUnlimitedDiscCache;

import java.io.File;

/**
 * Created by chengfei on 14-9-22.
 */
public class MyVolley {
    private static final int DEFAULT_CACHE_SIZE = 10 * 1024 * 1024;
    private static final long DEFAULT_MAX_AGE = Integer.MAX_VALUE;
    private static RequestQueue requestQueue;
    private static ImageLoader imageLoader;

    public static void init(Context context) {
        if (context == null)
            throw new NullPointerException("context must not be null.");

        Cache diskCache = getDefaultDiskCache(context);
//        ImageLoader.ImageCache memoryCache = getDefaultImageCache(context);
        requestQueue = new RequestQueue(diskCache, new BasicNetwork(
                new HurlStack()));

//        imageLoader = new ImageLoader(requestQueue, memoryCache);

        requestQueue.start();
    }


    public static RequestQueue getRequestQueue() {
        if (requestQueue == null)
            throw new IllegalStateException("RequestQueue is not initialized.");
        return requestQueue;
    }

//    public static ImageLoader getImageLoader() {
//        if (imageLoader == null)
//            throw new IllegalStateException("ImageLoader is not initialized.");
//        return imageLoader;
//    }
//
//    private static ImageLoader.ImageCache getDefaultImageCache(Context context) {
//        return new UniversalUnlimitedDiscCache(getCacheDir(context, "images"));
//    }

    private static Cache getDefaultDiskCache(Context context) {
        File cacheDir = getCacheDir(context, "data");
        if (cacheDir == null) {
            return new NoCache();
        }
        return new UniversalUnlimitedDiscCache(cacheDir);
    }

    public static File getCacheDir(Context context, String sub) {
        File file = new File(context.getExternalCacheDir() + File.separator + context.getString(R.string.app_name)
                + sub);
        return file;
    }

}
