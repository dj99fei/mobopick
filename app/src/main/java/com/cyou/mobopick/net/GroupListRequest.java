package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.domain.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chengfei on 14/11/19.
 */
public class GroupListRequest extends BaseRequest<List<Group>> {

    public static final String URL = BASE_URL + "group/list.php";
    public GroupListRequest( NetworkRequestListener<List<Group>> listener) {
        super(URL, null, listener);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(Params.UUID, String.valueOf(DeviceInfo.uuid));
//        params.put(Params.)
        return params;
    }

    @Override
    protected List<Group> parseResponse(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("group_apps");
        List<Group> groups = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                Group group = new Group();
                JSONObject itemObject = jsonArray.getJSONObject(i);
                group.description = itemObject.optString(Params.GROUP_DESCRIPTION);
                group.groupName = itemObject.optString(Params.GROUP_NAME);
                group.groupId = itemObject.optString("id");
                group.myApkNumInGroup = Integer.valueOf(itemObject.optString("myapp"));
                groups.add(group);
            }
        }
        return groups;
    }
}
