package com.krt.clue.customerlevel.list

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.bean.CustomerLevelListEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet

class CustomerLevelListViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<List<CustomerLevelListEntity>>()

    fun loadData() =
            httpGet<CustomerLevelListEntity>(
                    NetUrlCommon.FIND_CUSTOMER_COUNT_BY_LEVEL_GROUP,
                    this,
                    httpParams = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()
            ).toList { resultDataLiveData.value = it }

}