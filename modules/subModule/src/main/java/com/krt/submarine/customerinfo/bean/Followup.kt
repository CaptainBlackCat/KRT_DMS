package com.krt.submarine.customerinfo.bean

import android.os.Parcelable
import com.alibaba.fastjson.annotation.JSONField
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Followup(
        @JSONField(name = "foBy")
        val foBy: String?,
        @JSONField(name = "foDate")
        val foDate: Long?,
        @JSONField(name = "foProcesse")
        val foProcesse: String?
) : Parcelable