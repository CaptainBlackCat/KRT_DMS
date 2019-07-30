package com.krt.submarine.newfollowup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.fastjson.JSONObject
import com.krt.base.ext.getColor
import com.krt.base.ext.postEvent
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.utils.TimeUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.config.information.bean.Color
import com.krt.business.config.information.bean.FoType
import com.krt.business.config.information.bean.LeadsLevel
import com.krt.business.ext.toCustom
import com.krt.component.eventbus.GlobalCustomerIndexFreshEventBus
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import com.krt.submarine.followup.eventbus.SubmarineFollowUpRefreshEventBus
import com.krt.submarine.newfollowup.defeatreason.SubDefeatReasonFragment
import kotlinx.android.synthetic.main.sub_fragment_new_follow_up.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.textColor
import java.util.*
import kotlin.collections.ArrayList


class NewFollowUpFragment : BaseLceFragment<NewFollowUpViewModel>() {

    private var pcNo: String = ""

    //战败原因列表
    private var defeatReasonList: ArrayList<Int>? = null

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_new_follow_up,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "新增跟进记录",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "保存",
            rightViewClickListener =
            {
                save()
            }
    ).toCustom(customAll = true)

    override fun initViewModelLiveData() {
        super.initViewModelLiveData()
        viewModel?.saveSuccessLiveData?.observe(this, Observer {
            showToast("保存成功")
            postEvent(SubmarineFollowUpRefreshEventBus())
            postEvent(GlobalCustomerIndexFreshEventBus())
            this.pop()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        val customerName = arguments!!.getString(PAIR_NAME) ?: ""
        val leadsLevel = arguments!!.getInt(PAIR_LEVEL)
        val seriesCode = arguments!!.getString(SERIES_CODE) ?: ""
        val modelCode = arguments!!.getString(MODEL_CODE) ?: ""
        val colorCode = arguments!!.getString(COLOR_CODE) ?: ""
        val nextFoDate = arguments!!.getLong(NEXT_FO_DATE)
        pcNo = arguments!!.getString(PC_NO) ?: ""


        plan_time.text = TimeUtils.formatYearMonthAndDay(nextFoDate)
        real_time.text = TimeUtils.formatYearMonthAndDay(System.currentTimeMillis())

        //意向车系、车型
        InformationDataConfig.getInformationConfig()?.series?.let {
            for (item in it) {
                if (item.seriesCode == seriesCode) {
                    tv_car_intentional.text = item.seriesName
                    tv_car_intentional.tag = item.seriesCode

                    item.carModels?.let {
                        for (item2 in it) {
                            if (item2.modelCode == modelCode) {
                                tv_intentional_model.text = item2.modelName
                                tv_intentional_model.tag = item2.modelCode
                                return@let
                            }
                        }
                    }
                    return@let
                }
            }
        }

        var nameShow = customerName

        //客户级别
        InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
            for (itemIn in it) {
                if (itemIn.dictKey == leadsLevel) {
                    nameShow += "(" + itemIn.dictValue?.subSequence(0, 1) + ")"
                    return@let
                }
            }
        }
        tv_client_name.text = nameShow

        //车辆颜色
        InformationDataConfig.getInformationConfig()?.color?.let {
            for (itemIn in it) {
                if (itemIn.colorCode == colorCode) {
                    tv_vehicle_color.text = itemIn.colorName
                    tv_vehicle_color.tag = itemIn.colorCode
                    return@let
                }
            }
        }

        tv_next_follow_up_data.text = TimeUtils.getFutureDays(1)[0]
    }

    override fun initViewClickListener() {
        tv_customer_level_container.onClick {
            InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.pickerViewText, item))
                }

                CustomPickerView.show(activity!!, list) {
                    tv_customer_level.text = it.text
                    tv_customer_level.tag = (it.data as LeadsLevel).dictKey

                    tv_customer_level.textColor = getColor(R.color.base_font_color_black)

                    if ((it.data as LeadsLevel).dictKey == 30440030) {
                        tv_cause_of_defeat_container.setVisible(View.VISIBLE)
                        cause_of_defeat_line.setVisible(View.VISIBLE)
                        tv_defeat_description_container.setVisible(View.VISIBLE)
                        defeat_description_line.setVisible(View.VISIBLE)

                        tv_next_follow_up_data_container.setVisible(View.GONE)
                        next_follow_up_data_line.setVisible(View.GONE)
                        next_follow_up_time_container.setVisible(View.GONE)
                    } else {
                        tv_cause_of_defeat_container.setVisible(View.GONE)
                        cause_of_defeat_line.setVisible(View.GONE)
                        tv_defeat_description_container.setVisible(View.GONE)
                        defeat_description_line.setVisible(View.GONE)

                        tv_next_follow_up_data_container.setVisible(View.VISIBLE)
                        next_follow_up_data_line.setVisible(View.VISIBLE)
                        next_follow_up_time_container.setVisible(View.VISIBLE)
                    }
                }
            }
        }

        //意向车型
        intentional_model_container.onClick {
            InformationDataConfig.getInformationConfig()?.series?.let {
                val options1Items = ArrayList<String>()
                val options2Items = ArrayList<ArrayList<String>>()
                for (item in it) {
                    options1Items.add(item.seriesName!!)

                    val list2 = ArrayList<String>()
                    options2Items.add(list2)

                    item.carModels?.let {
                        for (item2 in it) {
                            list2.add(item2?.modelName ?: "")
                        }
                    }
                }
                CustomPickerView.show(activity!!, options1Items, options2Items) { options1, options2 ->
                    tv_car_intentional.text = it[options1].seriesName
                    tv_intentional_model.text = it[options1].carModels?.get(options2)?.modelName

                    tv_car_intentional.tag = it[options1].seriesCode
                    tv_intentional_model.tag = it[options1].carModels?.get(options2)?.modelCode
                }
            }
        }

        //车辆颜色
        tv_vehicle_color_container.onClick {
            InformationDataConfig.getInformationConfig()?.color?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.pickerViewText, item))
                }

                CustomPickerView.show(activity!!, list) {
                    tv_vehicle_color.text = it.text
                    tv_vehicle_color.tag = (it.data as Color).colorCode

                    tv_vehicle_color.textColor = getColor(R.color.base_font_color_black)
                }
            }
        }

        //跟进方式
        tv_follow_up_container.onClick {
            InformationDataConfig.getInformationConfig()?.foType?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.dictValue ?: "", item))
                }

                CustomPickerView.show(activity!!, list) {
                    tv_follow_up.text = it.text
                    tv_follow_up.tag = (it.data as FoType).dictKey
                }
            }
        }

        tv_next_follow_up_data_container.onClick {
            val calendar = Calendar.getInstance()
            val dialog = DatePickerDialog(
                    context, { _, year, month, dayOfMonth ->

                val currentTime = TimeUtils.formatYearMonthAndDay(System.currentTimeMillis()).split("-")

                if (year < currentTime[0].toInt() || (month + 1) < currentTime[1].toInt() || dayOfMonth < currentTime[2].toInt()) {
                    showToast("必须大于当前时间")
                    return@DatePickerDialog
                }

                tv_next_follow_up_data.text = year.toString() + "-" + (month + 1) + "-" + dayOfMonth
            },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            )
            dialog.show()
        }

        next_follow_up_time_container.onClick {
            val calendar = Calendar.getInstance()
            val dialog = TimePickerDialog(
                    context, { view, hourOfDay, minute ->
                tv_next_follow_up_time.text = hourOfDay.toString() + ":" + minute
            },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
            )
            dialog.show()
        }

        tv_cause_of_defeat_container.onClick {
            val fragment = SubDefeatReasonFragment.newInstance()
            fragment.actionSuccess = { result ->
                if (null == defeatReasonList) {
                    defeatReasonList = ArrayList()
                } else {
                    defeatReasonList?.clear()
                }

                val textShow = StringBuilder()

                InformationDataConfig.getInformationConfig()?.reasonForFailure?.let {
                    for (index in result) {
                        val entity = it.get(index)
                        defeatReasonList?.add(entity.dictKey!!)
                        textShow.append(entity.dictValue).append(",")
                    }
                }

                if (textShow.isNotEmpty()) {
                    tv_cause_of_defeat.text = textShow.substring(0, textShow.length - 1)
                }

            }
            start(fragment)
        }
    }

    private fun save() {
        val foProcesse = ev_process_record.text.toString().trim()
        if (foProcesse.isEmpty()) {
            showToast("请输入过程记录")
            return
        }

        val leadsLevel = tv_customer_level.tag  as? Int
        if (leadsLevel == null) {
            showToast("请选择客户级别")
            return
        }

        val carModel = tv_intentional_model.tag as? String
        if (carModel == null) {
            showToast("请选择意向车型")
            return
        }

        val seriesCode = tv_car_intentional.tag  as? String

        val colorCode = tv_vehicle_color.tag as? String

        val foType = tv_follow_up.tag as? Int
        if (foType == null) {
            showToast("请选择跟进方式")
            return
        }

        //战败
        if (leadsLevel == 30440030) {
            if (defeatReasonList == null || defeatReasonList!!.size <= 0) {
                showToast("请选择战败原因")
                return
            }

            val defeatDescription = tv_defeat_description.text.toString().trim()
            viewModel?.saveByFailed(
                    pcNo,
                    leadsLevel,
                    foProcesse,
                    foType,
                    seriesCode!!,
                    carModel,
                    colorCode,
                    defeatDescription,
                    JSONObject.toJSON(defeatReasonList).toString()
            )
        } else {
            val dataText =
                    tv_next_follow_up_data.text.toString().trim() + " " + tv_next_follow_up_time.text.toString().trim() + ":0"


            val time = TimeUtils.string2Millis(dataText)
            if (time < System.currentTimeMillis()) {
                showToast("必须大于当前时间")
                return
            }
            viewModel?.save(pcNo, leadsLevel, foProcesse, time.toString(), foType, seriesCode!!, carModel, colorCode)
        }
    }

    override fun onBackPressedSupport(): Boolean {
        back()
        return true
    }

    private fun back() {
        MaterialDialog(activity!!)
                .title(R.string.base_warn)
                .message(R.string.sub_has_modify_info_and_has_not_save)
                .positiveButton(R.string.base_sure, click = {
                    pop()
                }).negativeButton(R.string.base_cancel)
                .show()
    }

    companion object {

        private const val PAIR_NAME = "pair_name"
        private const val PAIR_LEVEL = "pair_level"
        private const val MODEL_CODE = "model_code"
        private const val SERIES_CODE = "series_code"
        private const val COLOR_CODE = "color_code"
        private const val NEXT_FO_DATE = "NEXT_FO_DATE"
        private const val PC_NO = "PC_NO"

        fun newInstance(
                name: String,
                level: Int,
                seriesCode: String,
                modelCode: String,
                colorCode: String,
                nextFoDate: Long,
                pcNo: String
        ): NewFollowUpFragment {
            val fragment = NewFollowUpFragment()
            fragment.arguments = bundleOf(
                    Pair(PAIR_NAME, name),
                    Pair(PAIR_LEVEL, level),
                    Pair(SERIES_CODE, seriesCode),
                    Pair(MODEL_CODE, modelCode),
                    Pair(COLOR_CODE, colorCode),
                    Pair(NEXT_FO_DATE, nextFoDate),
                    Pair(PC_NO, pcNo)
            )
            return fragment
        }
    }
}