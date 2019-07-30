package com.krt.clue.saleconsultant.list.adapter

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.ext.getColor
import com.krt.base.ext.toParams
import com.krt.business.bean.CustomerLevelListEntity
import com.krt.business.service.NetUrlCommon
import com.krt.business.user.UserDefault
import com.krt.clue.R
import com.krt.clue.saleconsultant.detail.SaleConsultantDetailFragment
import com.krt.clue.saleconsultant.list.entity.SaleConsultantListEntity
import com.krt.frame.app.config.KRT
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.pullrecyclerview.GridDividerItemDecoration
import com.krt.network.httpGet

class SaleConsultantListAdapter(val fragment: BaseFragment) :
        BaseQuickAdapter<SaleConsultantListEntity, BaseViewHolder>(R.layout.clue_item_sale_consultant_view, null) {

    override fun convert(helper: BaseViewHolder, item: SaleConsultantListEntity) {

        helper.getView<TextView>(R.id.name_and_count).text = item.soldByName + "(" + item.cueCount + ")"

        val expandView = helper.getView<ImageView>(R.id.iv_expand)
        val recyclerView = helper.getView<RecyclerView>(R.id.recycler_view)

        helper.getView<View>(R.id.name_and_count).onClick {
            fragment.start(SaleConsultantDetailFragment.newInstance(item.soldBy ?: 0, item.soldByName ?: ""))
        }

        recyclerView.layoutManager = GridLayoutManager(KRT.getApplicationContext(), 4)
        recyclerView.addItemDecoration(GridDividerItemDecoration(1, getColor(R.color.base_app_background_gray)))
        recyclerView.requestFocus()
        recyclerView.isFocusableInTouchMode = false


        val adapter = SaleConsultantLeadLevelAdapter(fragment, item.soldBy ?: 0)
        recyclerView.adapter = adapter
        expandView.isSelected = false

        expandView.onClick {
            expandView.isSelected = !expandView.isSelected

            if (expandView.isSelected) {
                recyclerView.setVisible(View.VISIBLE)
                if (adapter.data.size <= 0) {
                    loadLeadsLevelData(item.soldBy, adapter)
                }
            } else {
                recyclerView.setVisible(View.GONE)
            }
        }
    }

    private fun loadLeadsLevelData(
            soldBy: Int?,
            adapter: SaleConsultantLeadLevelAdapter
    ) {

        val params = listOf(
                "companyId", UserDefault.companyId,
                "dealerId", UserDefault.dealerId,
                "empId", soldBy ?: 0
        ).toParams()

        httpGet<CustomerLevelListEntity>(NetUrlCommon.FIND_CUSTOMER_COUNT_BY_LEVEL_GROUP, this, httpParams = params)
                .toList { adapter.setNewData(it) }
    }

}
