package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.domain.Group;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14/11/18.
 */
public class GroupDetailRequest extends CreateGroupRequest {


    public static final String URL = BASE_URL + "group/detail.php";
    public GroupDetailRequest(Group group, NetworkRequestListener listener) {
        super(URL, group, listener);
    }

    public GroupDetailRequest(String url, Group group, NetworkRequestListener listener) {
        super(url,group, listener);
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

    @Override
    protected void saveGroup() {
    }
}
