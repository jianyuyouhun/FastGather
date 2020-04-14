package com.jianyuyouhun.mobile.fastgather.library.utils.kt

import android.util.Log
import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp

/**
 * 日志打印
 * Created by wangyu on 2017/7/25.
 */
private inline fun doLog(log: () -> Unit) {
    if (AbstractJApp.isDebug()) log()
}

fun lgE(tag: String, msg: String) = doLog { Log.e(tag, msg) }

fun lgI(tag: String, msg: String) = doLog { Log.i(tag, msg) }

fun lgD(tag: String, msg: String) = doLog { Log.d(tag, msg) }

fun lgW(tag: String, msg: String) = doLog { Log.w(tag, msg) }
