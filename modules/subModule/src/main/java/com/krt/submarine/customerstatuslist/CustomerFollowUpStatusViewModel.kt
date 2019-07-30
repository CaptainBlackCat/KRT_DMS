package com.krt.submarine.customerstatuslist

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.config.SubmarineModuleConfig
import com.krt.submarine.customerstatuslist.entity.CustomerFollowUpStatusEntity
import com.krt.submarine.service.NetUrlSubmarine

class CustomerFollowUpStatusViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<CustomerFollowUpStatusEntity>>()

    fun loadData(foBy: String, statusInt: Int) {
        val status = when (statusInt) {
            SubmarineModuleConfig.FOLLOW_UP_STATUS_NEED -> "NEED"
            SubmarineModuleConfig.FOLLOW_UP_STATUS_FINISH -> "FINISH"
            SubmarineModuleConfig.FOLLOW_UP_STATUS_INCOMPLETE -> "INCOMPLETE"
            else -> {
                "INCOMPLETE"
            }
        }

        val params = listOf("companyId", 1001, "dealerId", 0, "foBy", foBy, "status", status).toParams()
        httpGet<CustomerFollowUpStatusEntity>(NetUrlSubmarine.CUSTOMER_FOLLOW_UP_STATUS_LIST, this, httpParams = params)
                .toList {
                    listDataLiveData.value = it
                }
    }

}