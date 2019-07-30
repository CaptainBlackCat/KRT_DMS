package com.krt.clue.clueallocation.page

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.clue.clueallocation.page.entity.CueDataStatisticsDTO
import com.krt.clue.url.NetUrlClueAllocation
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet

class ClueAllocationListViewModel(application: Application) : BaseViewModel(application) {

    val resultDataLiveData = MutableLiveData<CueDataStatisticsDTO>()

    fun loadData() {
        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<CueDataStatisticsDTO>(NetUrlClueAllocation.CUE_DATA_STATISTICS, this, httpParams = params).toObject {
            resultDataLiveData.value = it
        }
    }

}