package com.krt.submarine.newsletter

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.frame.frame.mvvm.BaseViewModel
import com.krt.network.httpGet
import com.krt.submarine.newsletter.entity.NewsLetterEntity
import com.krt.submarine.service.NetUrlSubmarine

class NewsLetterViewModel(application: Application) : BaseViewModel(application) {

    val listDataLiveData = MutableLiveData<List<NewsLetterEntity>>()

    fun loadData() {
        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<NewsLetterEntity>(NetUrlSubmarine.LIST_TODAY_FOLLOW_UP_DATA, this, httpParams = params)
                .toList {
                    listDataLiveData.value = it
                }
    }

}