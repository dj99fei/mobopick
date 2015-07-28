package com.cyou.mobopick.net;

import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.cyou.mobopick.R;
import com.cyou.mobopick.bus.AddGroupEvent;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.domain.DeviceInfo;
import com.cyou.mobopick.domain.Group;
import com.cyou.mobopick.util.SharedPreferencesHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * Created by chengfei on 14/11/13.
 */
public class CreateGroupRequest extends BaseRequest<Group> {

    public static String URL = BASE_URL + "group/add.php";
    protected Group group;

    public CreateGroupRequest(Group group, NetworkRequestListener listener) {
        super(URL, null, listener);
        this.group = group;
    }

    public CreateGroupRequest(String url, Group group, NetworkRequestListener listener) {
        super(url, null, listener);
        this.group = group;
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put(Params.GROUP_NAME, group.groupName);
        params.put(Params.GROUP_DESCRIPTION, group.description);
        params.put(Params.UUID, String.valueOf(DeviceInfo.uuid));
        return params;
    }

    @Override
    protected Group parseResponse(String jsonString) throws JSONException {

        JSONObject jsonObject = new JSONObject(jsonString);
        String message = jsonObject.optString("message");
        if (!TextUtils.isEmpty(message)) {
            throw new JSONException(message);
        }
        JSONArray groupApps = jsonObject.optJSONArray("group_apps");
        group.groupName = jsonObject.optString(Params.GROUP_NAME);
        group.description = jsonObject.optString(Params.GROUP_DESCRIPTION);
        group.appModels = new ArrayList<>();
        group.memeberNum = jsonObject.optInt(Params.GROUP_MEM_COUNT);
        group.apkNum = jsonObject.optInt(Params.GROUP_APPS_COUNT);
        group.myApkNumInGroup = jsonObject.optInt(Params.USER_GROUP_APPS_COUNT);
        if (groupApps != null) {
            for (int i = 0; i < groupApps.length(); i++) {
                /**
                 * "apkid": "com.andregal.android.billard",
                 5             "apkpath": "http://download.mgccw.com/mu3/game/20141018/23/1413655591172/com.andregal.android.billard.apk",
                 6             "id": "3493",
                 7             "name": "Roll balls free",
                 8             "recmd": "",
                 9             "tpicpath": "http://download.mgccw.com/mu/wallpaper/2014/8/5/10494665/9d3aa8c3b7e8484593f07fb4a6a05659.png"
                 */
                JSONObject itemObject = groupApps.getJSONObject(i);
                AppModel appModel = new AppModel();
                appModel.packageName = itemObject.optString("apkid");
                appModel.downloadUrl = itemObject.optString("apkpath");
                appModel.title = itemObject.optString("name");
                appModel.tags = itemObject.optString("recmd");
                appModel.iconUrl = itemObject.optString("tpicpath");
                group.appModels.add(appModel);
            }
        }
        setGroupId(jsonObject);
        EventBus.getDefault().post(new AddGroupEvent(group));
        saveGroup();
        return group;
    }

    protected void setGroupId(JSONObject jsonObject) {
        group.groupId = jsonObject.optString(Params.GROUP_ID);
    }

    protected void saveGroup() {
        SharedPreferencesHelper.getInstance().withModule(R.string.module_group).withKey(R.string.key_my_group_id).setData(String.class, group.groupId).commit();
    }
}