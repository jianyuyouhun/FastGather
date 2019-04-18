package com.jianyuyouhun.mobile.fastgather.library.utils.kt

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.view.View
import com.jianyuyouhun.mobile.fastgather.library.app.JApp

/**
 * resource扩展类
 * Created by wangyu on 2018/11/2.
 */

@ColorInt
fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(JApp.getInstance(), colorId)

fun getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(JApp.getInstance(), drawableId)

fun View.getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(context, colorId)

fun View.getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(context, drawableId)
