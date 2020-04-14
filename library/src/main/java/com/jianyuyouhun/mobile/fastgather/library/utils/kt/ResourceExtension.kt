package com.jianyuyouhun.mobile.fastgather.library.utils.kt

import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.jianyuyouhun.mobile.fastgather.library.app.AbstractJApp

/**
 * resource扩展类
 * Created by wangyu on 2018/11/2.
 */

@ColorInt
fun getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(AbstractJApp.getInstance(), colorId)

fun getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(AbstractJApp.getInstance(), drawableId)

fun View.getColor(@ColorRes colorId: Int): Int = ContextCompat.getColor(context, colorId)

fun View.getDrawable(@DrawableRes drawableId: Int): Drawable? = ContextCompat.getDrawable(context, drawableId)
