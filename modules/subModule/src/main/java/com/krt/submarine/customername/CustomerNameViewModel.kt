package com.krt.submarine.customername

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpPost
import com.krt.submarine.customerinfo.bean.CustomerInfoEntity
import com.krt.submarine.service.NetUrlSubmarine

class CustomerNameViewModel(application: Application) : BaseViewModel(application) {

    var listDataLiveData = MutableLiveData<CustomerInfoEntity>()

    val saveSuccessLiveData = MutableLiveData<Boolean>()

    fun save(
            pcNo: String,
            pcName: String,
            gender: Int,
            isFocus: Int,
            isTestDrive: Int,
            isMortgage: Int,
            province: String?,
            city: String?,
            county: String?,
            address: String,
            focusPerformance: Int, focusPrice: Int, focusExterior: Int, focusInterior: Int,
            focusOilwear: Int, focusAfterSales: Int, focusAccessories: Int, focusOther: Int
    ) {
        val upJson = listOf(
                "pcNo", pcNo,
                "pcName", pcName,
                "gender", gender,
                "isTestDrive", isTestDrive,
                "isMortgage", isMortgage,
                "isFocus", isFocus,
                "province", province,
                "city", city,
                "county", county,
                "address", address,
                "focusPerformance", focusPerformance,
                "focusPrice", focusPrice,
                "focusExterior", focusExterior,
                "focusInterior", focusInterior,
                "focusOilwear", focusOilwear,
                "focusAfterSales", focusAfterSales,
                "focusAccessories", focusAccessories,
                "focusOther", focusOther
        ).toJson()

        httpPost<String>(
                NetUrlSubmarine.RECEPTION_FILING_SAVE, this,
                upJson = upJson, lce = LCEParams(showLoadingDialog = true)
        ) {
            saveSuccessLiveData.value = true
        }
    }

}