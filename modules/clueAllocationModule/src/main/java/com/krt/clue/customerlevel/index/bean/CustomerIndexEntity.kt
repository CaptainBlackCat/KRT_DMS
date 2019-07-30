package com.krt.clue.customerlevel.index.bean

import com.alibaba.fastjson.annotation.JSONField
import com.krt.base.widgets.index.IndexBar.bean.BaseIndexPinyinBean

data class CustomerIndexEntity(
        @JSONField(name = "infoSource")
        val infoSource: String?,
        @JSONField(name = "pcMobile")
        val pcMobile: String? = "",
        @JSONField(name = "pcName")
        val pcName: String? = "",
        @JSONField(name = "pcNo")
        val pcNo: String?,
        @JSONField(name = "pcStatus")
        val pcStatus: String?,
        @JSONField(name = "soldBy")
        val soldBy: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?,
        @JSONField(name = "isFocus")
        val isFocus: Int? = 0,
        @JSONField(name = "leadsLevel")
        val leadsLevel: Int?,
        @JSONField(name = "seriesCode")
        val seriesCode: String?
) : BaseIndexPinyinBean() {
    override fun getTarget(): String {
        if (isFocus == 1) {
            return "*"
        }
        return pcName ?: "#"
    }

}