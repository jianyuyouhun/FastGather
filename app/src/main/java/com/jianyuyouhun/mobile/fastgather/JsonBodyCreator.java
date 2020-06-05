package com.jianyuyouhun.mobile.fastgather;

import com.jianyuyouhun.mobile.fastgather.library.utils.json.JsonUtil;
import com.jianyuyouhun.mobile.okrequester.library.requester.creator.BodyCreatorAction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JsonBodyCreator implements BodyCreatorAction {
    @Override
    public RequestBody onCreate(Map<String, Object> params) {
        JSONObject jsonObject = new JSONObject();
        for (String key : params.keySet()) {
            try {
                Object value = params.get(key);
                if (value == null) {
                    continue;
                }
                if (value instanceof List) {
                    List list = (List) value;
                    jsonObject.put(key, JsonUtil.toJSONArray(list));
                } else if (value instanceof Integer
                        || value instanceof String
                        || value instanceof Double
                        || value instanceof Boolean
                        || value instanceof Float
                        || value instanceof Byte
                        || value instanceof Long
                        || value instanceof Short
                        || value instanceof Character) {
                    jsonObject.put(key, value);
                } else {
                    jsonObject.put(key, JsonUtil.toJSONObject(value));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return FormBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
    }
}
