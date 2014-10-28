package com.cyou.mobopick.net;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.cyou.mobopick.domain.Version;
import com.google.gson.Gson;

import org.json.JSONException;

import java.io.UnsupportedEncodingException;

/**
 * Created by chengfei on 14-10-21.
 */
public class CheckUpdateRequest extends BaseRequest<Version> {


    public static final String URL = BASE_URL + "app/version.php";

    public CheckUpdateRequest(NetworkRequestListener<Version> listener) {
        super(URL, null, listener);
    }

    @Override
    protected Response<Version> parseNetworkResponse(NetworkResponse response) {

        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Gson gson = new Gson();
            Version result = gson.fromJson(jsonString, Version.class);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected Version parseResponse(String jsonString) throws JSONException {
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Version.class);
    }

}
