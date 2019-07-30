package com.krt.home.utils

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.krt.base.ext.getColor
import com.krt.frame.app.config.KRT
import com.krt.frame.pullrecyclerview.GridDividerItemDecoration
import com.krt.home.R

object HomeCommonGridInitUtils {

    fun init(recyclerView: RecyclerView) {
        recyclerView.layoutManager = GridLayoutManager(KRT.getApplicationContext(), 4)
        recyclerView.addItemDecoration(GridDividerItemDecoration(1, getColor(R.color.base_app_background_gray)))
        recyclerView.requestFocus()
        recyclerView.isFocusableInTouchMode = false
    }

}