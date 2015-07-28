package com.cyou.mobopick.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.cyou.mobopick.util.LogUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by chengfei on 14-10-13.
 */
public abstract class BaseRequest<T> extends JsonRequest<T> {


    public static final String HOST = "http://www.mobo123.com/";
    public static final String BASE_URL = HOST + "static/api/";

    private static final String TAG = BaseRequest.class.getSimpleName();

    public BaseRequest(String url, String requestBody, NetworkRequestListener<T> listener) {
        super(Method.POST, url, requestBody, listener, listener);
    }


    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String jsonString = null;
        try {
            jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            LogUtils.d(TAG, jsonString);
            T result = parseResponse(jsonString);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            String error = TextUtils.isEmpty(e.getMessage()) ? "Error" : e.getMessage();
            return Response.error(new VolleyError(error));
        }
    }

    protected T parseResponse(String jsonString) throws JSONException{
        T a = null;
        Gson gson = new Gson();
        return (T) gson.fromJson(jsonString, a.getClass());
    }

        @Override
    public byte[] getBody() {
        Map<String, String> params = null;
        try {
            params = getParams();
            if (params != null && params.size() > 0) {
                return encodeParameters(params, getParamsEncoding());
            }
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }
        return null;
    }

    /**
     * Converts <code>params</code> into an application/x-www-form-urlencoded encoded string.
     */
    public byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (entry.getValue() != null) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                    encodedParams.append('&');
                }
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }

    @Override
    public String getBodyContentType() {
        return "application/x-www-form-urlencoded; charset=" + getParamsEncoding();
    }


    public static class Params {
        public static final String PAGE_CURRENT = "pagecurrent";
        public static final String PAGESIZE = "pagesize";
        public static final String UUID = "uuid";
        public static final String GROUP_NAME = "group_name";
        public static final String GROUP_ID = "group_id";
        public static final String GROUP_DESCRIPTION = "group_description";
        public static final String GROUP_APPS_COUNT = "group_apps_count";
        public static final String USER_GROUP_APPS_COUNT = "user_group_apps_count";
        public static final String GROUP_MEM_COUNT = "group_users_count";

    }
}
