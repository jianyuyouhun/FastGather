package com.jianyuyouhun.mobile.fastgather.library.utils.kt

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup


/**
 * kotlin扩展类
 * Created by wangyu on 2018/1/22.
 */
//获取childView列表
fun ViewGroup.getChildViews(): List<View> = (0 until childCount).map { getChildAt(it) }

inline fun <reified T : View> ViewGroup.filterChildViews(): List<T> {
    return getChildViews().filterIsInstance<T>()
}

//获取第一个view，可为空
fun ViewGroup.getFirstView(): View? {
    if (childCount == 0) return null
    return getChildAt(0)
}

//获取最后一个view，可为空
fun ViewGroup.getLastView(): View? {
    if (childCount == 0) return null
    return getChildAt(childCount - 1)
}

fun View.getScreenshotBitmap(): Bitmap {
    val screenshot: Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
    val c = Canvas(screenshot)
    c.translate((-scrollX).toFloat(), (-scrollY).toFloat())
    draw(c)
    return screenshot
}
