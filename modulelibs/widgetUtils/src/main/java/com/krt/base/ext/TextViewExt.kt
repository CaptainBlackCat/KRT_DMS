package com.krt.base.ext

import android.graphics.Paint
import android.widget.TextView

/**
 * 设置颜色直接使用colors.xml中定义的颜色即可
 */
fun TextView.setColor(resId: Int) {
    this.setTextColor(resources.getColor(resId))
}

fun TextView.setDrawableLeft(resId: Int) {
    val drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun TextView.setDrawableRight(resId: Int) {
    val drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, drawable, null)
}

fun TextView.setDrawableTop(resId: Int) {
    val drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, drawable, null, null)
}

fun TextView.setDrawableBottom(resId: Int) {
    val drawable = this.context.resources.getDrawable(resId)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
    this.setCompoundDrawables(null, null, null, drawable)
}

fun TextView.addBottomLine() {
    this.paint.flags = Paint.UNDERLINE_TEXT_FLAG //下划线
    this.paint.isAntiAlias = true//抗锯齿
}