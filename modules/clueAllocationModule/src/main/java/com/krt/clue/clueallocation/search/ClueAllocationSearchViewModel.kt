package com.krt.clue.clueallocation.search

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.postEvent
import com.krt.base.ext.toJson
import com.krt.base.ext.toParams
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.clue.clueallocation.eventbus.ClueAllocationRefreshEventBus
import com.krt.clue.clueallocation.search.entity.ClueAllocationSearchEntity
import com.krt.clue.url.NetUrlClueAllocation
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.base.LCEParams
import com.krt.network.httpGet
import com.krt.network.httpPost

class ClueAllocationSearchViewModel(application: Application) : BaseViewModel(application) {

    private var searchKey = ""

    val resultDataLiveData = MutableLiveData<List<ClueAllocationSearchEntity>>()

    val customerListLiveData = MutableLiveData<List<SalesConsultantEntity>>()

    fun loadData(searchKey: String) {
        this.searchKey = searchKey
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "searchKey", searchKey
        ).toParams()

        httpGet<ClueAllocationSearchEntity>(
                NetUrlClueAllocation.LIST_CUSTOMER_INFO_BY_CRITERIA,
                this,
                httpParams = params
        ).toList {
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
            loadData(searchKey)
            postEvent(ClueAllocationRefreshEventBus())
        }

    }

}