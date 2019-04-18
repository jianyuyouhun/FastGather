package com.jianyuyouhun.mobile.fastgather.library.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.jianyuyouhun.mobile.fastgather.library.R

/**
 * 圆角image
 * Created by wangyu on 2018/11/7.
 */
class RadiusImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : ImageView(context, attrs, defStyleAttr) {
    var width = 0F
    var height = 0F

    private var defaultRadius = 0
    private var radius: Int = 0
    private var leftTopRadius: Int = 0
    private var rightTopRadius: Int = 0
    private var rightBottomRadius: Int = 0
    private var leftBottomRadius: Int = 0

    init {
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
        val array = context.theme.obtainStyledAttributes(attrs,
                R.styleable.RadiusImageView, defStyleAttr, 0)
        radius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_imageRadius, defaultRadius)
        leftTopRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_leftTopRadius, defaultRadius)
        rightTopRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_rightTopRadius, defaultRadius)
        rightBottomRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_rightBottomRadius, defaultRadius)
        leftBottomRadius = array.getDimensionPixelOffset(R.styleable.RadiusImageView_leftBottomRadius, defaultRadius)

        //如果四个角的值没有设置，那么就使用通用的radius的值。
        if (defaultRadius == leftTopRadius) {
            leftTopRadius = radius
        }
        if (defaultRadius == rightTopRadius) {
            rightTopRadius = radius
        }
        if (defaultRadius == rightBottomRadius) {
            rightBottomRadius = radius
        }
        if (defaultRadius == leftBottomRadius) {
            leftBottomRadius = radius
        }
        array.recycle()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        width = getWidth().toFloat()
        height = getHeight().toFloat()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        val maxLeft = Math.max(leftTopRadius, leftBottomRadius)
        val maxRight = Math.max(rightTopRadius, rightBottomRadius)
        val minWidth = maxLeft + maxRight
        val maxTop = Math.max(leftTopRadius, rightTopRadius)
        val maxBottom = Math.max(leftBottomRadius, rightBottomRadius)
        val minHeight = maxTop + maxBottom
        if (width >= minWidth && height > minHeight) {
            val path = Path()

            //四个角：右上，右下，左下，左上
            path.moveTo(leftTopRadius.toFloat(), 0F)
            path.lineTo(width - rightTopRadius, 0F)
            path.quadTo(width, 0F, width, rightTopRadius.toFloat())

            path.lineTo(width, height - rightBottomRadius)
            path.quadTo(width, height, width - rightBottomRadius, height)

            path.lineTo(leftBottomRadius.toFloat(), height)
            path.quadTo(0F, height, 0F, height - leftBottomRadius)

            path.lineTo(0F, leftTopRadius.toFloat())
            path.quadTo(0F, 0F, leftTopRadius.toFloat(), 0F)

            canvas.clipPath(path)
        }
        super.onDraw(canvas)
    }
}