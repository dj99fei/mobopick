package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.DeviceInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14-10-22.
 */
public class BasicRequest extends BaseRequest<Integer> {
    public static final String URL = BASE_URL + "user/basic.php";
    public BasicRequest(NetworkRequestListener listener) {
        super(URL, null, listener);
    }

    @Override
    protected Integer parseResponse(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        return json.optInt("uuid");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mac", DeviceInfo.mac);
        params.put("ua", DeviceInfo.ua);
        params.put("imei", DeviceInfo.imei);
        params.put("ua", DeviceInfo.ua);
        params.put("isp", DeviceInfo.isp);
        params.put("version", DeviceInfo.version);
        params.put("ip", DeviceInfo.ip);
        params.put("lbs", DeviceInfo.lbs);
        params.put("apps", DeviceInfo.apps);
        params.put("uuid", String.valueOf(DeviceInfo.uuid));
        return params;
    }
}
