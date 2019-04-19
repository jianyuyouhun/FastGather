package com.jianyuyouhun.mobile.fastgather;

import com.jianyuyouhun.mobile.okrequester.library.requester.ApiInterface;

public class Api {
    static ApiInterface testApi;

    static {
        testApi = new TestApi();
    }

    public static class TestApi implements ApiInterface {

        @Override
        public String getApiUrl() {
            return "http://api.jianyuyouhun.com/";
        }
    }
}
