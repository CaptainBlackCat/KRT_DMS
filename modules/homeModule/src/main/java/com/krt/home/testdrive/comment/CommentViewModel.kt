package com.krt.home.testdrive.comment

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.home.service.NetUrlHome
import com.krt.home.testdrive.detail.bean.TestDriveDetailEntity
import com.krt.home.testdrive.list.TestDriveListConfig
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.network.httpPost

class CommentViewModel(application: Application) : BaseViewModel(application) {

    val commentSuccessLiveData = MutableLiveData<Boolean>()

    val resultDataLiveData = MutableLiveData<TestDriveDetailEntity>()

    fun loadData(bookNo: String) {
        val params = listOf(
                "companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId,
                "bookNo", bookNo
        ).toParams()

        httpGet<TestDriveDetailEntity>(
                NetUrlHome.GET_TRIAL_DRIVE_INFO,
                this,
                httpParams = params,
                lce = LCEParams(showLoadingDialog = true)
        ).toObject {
            resultDataLiveData.value = it
        }
    }

    fun save(
            bookNo: String,
            appearance: Int,
            interior: Int,
            passengerRoom: Int,
            comfortDegree: Int,
            safety: Int,
            practicability: Int,
            powerSteering: Int,
            comprehensive: Int,
            service: Int,
            influenceBuy: Int,
            recommend: Int,
            commentText: String
    ) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "bookNo", bookNo,
                "isCancel", 0,
                "status", TestDriveListConfig.FINISH,
                "appearance", appearance,
                "interior", interior,
                "passengerRoom", passengerRoom,
                "comfortDegree", comfortDegree,
                "safety", safety,
                "practicability", practicability,
                "powerSteering", powerSteering,
                "comprehensive", comprehensive,
                "service", service,
                "influenceBuy", influenceBuy,
                "recommend", recommend,
                "evaluation", commentText
        ).toJson()

        httpPost<String>(
                NetUrlHome.UPDATE_TRIAL_DRIVE_INFO,
                this,
                upJson = params,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            commentSuccessLiveData.value = true
        }

    }

}