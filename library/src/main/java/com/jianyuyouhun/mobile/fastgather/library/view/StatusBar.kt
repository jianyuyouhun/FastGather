package com.jianyuyouhun.mobile.fastgather.library.view

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.ImageView
import com.jianyuyouhun.mobile.fastgather.library.R
import com.jianyuyouhun.mobile.fastgather.library.utils.AppUtils

/**
 * 状态栏占位控件,用于适配不同版本的状态栏高度问题，使用方式 {
 *          <com.jianyuyouhun.mobile.fastgather.library.view.StatusBar
 *                 android:layout_width="match_parent"
 *                 android:layout_height="wrap_content"
 *                 app:targetVersion="21"                      目标版本（19——27）
 *                 app:statusBackground="@color/translucent"/> 状态栏颜色，7.0以上会被系统再加上一个蒙层，建议设置全透明
 * }
 * Created by wangyu on 2018/3/12.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
@SuppressLint("AppCompatCustomView")
class StatusBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ImageView(context, attrs, defStyleAttr) {

    private var targetVersionCode: Int = Build.VERSION_CODES.KITKAT

    /**
     * 是否需要显示StatusBar
     *
     * @return true 显示StatusBar
     */
    private val isNeedShowStatusBar: Boolean
        get() = isInEditMode || Build.VERSION.SDK_INT >= targetVersionCode

    private val statusBarHeight: Int
        get() = getStatusBarHeight(context)

    init {
        val array = context.theme.obtainStyledAttributes(attrs,
                R.styleable.StatusBar, defStyleAttr, 0)
        val drawable = array.getDrawable(R.styleable.StatusBar_statusBackground)
        targetVersionCode = array.getInt(R.styleable.StatusBar_targetVersion, Build.VERSION_CODES.KITKAT)
        targetVersionCode = if (targetVersionCode < Build.VERSION_CODES.KITKAT) Build.VERSION_CODES.KITKAT else targetVersionCode
        targetVersionCode = if (targetVersionCode > Build.VERSION_CODES.O_MR1) Build.VERSION_CODES.O else targetVersionCode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            background = drawable
        } else {
            @Suppress("DEPRECATION")
            setBackgroundDrawable(drawable)
        }
        array.recycle()
    }

    /**
     * 返回状态栏高度，PrivateApi在9.0可能会遇到问题，取不到则返回25dp高度
     *
     * @return 像素
     */
    @SuppressLint("PrivateApi")
    private fun getStatusBarHeight(context: Context): Int = if (isNeedShowStatusBar) {
        try {
            val aClass = Class.forName("com.android.internal.R\$dimen")
            val obj = aClass.newInstance()
            val field = aClass.getField("status_bar_height")
            val x = Integer.parseInt(field.get(obj).toString())
            context.resources.getDimensionPixelSize(x)
        } catch (e: Exception) {
            e.printStackTrace()
            AppUtils.dipToPx(getContext(), 25f)
        }
    } else {
        0
    }

    override fun getSuggestedMinimumHeight(): Int = statusBarHeight
}
