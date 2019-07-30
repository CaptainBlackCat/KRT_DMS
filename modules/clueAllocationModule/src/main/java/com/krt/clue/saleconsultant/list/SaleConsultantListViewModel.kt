package com.krt.clue.saleconsultant.list

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.clue.saleconsultant.list.entity.SaleConsultantListEntity
import com.krt.clue.url.NetUrlClueAllocation
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet

class SaleConsultantListViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<List<SaleConsultantListEntity>>()

    fun loadData(isOnDuty: Int) {
        val params = listOf(
                "companyId",
                UserDefault.companyId,
                "dealerId",
                UserDefault.dealerId,
                "isOnDuty",
                isOnDuty
        ).toParams()

        httpGet<SaleConsultantListEntity>(NetUrlClueAllocation.LIST_CONSULTANT_CUSTOMER, this, httpParams = params)
                .toList { resultDataLiveData.value = it }
    }

}