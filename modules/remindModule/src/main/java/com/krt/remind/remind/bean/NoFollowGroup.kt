package com.krt.remind.remind.bean

import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NoFollowGroup(
        @JSONField(name = "overtimecount")
        val overtimecount: Int?,
        @JSONField(name = "soldByName")
        val soldByName: String?
) : Parcelable