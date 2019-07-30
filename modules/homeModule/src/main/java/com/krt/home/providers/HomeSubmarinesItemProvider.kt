package com.krt.home.providers

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.krt.base.ext.toParams
import com.krt.business.bean.CustomerLevelListEntity
import com.krt.business.db.AppPostShow
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.home.R
import com.krt.home.homepage.HomePageMultipleItemAdapter
import com.krt.home.homepage.bean.HomeAdapterAllEntity
import com.krt.home.providers.adapter.SubmarinesCenterAdapter
import com.krt.home.utils.HomeCommonGridInitUtils
import com.krt.network.httpGet


class HomeSubmarinesItemProvider(val fragment: BaseFragment?) :
        BaseItemProvider<HomeAdapterAllEntity, BaseViewHolder>() {

    private var mAdapter = SubmarinesCenterAdapter()

    override fun viewType(): Int {
        return HomePageMultipleItemAdapter.TYPE_SUBMARINES
    }

    override fun layout(): Int {
        return R.layout.home_provider_submarines
    }

    override fun convert(helper: BaseViewHolder, entity: HomeAdapterAllEntity, position: Int) {
        val recyclerView = helper.getView<RecyclerView>(R.id.recycler_view)

        HomeCommonGridInitUtils.init(recyclerView)

        recyclerView.adapter = mAdapter
        mAdapter.setOnItemClickListener { _, _, position ->

            if (AppPostShow.isCurrentPostAdviserShow()) {
                (ARouter.getInstance().build(RouterPathClueAllocation.POTENTIAL_CUSTOMERS)
                        .withInt(
                                RouterPathClueAllocation.POTENTIAL_CUSTOMERS_LEADS_LEVEL, mAdapter.data[position].leadsLevel!!
                        ).withInt(
                                RouterPathClueAllocation.POTENTIAL_CUSTOMERS_EMD_ID, UserDefault.empId
                        ).navigation() as? BaseFragment)?.let {
                    fragment?.getParentFragmentByThis()?.start(it)
                }
            } else {
                (ARouter.getInstance().build(RouterPathClueAllocation.POTENTIAL_CUSTOMERS)
                        .withInt(
                                RouterPathClueAllocation.POTENTIAL_CUSTOMERS_LEADS_LEVEL, mAdapter.data[position].leadsLevel!!
                        ).navigation() as? BaseFragment)?.let {
                    fragment?.getParentFragmentByThis()?.start(it)
                }
            }
        }

        loadLeadsLevelData()
    }

    fun refresh() {
        loadLeadsLevelData()
    }

    private fun loadLeadsLevelData() {
        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId
        ).toParams()

        if (AppPostShow.isCurrentPostAdviserShow()) {
            params.put("empId", UserDefault.empId)
        }

        httpGet<CustomerLevelListEntity>(
                NetUrlCommon.FIND_CUSTOMER_COUNT_BY_LEVEL_GROUP,
                this,
                httpParams = params
        ).toList {
            mAdapter.setNewData(it)
        }
    }

}
