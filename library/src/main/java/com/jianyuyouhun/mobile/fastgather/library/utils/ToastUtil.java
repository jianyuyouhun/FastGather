package com.jianyuyouhun.mobile.fastgather.library.utils;

import android.widget.Toast;

import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp;

import androidx.annotation.StringRes;


/**
 * toast工具类
 * Created by wangyu on 2018/4/4.
 */

public class ToastUtil {

    private static Toast mLastToast;
    public static void showToast(@StringRes int msgId) {
        showToast(AbstractJApp.getInstance().getString(msgId));
    }

    public static void showToast(String msg) {
        cancelToast();
        mLastToast = Toast.makeText(AbstractJApp.getInstance(), msg, Toast.LENGTH_SHORT);
        mLastToast.show();
    }

    private static void cancelToast() {
        if (mLastToast != null) {
            mLastToast.cancel();
        }
    }
}
