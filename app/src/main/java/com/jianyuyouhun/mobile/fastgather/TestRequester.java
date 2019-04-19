package com.jianyuyouhun.mobile.fastgather;

import android.support.annotation.NonNull;

import com.jianyuyouhun.mobile.okrequester.library.listener.OnHttpResultListener;
import com.jianyuyouhun.mobile.okrequester.library.requester.ApiInterface;
import com.jianyuyouhun.mobile.okrequester.library.requester.BaseStringRequester;

import java.util.Map;

public class TestRequester extends BaseStringRequester {
    public TestRequester(@NonNull OnHttpResultListener<String> listener) {
        super(listener);
    }

    @Override
    protected void onPutParams(@NonNull Map<String, Object> params) {

    }

    @NonNull
    @Override
    protected ApiInterface getApi() {
        return Api.testApi;
    }
}
