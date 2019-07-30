package com.krt.clue.customerlevel.index.adapter

import android.view.View
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.ext.otherwise
import com.krt.base.ext.yes
import com.krt.business.config.information.InformationDataConfig
import com.krt.clue.R
import com.krt.clue.customerlevel.index.bean.CustomerIndexEntity
import com.krt.frame.ext.setVisible

class CustomerIndexAdapter :
        BaseQuickAdapter<CustomerIndexEntity, BaseViewHolder>(R.layout.clue_item_customer_index, null) {

    override fun convert(helper: BaseViewHolder, entity: CustomerIndexEntity) {
        helper.getView<TextView>(R.id.name).text = entity.pcName

        var level: String? = null

        InformationDataConfig.getInformationConfig()?.leadsLevel?.let { list ->
            for (item in list) {
                if (entity.leadsLevel == item.dictKey) {
                    level = item.dictValue?.subSequence(0, 1)?.toString()
                    return@let
                }
            }
        }

        val tvLeadsLevel = helper.getView<TextView>(R.id.tv_leads_level)
        (level != null).yes {
            tvLeadsLevel.setVisible(View.VISIBLE)
            tvLeadsLevel.text = level
        }.otherwise {
            tvLeadsLevel.setVisible(View.GONE)
        }

        helper.getView<View>(R.id.iv_focus).visibility = if (entity.isFocus == 1) View.VISIBLE else View.GONE

        //意向车型
        InformationDataConfig.getInformationConfig()?.series?.let {
            for (item in it) {
                if (item.seriesCode == entity.seriesCode) {
                    helper.getView<TextView>(R.id.tv_intentional_models).text = item.seriesName
                    return
                }

            }
        }


    }
}
