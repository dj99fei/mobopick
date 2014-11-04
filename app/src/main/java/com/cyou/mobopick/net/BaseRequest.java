package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.cyou.mobopick.util.LogUtils;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
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
            LogUtils.d(TAG, jsonString);
            LogUtils.e(TAG, e);
            return Response.error(new ParseError(e));
        }
    }

    protected T parseResponse(String jsonString) throws JSONException{
        T a = null;
        Gson gson = new Gson();
        return (T) gson.fromJson(jsonString, a.getClass());
    }


    public static class UrlBuilder {
        String base;

        Map<String, String> params;


        public UrlBuilder() {
            this.base = BASE_URL;
        }
        public UrlBuilder(String base) {
            this.base = base;
            params = new HashMap<String, String>();
        }

        public UrlBuilder addParam(String key, String value) {
            params.put(key, value);
            return this;
        }

        public String create() {
            StringBuilder builder = new StringBuilder(base);
            builder.append("?");
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.append(entry.getKey());
                builder.append(entry.getValue());
                builder.append("&");
            }
            builder.deleteCharAt(builder.length() - 1);
            return builder.toString();
        }
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



}
