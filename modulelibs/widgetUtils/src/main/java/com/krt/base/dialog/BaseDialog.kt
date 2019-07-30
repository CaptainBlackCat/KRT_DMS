package com.krt.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.Window
import android.view.WindowManager.LayoutParams
import com.krt.base.R


/**
 * @author xbf
 */
abstract class BaseDialog(context: Context, themeResId: Int, alpha: Float? = null, gravity: Int? = null) : Dialog(context, themeResId) {

    protected var layoutParams: LayoutParams? = null

    init {
        initView()
    }

    private fun initView() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window!!.setBackgroundDrawableResource(R.color.base_transparent)
        val window = this.window
        layoutParams = window!!.attributes
        layoutParams!!.alpha = 1f
        window.attributes = layoutParams
        if (layoutParams != null) {
            layoutParams!!.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams!!.gravity = Gravity.CENTER
        }
    }

//    /**
//     * @param context
//     * @param alpha   透明度 0.0f--1f(不透明)
//     * @param gravity 方向(Gravity.BOTTOM,Gravity.TOP,Gravity.LEFT,Gravity.RIGHT)
//     */
//    constructor(context: Context, alpha: Float, gravity: Int) : super(context) {
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        this.window!!.setBackgroundDrawableResource(R.color.com_transparent)
//        val window = this.window
//        layoutParams = window!!.attributes
//        layoutParams!!.alpha = 1f
//        window.attributes = layoutParams
//        if (layoutParams != null) {
//            layoutParams!!.height = android.view.ViewGroup.LayoutParams.MATCH_PARENT
//            layoutParams!!.gravity = gravity
//        }
//    }

    /**
     * 隐藏头部导航栏状态栏
     */
    fun skipTools() {
        window!!.setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN)
    }

    /**
     * 设置全屏显示
     */
    fun setFullScreen() {
        val window = window
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window.attributes
        lp.width = LayoutParams.MATCH_PARENT
        lp.height = LayoutParams.MATCH_PARENT
        window.attributes = lp
    }

    /**
     * 设置宽度match_parent
     */
    fun setFullScreenWidth() {
        val window = window
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window.attributes
        lp.width = LayoutParams.MATCH_PARENT
        lp.height = LayoutParams.WRAP_CONTENT
        window.attributes = lp
    }

    /**
     * 设置高度为match_parent
     */
    fun setFullScreenHeight() {
        val window = window
        window!!.decorView.setPadding(0, 0, 0, 0)
        val lp = window.attributes
        lp.width = LayoutParams.WRAP_CONTENT
        lp.height = LayoutParams.MATCH_PARENT
        window.attributes = lp
    }

    fun setOnWhole() {
        window!!.setType(LayoutParams.TYPE_SYSTEM_ALERT)
    }
}
