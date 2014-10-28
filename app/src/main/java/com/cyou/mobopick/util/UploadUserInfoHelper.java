package com.cyou.mobopick.util;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.net.BasicRequest;
import com.cyou.mobopick.net.NetworkRequestListener;
import com.cyou.mobopick.volley.MyVolley;

/**
 * Created by chengfei on 14-10-22.
 */
public class UploadUserInfoHelper implements NetworkRequestListener<Integer> {

    private static final Object LOCK = new Object();
    private static UploadUserInfoHelper helper;
    private UploadUserInfoHelper(){

    }

    public static UploadUserInfoHelper getInstance() {
        if (helper == null) {
            synchronized (LOCK) {
                if (helper == null) {
                    helper = new UploadUserInfoHelper();
                }
            }
        }
        return helper;
    }


    public void upload() {
        Request request = new BasicRequest(this);
        MyVolley.getRequestQueue().add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(Integer response) {
        DeviceInfo.uuid = response;
        SharedPreferencesHelper.getInstance().withKey(R.string.key_uuid).setData(int.class, DeviceInfo.uuid).commit();
    }
}
