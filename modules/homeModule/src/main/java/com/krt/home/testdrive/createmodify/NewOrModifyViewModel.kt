package com.krt.home.testdrive.createmodify

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.home.service.NetUrlHome
import com.krt.home.testdrive.createmodify.bean.NewTestDriveEntity
import com.krt.network.base.LCEParams
import com.krt.network.httpPost

class NewOrModifyViewModel(application: Application) : BaseViewModel(application) {

    val createNewSuccessLiveData = MutableLiveData<NewTestDriveEntity>()

    val modifySuccessLiveData = MutableLiveData<Boolean>()

    fun createNewTestDrive(
            customerName: String?,
            customerMobileNo: String?,
            reservationType: String?,
            modelPlateNumber: String?,
            testDrivingTime: Long?
    ) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "customerName", customerName,
                "customerMobileNo", customerMobileNo,
                "soldBy", UserDefault.empId,
                "soldByName", UserDefault.empName,
                "modelPlateNumber", modelPlateNumber,
                "testDrivingTime", testDrivingTime,
                "reservationType", reservationType
        ).toJson()

        httpPost<NewTestDriveEntity>(
                NetUrlHome.SAVE_TRIAL_DRIVE_INFO,
                this,
                upJson = params,
                lce = LCEParams(showLoadingDialog = true)
        ).toObject {
            createNewSuccessLiveData.value = it
        }
    }

    fun modify(
            customerName: String?,
            customerMobileNo: String?,
            reservationType: String?,
            modelPlateNumber: String?,
            testDrivingTime: Long?,
            bookNo: String
    ) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "bookNo", bookNo,
                "isCancel", 0,
                "customerName", customerName,
                "customerMobileNo", customerMobileNo,
                "modelPlateNumber", modelPlateNumber,
                "testDrivingTime", testDrivingTime,
                "reservationType", reservationType
        ).toJson()

        httpPost<String>(
                NetUrlHome.UPDATE_TRIAL_DRIVE_INFO,
                this,
                upJson = params,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            modifySuccessLiveData.value = true
        }
    }

}