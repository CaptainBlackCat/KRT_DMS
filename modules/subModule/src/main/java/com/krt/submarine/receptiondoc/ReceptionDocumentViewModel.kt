package com.krt.submarine.receptiondoc

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.alibaba.fastjson.JSONObject
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.base.NetWorkBaseResult
import com.krt.network.httpGet
import com.krt.network.httpPost
import com.krt.submarine.service.NetUrlSubmarine

class ReceptionDocumentViewModel(application: Application) : BaseViewModel(application) {

    val checkPhoneUsableLiveData = MutableLiveData<String>()

    val saveSuccessLiveData = MutableLiveData<Boolean>()

    fun checkPhone(pcMobile: String) {
        val params = listOf(
                "companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId,
                "empId", UserDefault.empId, "pcMobile", pcMobile
        ).toParams()

        httpGet<String>(
                NetUrlSubmarine.RECEPTION_FILING_CHECK,
                this,
                httpParams = params,
                lce = LCEParams(showLoadingDialog = true)
        ) {
            val result = JSONObject.parseObject(it, NetWorkBaseResult::class.java)
            if (result.code == 200) {
                checkPhoneUsableLiveData.value = result.message
            }
        }
    }

    fun save(
            pcName: String, pcMobile: String, gender: Int, infoSource: Int, leadsLevel: Int,
            isTestDrive: Int, isMortgage: Int, isFocus: Int, province: String?, city: String?,
            county: String?, address: String, seriesCode: String?, modelCode: String,
            colorCode: String?, firstComing: String,
            focusPerformance: Int, focusPrice: Int, focusExterior: Int, focusInterior: Int,
            focusOilwear: Int, focusAfterSales: Int, focusAccessories: Int, focusOther: Int
    ) {
        val upJson = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "pcName", pcName,
                "pcMobile", pcMobile,
                "gender", gender,
                "infoSource", infoSource,
                "leadsLevel", leadsLevel,
                "isTestDrive", isTestDrive,
                "isMortgage", isMortgage,
                "isFocus", isFocus,
                "province", province,
                "city", city,
                "county", county,
                "address", address,
                "seriesCode", seriesCode,
                "modelCode", modelCode,
                "colorCode", colorCode,
                "firstComing", firstComing,
                "focusPerformance", focusPerformance,
                "focusPrice", focusPrice,
                "focusExterior", focusExterior,
                "focusInterior", focusInterior,
                "focusOilwear", focusOilwear,
                "focusAfterSales", focusAfterSales,
                "focusAccessories", focusAccessories,
                "focusOther", focusOther,
                "soldBy", UserDefault.empId,
                "soldByName", UserDefault.empName
        ).toJson()

        httpPost<String>(
                NetUrlSubmarine.RECEPTION_FILING_SAVE, this,
                upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        ) {
            saveSuccessLiveData.value = true
        }
    }

}