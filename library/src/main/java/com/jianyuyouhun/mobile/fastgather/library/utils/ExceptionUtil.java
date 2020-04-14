package com.jianyuyouhun.mobile.fastgather.library.utils;


import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp;

/**
 * 异常抛出
 * Created by wangyu on 2018/3/30.
 */

public class ExceptionUtil {

    public static void throwException(Exception exception) {
        if (AbstractJApp.isDebug()) {
            throw new RuntimeException(exception);
        }
    }
}
