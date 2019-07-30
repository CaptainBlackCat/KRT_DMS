package com.krt.business.config.information.bean

import com.alibaba.fastjson.annotation.JSONField
import com.contrarywind.interfaces.IPickerViewData

data class InformationConfigEntity(
        @JSONField(name = "code")
        val code: Int?,
        @JSONField(name = "data")
        val `data`: InformationConfig?,
        @JSONField(name = "message")
        val message: String?
)

data class InformationConfig(
        @JSONField(name = "infoSource")
        val infoSource: List<InfoSource>?,
        @JSONField(name = "leadsLevel")
        val leadsLevel: List<LeadsLevel>?,
        @JSONField(name = "foType")
        val foType: List<FoType>?,
        @JSONField(name = "color")
        val color: List<Color>?,
        @JSONField(name = "series")
        val series: List<Sery>?,
        @JSONField(name = "platAndModel")
        val platAndModel: List<PlatAndModel>?,
        @JSONField(name = "testDriver")
        val testDriver: List<TestDriver>?,
        @JSONField(name = "reasonForFailure")
        val reasonForFailure: List<ReasonForFailure>?
)

data class InfoSource(
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "dictNo")
        val dictNo: Int?,
        @JSONField(name = "dictKey")
        val dictKey: Int?,
        @JSONField(name = "dictValue")
        val dictValue: String?
) : IPickerViewData {
    override fun getPickerViewText() = dictValue ?: ""
}

data class PlatAndModel(
        @JSONField(name = "modelName")
        val modelName: String?
)

data class Sery(
        @JSONField(name = "seriesCode")
        val seriesCode: String?,
        @JSONField(name = "seriesName")
        val seriesName: String? = "",
        @JSONField(name = "carModels")
        val carModels: List<CarModel>?
)

data class CarModel(
        @JSONField(name = "seriesCode")
        val seriesCode: String?,
        @JSONField(name = "modelCode")
        val modelCode: String?,
        @JSONField(name = "modelName")
        val modelName: String?
)

data class FoType(
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "dictNo")
        val dictNo: Int?,
        @JSONField(name = "dictKey")
        val dictKey: Int?,
        @JSONField(name = "dictValue")
        val dictValue: String?
)

data class LeadsLevel(
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "dictNo")
        val dictNo: Int?,
        @JSONField(name = "dictKey")
        val dictKey: Int?,
        @JSONField(name = "dictValue")
        val dictValue: String?,
        @JSONField(name = "dictDesc")
        val dictDesc: String?
) : IPickerViewData {
    override fun getPickerViewText() = dictValue ?: ""
}

data class Color(
        @JSONField(name = "colorCode")
        val colorCode: String?,
        @JSONField(name = "colorName")
        val colorName: String?
) : IPickerViewData {
    override fun getPickerViewText() = colorName ?: ""
}

data class TestDriver(
        @JSONField(name = "empName")
        val empName: String?
)

data class ReasonForFailure(
        @JSONField(name = "companyId")
        val companyId: Int?,
        @JSONField(name = "dictNo")
        val dictNo: Int?,
        @JSONField(name = "dictKey")
        val dictKey: Int?,
        @JSONField(name = "dictValue")
        val dictValue: String?,
        @JSONField(name = "dictDesc")
        val dictDesc: String?
)