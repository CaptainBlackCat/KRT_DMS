package com.krt.business.ext

import android.support.v4.content.ContextCompat
import com.krt.base.ext.getColor
import com.krt.business.R
import com.krt.frame.app.config.KRT
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle

fun ToolBarConfig.toCustom(
        customBackground: Boolean = false,    //默认背景色
        customBackPop: Boolean = false,       //默认后退————左箭头并后退
        customToolBarBackGround: Boolean = false,      //标题栏  默认
        customAll: Boolean = false
): ToolBarConfig {
    if (customBackPop || customAll) {
        leftViewStyle = ToolBarViewStyle.ICON
        leftViewIcon = R.drawable.abc_text_select_handle_left_mtrl_light
        leftViewClickListener = {
            currentFragment?.pop()
        }
    }

    if (customBackground || customAll) {
        contentBackgroundColor = getColor(R.color.base_app_background_blue)
    }

    if (customToolBarBackGround || customAll) {
        toolBarBackgroundColor = ContextCompat.getColor(KRT.getApplicationContext(), R.color.base_app_background_blue)
        middleTitleFontColor = ContextCompat.getColor(KRT.getApplicationContext(), R.color.base_white)
    }

    return this
}