package com.krt.home.homepage

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.home.homepage.bean.HomeAdapterAllEntity
import com.krt.home.providers.HomeFunctionChooseProvider
import com.krt.home.providers.HomeMonthlyPerformanceItemProvider
import com.krt.home.providers.HomeSubmarinesItemProvider
import com.krt.home.providers.HomeTodayPerformanceItemProvider
import com.krt.home.providers.diagram.HomeSubmarinesDiagramProvider

class HomePageMultipleItemAdapter(val fragment: BaseFragment?, data: List<HomeAdapterAllEntity>? = ArrayList()) :
        MultipleItemRvAdapter<HomeAdapterAllEntity, BaseViewHolder>(data) {

    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(HomeMonthlyPerformanceItemProvider())
        mProviderDelegate.registerProvider(HomeTodayPerformanceItemProvider())
        mProviderDelegate.registerProvider(HomeFunctionChooseProvider(fragment))
        mProviderDelegate.registerProvider(HomeSubmarinesItemProvider(fragment))
        mProviderDelegate.registerProvider(HomeSubmarinesDiagramProvider())
    }

    override fun getViewType(entity: HomeAdapterAllEntity?): Int {
        entity?.let {
            return when (it.itemType) {
                MONTHLY_PERFORMANCE -> TYPE_MONTHLY_PERFORMANCE
                TODAY_PERFORMANCE -> TYPE_TODAY_PERFORMANCE
                FUNCTION_CHOOSE -> TYPE_FUNCTION_CHOOSE
                SUBMARINES -> TYPE_SUBMARINES
                SUBMARINES_SCHEMATIC_DIAGRAM -> TYPE_SUBMARINES_SCHEMATIC_DIAGRAM
                else -> 0
            }
        }
        return 0
    }

    /**
     * 刷新 潜客中心
     */
    fun refreshSubmarinesItemProvider() {
        (this.mProviderDelegate?.itemProviders?.get(TYPE_SUBMARINES) as? HomeSubmarinesItemProvider)?.refresh()
    }

    companion object {
        //Provider Type
        const val TYPE_MONTHLY_PERFORMANCE = 100
        const val TYPE_TODAY_PERFORMANCE = 101
        const val TYPE_FUNCTION_CHOOSE = 200
        const val TYPE_SUBMARINES = 300
        const val TYPE_SUBMARINES_SCHEMATIC_DIAGRAM = 400


        //后台配置
        const val MONTHLY_PERFORMANCE = 1  //本月业绩
        const val TODAY_PERFORMANCE = 2  //本日业绩
        const val FUNCTION_CHOOSE = 3  //功能选择
        const val SUBMARINES = 4  //潜客中心
        const val SUBMARINES_SCHEMATIC_DIAGRAM = 5  //潜客图表示意图

    }
}