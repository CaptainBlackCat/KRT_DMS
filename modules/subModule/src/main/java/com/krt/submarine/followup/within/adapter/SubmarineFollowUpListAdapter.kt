package com.krt.submarine.followup.within.adapter

import android.view.View
import android.widget.TextView
import com.blankj.utilcode.constant.TimeConstants
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.utils.CallAndSmsPermissionUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.submarine.R
import com.krt.submarine.followup.SubmarineFollowUpConfig
import com.krt.submarine.followup.within.bean.SubmarineFollowUpListEntity
import com.krt.submarine.sms.SendSmsFragment

class SubmarineFollowUpListAdapter(private val baseFragment: BaseFragment, private val status: Int) :
        BaseQuickAdapter<SubmarineFollowUpListEntity, BaseViewHolder>(R.layout.sub_item_submarine_follow_up_view, null) {

    var actionFollowUp2Detail: ((SubmarineFollowUpListEntity) -> Unit)? = null

    var actionFollowUp2New: ((SubmarineFollowUpListEntity) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: SubmarineFollowUpListEntity) {

        initTimeView(helper.getView(R.id.time), item)

        helper.setText(R.id.name, item.pcName)
        helper.setText(R.id.tv_intentional_model, item.modelName)
        helper.setText(R.id.tv_last_record, item.foProcesse)

        var level = ""

        InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
            for (itemIn in it) {
                if (itemIn.dictKey == item.leadsLevel) {
                    level = "(" + itemIn.dictValue?.subSequence(0, 1) + ")"
                    return@let
                }
            }
        }
        helper.setText(R.id.tv_level, level)

        helper.getView<TextView>(R.id.view_follow_up_details).onClick {
            actionFollowUp2Detail?.invoke(item)
        }

        helper.getView<TextView>(R.id.new_follow_up).onClick {
            actionFollowUp2New?.invoke(item)
        }

        helper.getView<View>(R.id.btn_phone).onClick {
            CallAndSmsPermissionUtils.checkCallPhonePermission(baseFragment, item.pcMobile!!)
        }

        helper.getView<View>(R.id.btn_sms).onClick {
            baseFragment.getParentFragmentByThis()?.start(SendSmsFragment.newInstance(item.pcName ?: "", item.pcMobile ?: ""))
        }
    }

    private fun initTimeView(timeView: TextView, item: SubmarineFollowUpListEntity) {
        when (status) {
            SubmarineFollowUpConfig.TWO_DAYS_WITHIN -> {
                timeView.setBackgroundResource(R.drawable.sub_rectangle_solid_yellow_bg)
                timeView.text = com.krt.base.utils.TimeUtils.formatTimeByDayHourMin(
                        TimeUtils.getTimeSpanByNow(
                                item.nextFoDate!!,
                                TimeConstants.SEC
                        )
                )
            }
            SubmarineFollowUpConfig.TWO_DAYS_AFTER -> {
                timeView.setBackgroundResource(R.drawable.sub_rectangle_solid_green_bg)
                timeView.text = com.krt.base.utils.TimeUtils.formatTimeByDayHourMin(
                        TimeUtils.getTimeSpanByNow(
                                item.nextFoDate!!,
                                TimeConstants.SEC
                        )
                )
            }
            SubmarineFollowUpConfig.TIME_OUT -> {
                timeView.setBackgroundResource(R.drawable.sub_rectangle_solid_red_bg)
                timeView.text = com.krt.base.utils.TimeUtils.formatTimeByDayHourMin(
                        Math.abs(
                                TimeUtils.getTimeSpanByNow(
                                        item.nextFoDate!!,
                                        TimeConstants.SEC
                                )
                        )
                )
            }
        }
    }

}
