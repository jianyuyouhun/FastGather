package com.jianyuyouhun.mobile.fastgather;


import com.jianyuyouhun.mobile.fastgather.library.app.BaseManager;
import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp;
import com.jianyuyouhun.mobile.fastgather.library.utils.FileUtils;
import com.jianyuyouhun.mobile.okrequester.library.HttpHolder;

import java.util.List;

import androidx.annotation.NonNull;

public class MyApplication extends AbstractJApp {
    @Override
    protected void initManagers(@NonNull List<BaseManager> managerList) {

    }

    @Override
    protected boolean setDebugMode() {
        return BuildConfig.DEBUG;
    }

    @Override
    protected void initDependencies() {
        super.initDependencies();
        HttpHolder.isDebug = isDebug();
        FileUtils.APPLICATION_ID = BuildConfig.APPLICATION_ID;
    }
}
