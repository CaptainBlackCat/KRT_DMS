package com.krt.base.ext

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.krt.frame.ext.setVisible

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)


fun ViewGroup.setVisibleAndOtherGone(list: List<Int>) {
    val childCount = this.childCount
    for (i in 0 until childCount) {
        val view = getChildAt(i)
        if (list.contains(view.id)) {
            view.setVisible(View.VISIBLE)
        } else {
            view.setVisible(View.GONE)
        }
    }

}