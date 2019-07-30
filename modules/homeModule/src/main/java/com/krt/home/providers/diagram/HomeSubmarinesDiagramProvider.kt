package com.krt.home.providers.diagram

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.krt.base.ext.toParams
import com.krt.business.user.UserDefault
import com.krt.home.R
import com.krt.home.homepage.HomePageMultipleItemAdapter
import com.krt.home.homepage.bean.HomeAdapterAllEntity
import com.krt.home.providers.diagram.entity.SubmarinesCreateChartsEntity
import com.krt.home.service.NetUrlHome
import com.krt.home.widgets.RankingListView
import com.krt.network.httpGet

/**
 *  潜客图表示意图
 */
class HomeSubmarinesDiagramProvider : BaseItemProvider<HomeAdapterAllEntity, BaseViewHolder>() {


    override fun viewType(): Int {
        return HomePageMultipleItemAdapter.TYPE_SUBMARINES_SCHEMATIC_DIAGRAM
    }

    override fun layout(): Int {
        return R.layout.home_provider_submarines_diagram
    }

    override fun convert(helper: BaseViewHolder, entity: HomeAdapterAllEntity, position: Int) {

        val container = helper.getView<LinearLayout>(R.id.container)
        container.removeAllViews()

        val params = listOf("companyId", UserDefault.companyId, "dealerId", UserDefault.dealerId).toParams()

        httpGet<SubmarinesCreateChartsEntity>(
                NetUrlHome.FIND_ESTABLISH_CUSTOMER_FILE_RANK,
                this,
                httpParams = params
        ).toList {
            var nameMaxCount = 0
            var maxCount = 0


            for (item in it) {
                val nameCount = (item.soldByName ?: "").length

                if (nameCount > nameMaxCount) {
                    nameMaxCount = nameCount
                }

                val count = item.cueCount

                if (count > maxCount) {
                    maxCount = count
                }
            }

            if (nameMaxCount > 6) {
                nameMaxCount = 6
            }

            for (item in it) {
                container.addView(
                        RankingListView(
                                item.soldByName ?: "",
                                item.cueCount,
                                maxCount,
                                nameMaxCount,
                                mContext
                        )
                )
            }
        }

    }

}
