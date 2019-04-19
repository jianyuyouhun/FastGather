package com.jianyuyouhun.mobile.fastgather;

import com.jianyuyouhun.mobile.okrequester.library.listener.OnJsonResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class OnHttpListener<T> extends OnJsonResultListener<T> {
    @Override
    protected int initSuccessCode() {
        return 0;
    }

    @Override
    protected int initFailedCode() {
        return -1;
    }

    @Override
    public JSONObject parseJsonObject(String in) throws JSONException {
        return super.parseJsonObject(in);
    }
}
