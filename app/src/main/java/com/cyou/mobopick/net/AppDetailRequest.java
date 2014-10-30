package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.AppModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14-10-24.
 */
public class AppDetailRequest extends BaseRequest<AppModel> {

    public static final String URL = BASE_URL + "/app/detail.php";
    private String id;
    public AppDetailRequest(String id, NetworkRequestListener<AppModel> listener) {
        super(URL, null, listener);
        this.id = id;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return params;
    }

    @Override
    protected AppModel parseResponse(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONObject appObject = jsonObject.getJSONObject("resource");
        Gson gson = new Gson();
        return gson.fromJson(appObject.toString(), AppModel.class);
    }
}
