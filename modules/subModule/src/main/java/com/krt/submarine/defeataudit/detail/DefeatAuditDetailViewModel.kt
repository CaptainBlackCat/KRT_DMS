package com.krt.submarine.defeataudit.detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.defeataudit.detail.entity.DefeatAuditDetailEntity
import com.krt.submarine.service.NetUrlSubmarine

class DefeatAuditDetailViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<DefeatAuditDetailEntity>>()

    fun loadData(name: String) {
        val params =
                listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId, "foBy", name).toParams()

        httpGet<DefeatAuditDetailEntity>(NetUrlSubmarine.DEFEATED_FOR_REVIEW_DETAIL, this, httpParams = params)
                .toList {
                    listDataLiveData.value = it
                }
    }

}