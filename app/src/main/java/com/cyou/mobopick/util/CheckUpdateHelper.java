package com.cyou.mobopick.util;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.cyou.mobopick.MyApplication;
import com.cyou.mobopick.domain.Version;
import com.cyou.mobopick.net.CheckUpdateRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.volley.MyVolley;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chengfei on 14-10-21.
 */
public class CheckUpdateHelper implements NetworkRequestListener<Version> {

    private static final Object LOCK = new Object();
    private static CheckUpdateHelper helper;
    private Context context;

    List<NetworkRequestListener> listeners = new ArrayList<NetworkRequestListener>();
    private CheckUpdateHelper() {
        context = MyApplication.getInstance();
    }

    public static CheckUpdateHelper getInstance() {
        if (helper == null) {
            synchronized (LOCK) {
                if (helper == null) {
                    helper = new CheckUpdateHelper();
                }
            }
        }
        return helper;
    }

    public CheckUpdateHelper withListener(NetworkRequestListener listener) {
        listeners.clear();
        listeners.add(listener);
        return this;

    }

    public CheckUpdateHelper check() {
        Request request = new CheckUpdateRequest(this);
        MyVolley.getRequestQueue().add(request);
        return this;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (listeners != null) {
            for (NetworkRequestListener listener : listeners) {
                listener.onErrorResponse(error);
            }
        }
    }

    @Override
    public void onResponse(Version response) {
        Version.setLastestVersion(response);
        if (listeners != null) {
            for (NetworkRequestListener listener : listeners) {
                listener.onResponse(response);
            }
        }
    }




}
