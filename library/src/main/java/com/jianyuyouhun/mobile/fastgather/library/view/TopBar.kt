package com.jianyuyouhun.mobile.fastgather.library.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.jianyuyouhun.mobile.fastgather.library.R
import com.jianyuyouhun.mobile.fastgather.library.utils.AppUtils
import com.jianyuyouhun.mobile.fastgather.library.utils.kt.getChildViews
import com.jianyuyouhun.mobile.fastgather.library.utils.kt.getColor
import com.jianyuyouhun.mobile.fastgather.library.utils.kt.getDrawable
import com.jianyuyouhun.mobile.fastgather.library.utils.kt.proxy.bindView


/**
 * 顶部工具栏
 */
const val FUNCTION_NONE = 0
const val FUNCTION_LEFT_TITLE = 2
const val FUNCTION_LEFT_BTN = 4
const val FUNCTION_TITLE = 8

class TopBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RelativeLayout(context, attrs, defStyleAttr) {

    private var function = -1

    private val leftTextView by bindView<TextView>(R.id.top_bar_left_title)

    private val titleTextView by bindView<TextView>(R.id.top_bar_title)

    private val rightContainer by bindView<LinearLayout>(R.id.right_container)

    private val leftIconBtn by bindView<ImageView>(R.id.top_bar_left_btn)

    private val rightTextPadding by lazy {
        AppUtils.dipToPx(context, 12F)
    }
    private val rightImagePadding by lazy {
        AppUtils.dipToPx(context, 10F)
    }
    private val rightImageWidth by lazy {
        context.resources.getDimension(R.dimen.actionBarRightImgWidth)
    }
    private val rightImageHeight by lazy {
        context.resources.getDimension(R.dimen.actionBarRightImgHeight)
    }

    private fun rightBackDrawable(): Drawable? {
        val attrsArray = intArrayOf(R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.obtainStyledAttributes(attrsArray)
        val result = typedArray.getDrawable(0)
        typedArray.recycle()
        return result
    }

    init {
        inflate(context, R.layout.view_top_bar, this)

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar)

        typedArray.getString(R.styleable.TopBar_titleText)?.apply {
            setTitle(this)
        } ?: setTitle(resources.getString(R.string.app_name))

        setTitleColor(typedArray.getColor(R.styleable.TopBar_titleColor, getColor(R.color.black)))
        setLeftText(typedArray.getString(R.styleable.TopBar_leftText))
        setLeftColor(typedArray.getColor(R.styleable.TopBar_leftColor, getColor(R.color.textTitle)))
        setBackgroundColor(typedArray.getColor(R.styleable.TopBar_bgColor, getColor(R.color.actionBarColor)))

        typedArray.getDrawable(R.styleable.TopBar_leftIcon)?.apply {
            setLeftDrawable(this)
        } ?: setLeftDrawable(getDrawable(R.drawable.ic_back)!!)

        setLeftDrawablePadding(typedArray.getDimensionPixelSize(R.styleable.TopBar_leftIconPadding, resources.getDimensionPixelSize(R.dimen.actionBarIconPadding)))

        val function = typedArray.getInt(R.styleable.TopBar_function, FUNCTION_LEFT_BTN or FUNCTION_TITLE)
        setFunction(function)
        typedArray.recycle()

        setOnLeftClickListener(View.OnClickListener { (context as Activity).onBackPressed() })
    }

    fun setOnLeftClickListener(onLeftClickListener: View.OnClickListener?) {
        leftIconBtn.setOnClickListener(onLeftClickListener)
        leftTextView.setOnClickListener(onLeftClickListener)

        leftIconBtn.isEnabled = onLeftClickListener != null
        leftTextView.isEnabled = onLeftClickListener != null
    }

    fun setOnTitleClickListener(onTitleClickListener: View.OnClickListener?) {
        titleTextView.setOnClickListener(onTitleClickListener)
        titleTextView.isEnabled = onTitleClickListener != null
    }

    fun addRightIcon(icon: Drawable, tag: String, onClickListener: OnClickListener) {
        val imageView = ImageView(context)
        imageView.setPadding(rightImagePadding, rightImagePadding, rightImagePadding, rightImagePadding)
        imageView.setImageDrawable(icon)
        imageView.setBackgroundDrawable(rightBackDrawable())
        imageView.tag = tag
        imageView.setOnClickListener(onClickListener)
        rightContainer.addView(imageView)
        val layoutParams = imageView.layoutParams as LinearLayout.LayoutParams
        layoutParams.width = rightImageWidth.toInt()
        layoutParams.height = rightImageHeight.toInt()
        imageView.layoutParams = layoutParams
    }

    fun addRightText(text: String, tag: String, onClickListener: OnClickListener) {
        addRightText(text, getColor(R.color.textTitle), 17F, tag, onClickListener)
    }

    fun addRightText(text: String, color: Int, textSize: Float, tag: String, onClickListener: View.OnClickListener) {
        val textView = TextView(context)
        textView.text = text
        textView.setTextColor(color)
        textView.setOnClickListener(onClickListener)
        textView.gravity = Gravity.CENTER
        textView.setPadding(rightTextPadding, 0, rightTextPadding, 0)
        textView.textSize = textSize
        textView.tag = tag
        textView.setBackgroundDrawable(rightBackDrawable())
        rightContainer.addView(textView)
        val layoutParams = textView.layoutParams as LinearLayout.LayoutParams
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.MATCH_PARENT
        textView.layoutParams = layoutParams
    }

    fun removeRightByTag(tag: String) {
        rightContainer.getChildViews()
                .filter { (it.tag as String) == tag }
                .forEach { rightContainer.removeView(it) }
    }

    /**
     * 添加某一个功能
     */
    fun addFunction(function: Int) {
        setFunction(this.function or function)
    }

    fun isAddFunction(function: Int): Boolean {
        return this.function and function == function
    }

    fun removeFunction(function: Int) {
        setFunction(this.function and function.inv())
    }

    fun setTitle(title: String) {
        titleTextView.text = title
    }

    fun setTitleColor(@ColorInt color: Int) {
        titleTextView.setTextColor(color)
    }

    fun setLeftText(title: String?) {
        leftTextView.text = title
    }

    fun setLeftColor(@ColorInt color: Int) {
        leftTextView.setTextColor(color)
    }

    fun setLeftDrawable(drawable: Drawable) {
        leftIconBtn.setImageDrawable(drawable)
    }

    fun setLeftDrawablePadding(padding: Int) {
        leftIconBtn.setPadding(padding, padding, padding, padding)
    }


    fun setFunction(function: Int) {
        if (this.function == function) {
            return
        }
        this.function = function

        leftTextView.visibility = if (isAddFunction(FUNCTION_LEFT_TITLE)) View.VISIBLE else View.INVISIBLE

        leftIconBtn.visibility = if (isAddFunction(FUNCTION_LEFT_BTN)) View.VISIBLE else View.INVISIBLE

        titleTextView.visibility = if (isAddFunction(FUNCTION_TITLE)) View.VISIBLE else View.INVISIBLE

    }

}