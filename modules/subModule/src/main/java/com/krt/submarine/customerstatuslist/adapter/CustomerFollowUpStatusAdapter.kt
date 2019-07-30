package com.krt.submarine.customerstatuslist.adapter

import android.util.SparseBooleanArray
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.utils.TimeUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.frame.ext.onClick
import com.krt.submarine.R
import com.krt.submarine.customerstatuslist.entity.CustomerFollowUpStatusEntity

class CustomerFollowUpStatusAdapter :
        BaseQuickAdapter<CustomerFollowUpStatusEntity, BaseViewHolder>(R.layout.sub_item_customer_follow_up, null) {

    private val historyArray = SparseBooleanArray()

    override fun convert(helper: BaseViewHolder, item: CustomerFollowUpStatusEntity) {

        helper.getView<TextView>(R.id.name).text = item.pcName
        helper.getView<TextView>(R.id.level).text = "(" + item.leadsLevel.toString() + ")"

        //意向车系、车型
        InformationDataConfig.getInformationConfig()?.series?.let {
            for (sery in it) {
                if (item.seriesCode == item.seriesCode) {
                    helper.getView<TextView>(R.id.tv_intentional_car).text = sery.seriesName

                    sery.carModels?.let {
                        for (item2 in it) {
                            if (item2.modelCode == item.modelCode) {
                                helper.getView<TextView>(R.id.tv_intentional_model).text = item2.modelName
                                return@let
                            }
                        }
                    }
                    return@let
                }
            }
        }


        helper.getView<TextView>(R.id.time).text = TimeUtils.remainingTime(item.nextFoDate)

        if (historyArray.get(helper.adapterPosition)) {
            showAll(true, item, helper)
        } else {
            showAll(false, item, helper)
        }

        helper.getView<View>(R.id.history_more).onClick {
            if (!historyArray.get(helper.adapterPosition)) {
                showAll(true, item, helper)
                historyArray.put(helper.adapterPosition, true)
            } else {
                showAll(false, item, helper)
                historyArray.put(helper.adapterPosition, false)
            }
        }
    }

    private fun showAll(
            allShow: Boolean,
            item: CustomerFollowUpStatusEntity,
            helper: BaseViewHolder
    ) {
        val followUpContainer = helper.getView<LinearLayout>(R.id.history_container)
        followUpContainer.removeAllViews()

        val ivMore = helper.getView<ImageView>(R.id.iv_more)
        if (allShow) {
            item.followupList?.let { list ->
                for (it in list) {
                    val viewHistory = View.inflate(mContext, R.layout.bus_item_com_history_view, null)
                    viewHistory.findViewById<TextView>(R.id.history_time).text = TimeUtils.formatTimeStamp(it?.foDate!!)
                    viewHistory.findViewById<TextView>(R.id.history_content).text = it.foProcesse
                    followUpContainer.addView(viewHistory)
                }
            }

            ivMore.isSelected = true
        } else {
            item.followupList?.let { list ->
                if (list.isNotEmpty()) {
                    val item = list[0]
                    val viewHistory = View.inflate(mContext, R.layout.bus_item_com_history_view, null)
                    viewHistory.findViewById<TextView>(R.id.history_time).text =
                            TimeUtils.formatTimeStamp(item?.foDate!!)
                    viewHistory.findViewById<TextView>(R.id.history_content).text = item.foProcesse
                    followUpContainer.addView(viewHistory)
                }
            }
            ivMore.isSelected = false
        }
    }

}
