package com.jianyuyouhun.mobile.fastgather;

import android.support.annotation.NonNull;

import com.jianyuyouhun.mobile.fastgather.library.app.BaseManager;
import com.jianyuyouhun.mobile.fastgather.library.app.JApp;
import com.jianyuyouhun.mobile.fastgather.library.utils.FileUtils;
import com.jianyuyouhun.mobile.okrequester.library.HttpHolder;

import java.util.List;

public class MyApplication extends JApp {
    @Override
    protected void initManagers(@NonNull List<BaseManager> managerList) {

    }

    @Override
    protected boolean setDebugMode() {
        HttpHolder.isDebug = BuildConfig.DEBUG;
        FileUtils.APPLICATION_ID = BuildConfig.APPLICATION_ID;
        return BuildConfig.DEBUG;
    }
}
