package com.krt.base.picker

import com.contrarywind.interfaces.IPickerViewData

class SimplePickerDate(val text: String, val data: Any? = null) : IPickerViewData {

    override fun getPickerViewText() = text

}