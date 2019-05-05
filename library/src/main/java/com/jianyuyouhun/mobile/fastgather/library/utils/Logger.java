package com.jianyuyouhun.mobile.fastgather.library.utils;

import android.util.Log;

import com.jianyuyouhun.mobile.fastgather.library.app.JApp;

/**
 * 日志打印
 * Created by wangyu on 2018/3/27.
 */

public class Logger {
    public static void i(String tag, String msg) {
        if (JApp.isDebug()) {
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.i(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (JApp.isDebug()) {
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.e(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (JApp.isDebug()) {
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.d(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (JApp.isDebug()) {
            int max_str_length = 2001 - tag.length();
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.w(tag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.w(tag, msg);
        }
    }
}
