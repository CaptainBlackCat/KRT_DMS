package com.krt.business.db

import com.blankj.utilcode.util.SPUtils

/**
 * 当前APP正在展示的职位，销售经理   或   销售顾问
 */
object AppPostShow {

    private var isCurrentPostAdviserShow: Boolean? = null

    //是否为  销售顾问 模式
    fun isCurrentPostAdviserShow(): Boolean {
        if (null == isCurrentPostAdviserShow) {
            isCurrentPostAdviserShow = SPUtils.getInstance().getInt(SP_LAST_POST_SHOW) == SP_LAST_POST_SHOW_ADVISER
        }
        return isCurrentPostAdviserShow!!
    }

    fun isCurrentPostEmpty() =
            SPUtils.getInstance().getInt(SP_LAST_POST_SHOW) <= 0

    fun clear() {
        SPUtils.getInstance().remove(SP_LAST_POST_SHOW)
    }

    fun setCurrentPostManager() {
        SPUtils.getInstance().put(AppPostShow.SP_LAST_POST_SHOW, AppPostShow.SP_LAST_POST_SHOW_MANGER)
        isCurrentPostAdviserShow = false
    }

    fun setCurrentPostAdviser() {
        SPUtils.getInstance().put(AppPostShow.SP_LAST_POST_SHOW, AppPostShow.SP_LAST_POST_SHOW_ADVISER)
        isCurrentPostAdviserShow = true
    }

    //岗位
    const val SP_LAST_POST_SHOW = "lastPostShow"
    const val SP_LAST_POST_SHOW_MANGER = 1
    const val SP_LAST_POST_SHOW_ADVISER = 2

}