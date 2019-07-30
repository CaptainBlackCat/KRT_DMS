package com.krt.submarine.newfollowup

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpPost
import com.krt.submarine.service.NetUrlSubmarine

class NewFollowUpViewModel(application: Application) : BaseViewModel(application) {

    val saveSuccessLiveData = MutableLiveData<Boolean>()

    fun save(
            pcNo: String,
            leadsLevel: Int,
            foProcesse: String,
            nextFoDate: String,
            foType: Int,
            seriesCode: String,
            modelCode: String,
            colorCode: String?
    ) {
        val upJson = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "foBy", UserDefault.empName,
                "pcNo", pcNo,
                "leadsLevel", leadsLevel,
                "foProcesse", foProcesse,
                "nextFoDate", nextFoDate,
                "foType", foType,
                "seriesCode", seriesCode,
                "modelCode", modelCode,
                "colorCode", colorCode
        ).toJson()

        httpPost<String>(
                NetUrlSubmarine.FOLLOW_UP_SAVE,
                this,
                upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        ) {
            saveSuccessLiveData.value = true
        }
    }

    fun saveByFailed(
            pcNo: String,
            leadsLevel: Int,
            foProcesse: String,
            foType: Int,
            seriesCode: String,
            modelCode: String,
            colorCode: String?,
            summarizeAnalyze: String,
            defeatReason: String
    ) {
        val upJson = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "foBy", UserDefault.empName,
                "pcNo", pcNo,
                "leadsLevel", leadsLevel,
                "foProcesse", foProcesse,
                "foType", foType,
                "seriesCode", seriesCode,
                "modelCode", modelCode,
                "colorCode", colorCode,
                "summarizeAnalyze", summarizeAnalyze,
                "defeatReason", defeatReason
        ).toJson()

        httpPost<String>(
                NetUrlSubmarine.FOLLOW_UP_SAVE,
                this,
                upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        ) {
            saveSuccessLiveData.value = true
        }
    }

}