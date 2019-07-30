package com.krt.home.providers

import android.support.v7.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.krt.base.ext.toParams
import com.krt.business.db.AppPostShow
import com.krt.business.user.UserDefault
import com.krt.frame.ext.showToast
import com.krt.home.R
import com.krt.home.homepage.HomePageMultipleItemAdapter
import com.krt.home.homepage.adapter.HomeFunctionChooseAdapter
import com.krt.home.homepage.bean.HomeAdapterAllEntity
import com.krt.home.providers.entity.HomeFunctionChooseEntity
import com.krt.home.service.NetUrlHome
import com.krt.home.utils.HomeCommonGridInitUtils
import com.krt.network.httpGet
import me.yokeyword.fragmentation.SupportFragment

class HomeFunctionChooseProvider(val fragment: SupportFragment?) : BaseItemProvider<HomeAdapterAllEntity, BaseViewHolder>() {

    override fun viewType(): Int {
        return HomePageMultipleItemAdapter.TYPE_FUNCTION_CHOOSE
    }

    override fun layout(): Int {
        return R.layout.home_provider_function_choose
    }

    override fun convert(holder: BaseViewHolder, data: HomeAdapterAllEntity, position: Int) {
        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId, "dutyId", UserDefault.dutyId,
                "empId", if (AppPostShow.isCurrentPostAdviserShow()) UserDefault.empId else "",
                "switchEnd", if (AppPostShow.isCurrentPostAdviserShow()) "CONSULTANT" else "MANAGER").toParams()

        httpGet<HomeFunctionChooseEntity>(NetUrlHome.GET_HOME_PAGE_CONF_DATA, this, httpParams = params).toObject {
            val recyclerView = holder.getView<RecyclerView>(R.id.recycler_view)
            HomeCommonGridInitUtils.init(recyclerView)
            it?.modules?.let {
                val adapter = HomeFunctionChooseAdapter(it)
                recyclerView.adapter = adapter

                adapter.setOnItemClickListener { _, _, position ->
                    val findFragment =
                            ARouter.getInstance().build(adapter.data[position].androidRoute).navigation() as? SupportFragment

                    if (findFragment != null) {
                        (fragment?.parentFragment as? SupportFragment)?.start(findFragment)
                    } else {
                        showToast("can not find " + adapter.data[position].androidRoute)
                    }
                }
            }
        }
    }
}
