package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.Group;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14/11/11.
 */
public class GetMyGroupRequest extends BaseRequest<Group> {

    public static final String URL = BASE_URL + "group/add.php";

    public GetMyGroupRequest(NetworkRequestListener<Group> listener) {
        super(URL, null, listener);
    }

    @Override
    protected Group parseResponse(String jsonString) throws JSONException {
        return super.parseResponse(jsonString);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        return params;
    }
}
