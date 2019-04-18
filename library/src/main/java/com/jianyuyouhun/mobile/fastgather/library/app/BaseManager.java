package com.jianyuyouhun.mobile.fastgather.library.app;

import android.app.Application;
import android.content.Context;

/**
 * 控制器基类
 * Created by wangyu on 2017/12/7.
 */

public abstract class BaseManager {

    protected String TAG;

    protected Application application;

    public void onManagerCreate(Application application) {
        TAG = this.getClass().getSimpleName();
        this.application = application;
    }

    public void onAllManagerCreate() {

    }

    protected  <M extends BaseManager> M getManager(Class<M> cls) {
        return JApp.getInstance().getManager(cls);
    }

    protected Context getApplicationContext() {
        return application.getApplicationContext();
    }

    @Deprecated
    protected Context getBaseContext() {
        return application.getBaseContext();
    }

    protected Context getContext() { return application.getBaseContext(); }
}
