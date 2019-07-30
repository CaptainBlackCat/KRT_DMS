package com.krt.base.widgets

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import com.krt.base.R

class EditTextWithClear : AppCompatEditText {
    private var imgInable: Drawable? = null
    private val imgAble: Drawable? = null
    private var mContext: Context? = null

    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mContext = context
        init()
    }

    private fun init() {
        setPadding(paddingLeft, paddingTop, context!!.resources.getDimensionPixelOffset(R.dimen.base_10), paddingBottom)
        imgInable = ContextCompat.getDrawable(mContext!!, R.drawable.base_icon_edittext_clear)
        addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun afterTextChanged(s: Editable) {
                setDrawable()
            }
        })
        setDrawable()
    }

    // 设置删除图片
    private fun setDrawable() {
        if (length() < 1)
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        else
            setCompoundDrawablesWithIntrinsicBounds(null, null, imgInable, null)
    }

    // 处理删除事件
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (imgInable != null && event.action == MotionEvent.ACTION_UP) {
            val eventX = event.rawX.toInt()
            val eventY = event.rawY.toInt()
            val rect = Rect()
            getGlobalVisibleRect(rect)
            rect.left = rect.right - 100
            if (rect.contains(eventX, eventY))
                setText("")
        }
        return super.onTouchEvent(event)
    }

}