package com.krt.home.testdrive.detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.home.service.NetUrlHome
import com.krt.home.testdrive.detail.bean.TestDriveDetailEntity
import com.krt.network.httpGet

class TestDriveDetailViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<TestDriveDetailEntity>()

    fun loadData(bookNo: String) {
        val params = listOf(
                "companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId,
                "bookNo", bookNo
        ).toParams()

        httpGet<TestDriveDetailEntity>(
                NetUrlHome.GET_TRIAL_DRIVE_INFO,
                this,
                httpParams = params
        ).toObject {
            resultDataLiveData.value = it
        }
    }

}