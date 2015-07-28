package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.domain.Group;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14/11/13.
 */
public class JoinGroupRequest extends CreateGroupRequest {

    public static final String URL = BASE_URL + "group/join.php";

    public JoinGroupRequest(String groupId, NetworkRequestListener listener) {
        super(URL, new Group(groupId), listener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(Params.UUID, String.valueOf(DeviceInfo.uuid));
        params.put(Params.GROUP_ID, group.groupId);
        return params;
    }

    @Override
    protected void setGroupId(JSONObject jsonObject) {
    }
}
