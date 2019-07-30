package com.krt.clue.customerlevel.index

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.clue.customerlevel.index.bean.CustomerIndexEntity
import com.krt.clue.url.NetUrlClueAllocation
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet

class CustomerIndexViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<List<CustomerIndexEntity>>()

    val searchLiveData = MutableLiveData<List<CustomerIndexEntity>>()

    fun loadData(leadsLevel: Int?, empId: Int?) {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "leadsLevel", leadsLevel,
                "empId", empId
        ).toParams()

        httpGet<CustomerIndexEntity>(NetUrlClueAllocation.LIST_CUSTOMER_BY_LEVEL, this, httpParams = params)
                .toList {
                    resultDataLiveData.value = it
                }
    }

    fun search(text: String) {
        val list = ArrayList<CustomerIndexEntity>()

        resultDataLiveData.value?.let {
            for (item in it) {
                if ((item.pcMobile ?: "").contains(text) || (item.pcName ?: "").contains(text)) {
                    list.add(item)
                }
            }
        }

        searchLiveData.value = list
    }

}