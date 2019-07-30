package com.krt.remind.remind

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.remind.remind.bean.RemindPageConsultantEntity
import com.krt.remind.remind.bean.RemindPageManagerEntity
import com.krt.remind.service.NetUrlRemind

class RemindPageViewModel(application: Application) : BaseViewModel(application) {

    val consultantInfoLiveData = MutableLiveData<RemindPageConsultantEntity>()

    val managerInfoLiveData = MutableLiveData<RemindPageManagerEntity>()

    fun loadDataByConsultant() {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "empId", UserDefault.empId
        ).toParams()

        httpGet<RemindPageConsultantEntity>(
                NetUrlRemind.FIND_CONSULTANT_INFO,
                this,
                httpParams = params,
                lce = LCEParams(notStartLce = true)
        ).toObject {
            consultantInfoLiveData.value = it
        }
    }

    fun loadDataByManager() {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "empId", UserDefault.empId
        ).toParams()

        httpGet<RemindPageManagerEntity>(
                NetUrlRemind.FIND_MANAGER_INFO,
                this,
                httpParams = params,
                lce = LCEParams(notStartLce = true)
        ).toObject {
            managerInfoLiveData.value = it
        }
    }

}