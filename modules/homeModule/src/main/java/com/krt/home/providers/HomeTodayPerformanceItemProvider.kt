package com.krt.home.providers

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.krt.home.R
import com.krt.home.homepage.HomePageMultipleItemAdapter
import com.krt.home.homepage.bean.HomeAdapterAllEntity


class HomeTodayPerformanceItemProvider : BaseItemProvider<HomeAdapterAllEntity, BaseViewHolder>() {


    override fun viewType(): Int {
        return HomePageMultipleItemAdapter.TYPE_TODAY_PERFORMANCE
    }

    override fun layout(): Int {
        return R.layout.home_provider_today_performance
    }

    override fun convert(helper: BaseViewHolder, entity: HomeAdapterAllEntity, position: Int) {


    }
}
