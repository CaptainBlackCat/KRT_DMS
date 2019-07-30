package com.krt.clue.customerlevel.detail

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.clue.customerlevel.detail.entity.CustomerLevelEntity
import com.krt.clue.url.NetUrlClueAllocation
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.network.httpPost

class CustomerLevelDetailViewModel(application: Application) : BaseViewModel(application) {

    private var leadsLevel: Int? = null

    val resultDataLiveData = MutableLiveData<List<CustomerLevelEntity>>()

    val customerListLiveData = MutableLiveData<List<SalesConsultantEntity>>()

    fun loadData(leadsLevel: Int) {
        this.leadsLevel = leadsLevel
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "leadsLevel", leadsLevel
        ).toParams()

        httpGet<CustomerLevelEntity>(NetUrlClueAllocation.LIST_CUSTOMER_BY_LEVEL, this, httpParams = params)
                .toList {
                    resultDataLiveData.value = it
                }
    }

    fun showCustomerList() {
        if (customerListLiveData.value != null) {
            customerListLiveData.value = customerListLiveData.value
            return
        }

        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<SalesConsultantEntity>(
                NetUrlCommon.LIST_SALES_CONSULTANT,
                this,
                httpParams = params,
                lce = LCEParams(notStartLce = true)
        ).toList {
            customerListLiveData.value = it
        }
    }

    fun distribute(salesConsultant: Int, pcNoList: String) {
        val upJson = listOf(
                "companyId", UserDefault.companyId,
                "soldBy", salesConsultant,
                "pcNoList", pcNoList
        ).toJson()

        httpPost<String>(
                NetUrlClueAllocation.ASSIGN_CLUES_TO_CONSULTANT,
                this,
                upJson = upJson,
                lce = LCEParams(showLoadingDialog = true)
        )
        {
            loadData(leadsLevel ?: 0)
        }

    }

}