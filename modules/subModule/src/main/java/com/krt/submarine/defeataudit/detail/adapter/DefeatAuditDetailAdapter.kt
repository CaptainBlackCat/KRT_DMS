package com.krt.submarine.defeataudit.detail.adapter

import android.util.SparseBooleanArray
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSONObject
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.utils.CallAndSmsPermissionUtils
import com.krt.base.utils.TimeUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.submarine.R
import com.krt.submarine.defeataudit.detail.entity.DefeatAuditDetailEntity
import com.krt.submarine.defeataudit.examine.DefeatAuditExamineFragment

class DefeatAuditDetailAdapter(val fragment: BaseFragment) :
        BaseQuickAdapter<DefeatAuditDetailEntity, BaseViewHolder>(R.layout.sub_item_defeat_audit_detail, null) {

    private val historyArray = SparseBooleanArray()

    var actionDirect2Examine: ((Int, String) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: DefeatAuditDetailEntity) {
        helper.getView<TextView>(R.id.tv_name).text = item.pcName

        item.defeatReason?.let {
            val listConfig = InformationDataConfig.getInformationConfig()?.reasonForFailure

            val text = StringBuilder()
            for (item in JSONObject.parseArray(it, String::class.java)) {
                listConfig?.let {
                    for (item2 in listConfig) {
                        if (item2.dictKey == item.toInt()) {
                            text.append(item2.dictValue).append(",")
                            return@let
                        }
                    }
                }
            }

            if (text.length > 1) {
                helper.getView<TextView>(R.id.tv_cause_of_defeat).text = text.substring(0, text.length - 1)
            }
        }

        helper.getView<TextView>(R.id.tv_defeat_description).text = item.summarizeAnalyze
        helper.getView<TextView>(R.id.tv_intentional_car).text = item.seriesCode
        helper.getView<TextView>(R.id.tv_intentional_model).text = item.modelCode

        helper.getView<View>(R.id.iv_phone).onClick {
            CallAndSmsPermissionUtils.checkCallPhonePermission(fragment, item.pcMobile ?: "")
        }

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

        helper.getView<TextView>(R.id.btn_pass).onClick {
            actionDirect2Examine?.invoke(DefeatAuditExamineFragment.STYLE_PASS, item.foNo ?: "")
        }

        helper.getView<TextView>(R.id.btn_reject).onClick {
            actionDirect2Examine?.invoke(DefeatAuditExamineFragment.STYLE_REJECT, item.foNo ?: "")
        }

        helper.getView<TextView>(R.id.btn_distribution).onClick {
            actionDirect2Examine?.invoke(DefeatAuditExamineFragment.STYLE_ALLOCATION, item.foNo ?: "")
        }

    }

    private fun showAll(
            allShow: Boolean,
            item: DefeatAuditDetailEntity,
            helper: BaseViewHolder
    ) {
        val followUpContainer = helper.getView<LinearLayout>(R.id.history_container)
        followUpContainer.removeAllViews()

        val ivMore = helper.getView<ImageView>(R.id.iv_more)
        if (allShow) {
            item.historyTrack?.let { list ->
                for (it in list) {
                    val viewHistory = View.inflate(mContext, R.layout.bus_item_com_history_view, null)
                    viewHistory.findViewById<TextView>(R.id.history_time).text =
                            TimeUtils.formatTimeStamp(it?.foDate ?: 0)
                    viewHistory.findViewById<TextView>(R.id.history_content).text = it?.foProcesse
                    followUpContainer.addView(viewHistory)
                }
            }

            ivMore.isSelected = true
        } else {
            item.historyTrack?.let { list ->
                if (list.isNotEmpty()) {
                    val item = list[0]
                    val viewHistory = View.inflate(mContext, R.layout.bus_item_com_history_view, null)
                    viewHistory.findViewById<TextView>(R.id.history_time).text =
                            TimeUtils.formatTimeStamp(item?.foDate ?: 0)
                    viewHistory.findViewById<TextView>(R.id.history_content).text = item?.foProcesse
                    followUpContainer.addView(viewHistory)
                }
            }
            ivMore.isSelected = false
        }
    }
}
