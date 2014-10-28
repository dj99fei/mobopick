package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.AppModel;

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



//    @Override
//    protected AppModel parseResponse(String jsonString) throws JSONException {
//        Gson gson = new Gson();
//        return gson.fromJson(jsonString, AppModel.class);
//    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        return params;
    }
}
