package com.cyou.mobopick.net;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.cyou.mobopick.domain.AppModel;
import com.cyou.mobopick.domain.AppModelListResult;
import com.cyou.mobopick.domain.DeviceInfo;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by chengfei on 14-10-13.
 */
public class AppTimelineRequest extends BaseRequest<AppModelListResult> {


    public static final String URL = BASE_URL + "app/list.php";
    private static final String TAG = AppTimelineRequest.class.getSimpleName();
    private int pageCurrent;
    public AppTimelineRequest(NetworkRequestListener listener, int pageCurrent) {
        super(URL, null, listener);
        this.pageCurrent = pageCurrent;
        setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
    }

    @Override
    protected AppModelListResult parseResponse(String jsonString) throws JSONException {
        Gson gson = new Gson();
        AppModelListResult result = gson.fromJson(jsonString, AppModelListResult.class);
        JSONObject jsonObject = new JSONObject(jsonString);
        result.appModels = Arrays.asList
                (gson.fromJson(jsonObject.getJSONArray("listdata").toString(), AppModel[].class));
        return result;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put(Params.PAGE_CURRENT, String.valueOf(pageCurrent));
        params.put(Params.PAGESIZE, String.valueOf(AppModelListResult.PAGE_SIZE));
        params.put(Params.UUID, String.valueOf(DeviceInfo.uuid));
        return params;
    }

}
