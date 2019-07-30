package com.krt.submarine.followup.within

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.followup.within.bean.SubmarineFollowUpListEntity
import com.krt.submarine.service.NetUrlSubmarine

class SubmarineFollowUpViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<SubmarineFollowUpListEntity>>()

    fun loadData(status: Int) {
        val params = listOf(
                "companyId", UserDefault.companyId, "empId", UserDefault.empId,
                "status", status
        ).toParams()

        httpGet<SubmarineFollowUpListEntity>(NetUrlSubmarine.FOLLOW_UP_LIST, this, httpParams = params).toList {
            listDataLiveData.value = it
        }
    }

}