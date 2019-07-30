package com.krt.home.testdrive.list

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.db.AppPostShow
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.home.service.NetUrlHome
import com.krt.home.testdrive.list.bean.TestDriveListEntity
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.network.httpPost

class TestDriveListViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<List<TestDriveListEntity>>()

    val deleteSuccessLiveData = MutableLiveData<Int>()

    val endTestDriveSuccessLiveData = MutableLiveData<Int>()

    fun loadData(testDriveDate: String, sort: String, status: String) {
        val params = listOf(
                "companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId,
                "testDriveDate", testDriveDate, "sort", sort, "status", status
        ).toParams()

        if (AppPostShow.isCurrentPostAdviserShow()) {
            params.put("empId", UserDefault.empId)
        }

        httpGet<TestDriveListEntity>(NetUrlHome.LIST_TRIAL_DRIVE, this, httpParams = params)
                .toList {
                    resultDataLiveData.value = it
                }
    }

    fun delete(entity: TestDriveListEntity, position: Int) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "bookNo", entity.bookNo,
                "isCancel", 1
        ).toJson()

        httpPost<String>(
                NetUrlHome.UPDATE_TRIAL_DRIVE_INFO,
                this,
                upJson = params,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            deleteSuccessLiveData.value = position
        }
    }

    fun endTestDrive(entity: TestDriveListEntity, position: Int) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "bookNo", entity.bookNo,
                "isCancel", 0,
                "status", "TOCOMMENT"
        ).toJson()

        httpPost<String>(
                NetUrlHome.UPDATE_TRIAL_DRIVE_INFO,
                this,
                upJson = params,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            entity.status = TestDriveListConfig.TOCOMMENT
            endTestDriveSuccessLiveData.value = position
        }
    }
}