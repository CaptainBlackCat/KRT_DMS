package com.krt.base.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.view.View
import com.krt.base.R

class TimerView @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) :
        AppCompatTextView(context, attrs), View.OnClickListener {

    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            val currentCount = (startCount - (System.currentTimeMillis() - startTime) / 1000).toInt()

            if (currentCount <= 0) {
                isEnabled = true
                setTextColor(ContextCompat.getColor(getContext(), R.color.base_font_color_black))
//                text = resources.getString(R.string.base_resend)
                if (null != callBack) {
                    callBack!!.ITimerViewCallBack_OnEnd()
                }
                return
            }

            this.sendEmptyMessageDelayed(0, 500)
            super.handleMessage(msg)
        }
    }

    private var isDecrease = true
    private var startCount = 10
    private var startTime: Long = 0
    private var callBack: ITimerViewCallBack? = null

    init {
        setOnClickListener(this)
//        text = resources.getString(R.string.base_acquire_verification_code)
//        obtainStyledAttrs(context, attrs, defStyleAttr)
    }

    private fun obtainStyledAttrs(context: Context, attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.timer_View, defStyleAttr, 0)

        isDecrease = typedArray.getBoolean(R.styleable.timer_View_isDecrease, true)
        startCount = typedArray.getInt(R.styleable.timer_View_timerView, 10)
        typedArray.recycle()
    }

    private fun start() {
        startTime = System.currentTimeMillis()
        if (null != callBack) {
            callBack!!.ITimerViewCallBack_OnStart()
        }
        mHandler.sendEmptyMessage(0)
    }

    override fun onClick(v: View) {
        if (null != callBack) {
            if (!callBack!!.ITimerViewCallBack_BeforeEnabled()) {
                return
            }
        }

//        val text = text.toString().trim { it <= ' ' }
//        if (resources.getString(R.string.base_acquire_verification_code) == text) {
//            isEnabled = false
//            setTextColor(ContextCompat.getColor(context, R.color.base_font_color_gray))
//            start()
//        } else if (resources.getString(R.string.base_resend) == text) {
//            isEnabled = false
//            setTextColor(ContextCompat.getColor(context, R.color.base_font_color_gray))
//            start()
//        } else {
//
//        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }

    fun setITimerViewCallBack(callBack: ITimerViewCallBack) {
        this.callBack = callBack
    }

    fun reset() {
//        text = resources.getString(R.string.base_acquire_verification_code)
//        isEnabled = true
//        setTextColor(ContextCompat.getColor(context, R.color.base_app_black))
//        mHandler.removeCallbacksAndMessages(null)
    }

    interface ITimerViewCallBack {

        fun ITimerViewCallBack_BeforeEnabled(): Boolean

        fun ITimerViewCallBack_OnStart()

        fun ITimerViewCallBack_OnEnd()
    }

}
