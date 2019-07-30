package com.krt.clue.saleconsultant.list.adapter

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.business.bean.CustomerLevelListEntity
import com.krt.clue.R
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseFragment

class SaleConsultantLeadLevelAdapter(val fragment: BaseFragment, val empId: Int) :
        BaseQuickAdapter<CustomerLevelListEntity, BaseViewHolder>(
                R.layout.clue_item_sale_consultant_lead_level_view,
                null
        ) {

    override fun convert(helper: BaseViewHolder, item: CustomerLevelListEntity) {
        helper.setText(R.id.leads_level, item.dictValue)
        helper.setText(R.id.count, item.cueCount.toString() + "")


        helper.getView<View>(R.id.container).onClick {
            (ARouter.getInstance().build(RouterPathClueAllocation.POTENTIAL_CUSTOMERS)
                    .withInt(
                            RouterPathClueAllocation.POTENTIAL_CUSTOMERS_LEADS_LEVEL, item.leadsLevel!!
                    ).withInt(
                            RouterPathClueAllocation.POTENTIAL_CUSTOMERS_EMD_ID,
                            empId
                    ).navigation() as? BaseFragment)?.let {
                fragment.start(it)
            }
        }
    }

}
