package com.jianyuyouhun.mobile.fastgather.library.view.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.JavascriptInterface;

import com.jianyuyouhun.mobile.fastgather.library.app.BaseActivity;
import com.jianyuyouhun.mobile.fastgather.library.utils.ExceptionUtil;
import com.jianyuyouhun.mobile.fastgather.library.utils.Logger;
import com.jianyuyouhun.mobile.fastgather.library.utils.ToastUtil;


/**
 * js交互基类，使用：window.JavaScriptInterface.logI('日志')
 * Created by wangyu on 2017/12/14.
 */

public abstract class JsInterface {

    private Context context;
    private static String TAG;

    public JsInterface(Context context) {
        this.context = context;
        TAG = this.getClass().getSimpleName();
    }

    protected Context getContext() {
        return context;
    }

    /**
     * 获取手机系统版本
     * @return
     */
    @JavascriptInterface
    public final int getMobileVersion() {
        return Build.VERSION.SDK_INT;
    }

    @JavascriptInterface
    public final String getMobilePlatform() {
        return "android";
    }

    /**
     * 弹出原生进度框
     * @param msg
     */
    @JavascriptInterface
    public void showProgress(String msg) {
        ((BaseActivity) getContext()).showProgressDialog(msg);
    }

    /**
     * 关闭原生进度框
     */
    @JavascriptInterface
    public void dismissProgress() {
        ((BaseActivity) getContext()).dismissProgressDialog();
    }

    /**
     * 弹出toast
     * @param msg toast内容
     */
    @JavascriptInterface
    public void showToast(String msg) {
        ToastUtil.showToast(msg);
    }

    /**
     * 抛出异常
     * @param msg 异常信息
     */
    @JavascriptInterface
    public void throwException(String msg) {
        ExceptionUtil.throwException(new RuntimeException(msg));
    }

    /**
     * 打印日志 info级别
     * @param msg 日志内容
     */
    @JavascriptInterface
    public void logI(String msg) {
        Logger.i(TAG, msg);
    }

    /**
     * 关闭页面
     */
    @JavascriptInterface
    public void finish() {
        ((Activity) context).finish();
    }
}
