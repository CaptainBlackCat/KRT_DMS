package com.krt.base.picker.addressbean

import com.alibaba.fastjson.annotation.JSONField
import com.contrarywind.interfaces.IPickerViewData

data class AddressEntity(
        @JSONField(name = "city")
        val city: List<City>?,
        @JSONField(name = "name")
        val name: String?
) : IPickerViewData {
    override fun getPickerViewText() = name ?: ""
}