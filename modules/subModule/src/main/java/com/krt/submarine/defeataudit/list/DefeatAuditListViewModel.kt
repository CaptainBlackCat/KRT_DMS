package com.krt.submarine.defeataudit.list

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.defeataudit.list.entity.DefeatAuditListEntity
import com.krt.submarine.service.NetUrlSubmarine

class DefeatAuditListViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<DefeatAuditListEntity>>()

    fun loadData() {
        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<DefeatAuditListEntity>(NetUrlSubmarine.DEFEATED_FOR_REVIEW_LIST, this, httpParams = params)
                .toList {
                    listDataLiveData.value = it
                }
    }

}