package com.krt.submarine.customerinfo

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.krt.base.ext.getColor
import com.krt.base.ext.onClick
import com.krt.base.utils.CallAndSmsPermissionUtils
import com.krt.base.utils.TimeUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.ext.toCustom
import com.krt.business.ext.toSex
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import com.krt.submarine.customername.CustomerNameFragment
import com.krt.submarine.followup.eventbus.SubmarineFollowUpRefreshEventBus
import com.krt.submarine.newfollowup.NewFollowUpFragment
import com.krt.submarine.record.history.HistoryRecordFragment
import kotlinx.android.synthetic.main.sub_fragment_customer_info.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

class CustomerInfoFragment : BaseLceFragment<CustomerInfoViewModel>() {

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_customer_info,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "客户信息",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "跟进",
            rightViewClickListener = {
                viewModel?.listDataLiveData?.value?.let {
                    if (it.nextFoDate != null && it.seriesCode != null && it.modelCode != null) {
                        start(NewFollowUpFragment.newInstance(
                                it.pcName!!,
                                it.leadsLevel!!,
                                it.seriesCode,
                                it.modelCode,
                                it.colorCode ?: "",
                                it.nextFoDate,
                                it.pcNo ?: "")
                        )
                    } else {
                        showToast("后台数据有误!")
                    }
                }
            }
    ).toCustom(customAll = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.listDataLiveData?.observe(this, Observer {
            it?.apply {

                val nameShow = StringBuilder(this.pcName + "(")

                //客户级别
                InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
                    for (itemIn in it) {
                        if (itemIn.dictKey == leadsLevel) {
                            nameShow.append(itemIn.dictValue?.subSequence(0, 1))
                            return@let
                        }
                    }
                }
                nameShow.append(")" + gender?.toSex())

                tv_user_name.text = nameShow.toString()

                tv_user_phone.text = this.pcMobile

                iv_major.setVisible(if (this.focus == 1) View.VISIBLE else View.GONE)

                InformationDataConfig.getInformationConfig()?.infoSource?.let {
                    for (item in it) {
                        if (item.dictKey == this.infoSource) {
                            tv_customer_source.text = item.dictValue
                            return@let
                        }
                    }
                }

                this.testDrive?.let {
                    if (it == 1) {
                        tv_test_drive.text = "是"
                    } else {
                        tv_test_drive.text = "否"
                    }
                }

                this.mortgage?.let {
                    if (it == 1) {
                        tv_whether_to_mortgage.text = "是"
                    } else {
                        tv_whether_to_mortgage.text = "否"
                    }
                }


                tv_area.text = (this.province ?: "") + (this.city ?: "") + (this.county ?: "")
                tv_address.text = this.address

                InformationDataConfig.getInformationConfig()?.series?.let {
                    for (item in it) {
                        if (item.seriesCode == this.seriesCode) {
                            tv_car_intentional.text = item.seriesName

                            item.carModels?.let {
                                for (item2 in it) {
                                    if (item2.modelCode == this.modelCode) {
                                        tv_intentional_model.text = item2.modelName
                                    }
                                }
                            }
                            return@let
                        }
                    }
                }

                InformationDataConfig.getInformationConfig()?.color?.let {
                    for (item in it) {
                        if (item.colorCode == this.colorCode) {
                            tv_vehicle_color.text = item.colorName
                            return@let
                        }
                    }
                }

                val attentionString = StringBuilder()
                if (focusPerformance == 1) {
                    attentionString.append("性能").append(",")
                }
                if (focusExterior == 1) {
                    attentionString.append("外观").append(",")
                }
                if (focusInterior == 1) {
                    attentionString.append("内饰").append(",")
                }
                if (focusPrice == 1) {
                    attentionString.append("价格 ").append(",")
                }
                if (focusOilwear == 1) {
                    attentionString.append("油耗").append(",")
                }

                if (focusAfterSales == 1) {
                    attentionString.append("服务").append(",")
                }

                if (focusAccessories == 1) {
                    attentionString.append("配件").append(",")
                }
                if (focusOther == 1) {
                    attentionString.append("其它").append(",")
                }

                tv_car_buying_attention.text = if (attentionString.isNotEmpty())
                    attentionString.toString().substring(0, attentionString.length - 1) else ""

                tv_first_reception.text = this.firstComing ?: ""

                this.followupList?.let {
                    if (it.isEmpty()) {
                        return@let
                    }
                    val item = it[0]

                    view?.findViewById<TextView>(R.id.history_time)?.text =
                            TimeUtils.formatTimeStamp(item.foDate ?: 0)

                    view?.findViewById<TextView>(R.id.history_content)?.text = item.foProcesse
                }

                iv_user_phone.onClick {
                    CallAndSmsPermissionUtils.checkCallPhonePermission(this@CustomerInfoFragment, pcMobile)
                }

                iv_user_sms.onClick {
                    CallAndSmsPermissionUtils.checkSendSMSPermission(this@CustomerInfoFragment, pcMobile)
                }

                more_history_container.onClick {
                    viewModel?.listDataLiveData?.value?.pcMobile?.let {
                        start(HistoryRecordFragment.newInstance(it))
                    }
                }
            }
        })
    }

    override fun initView() {
    }

    override fun initViewClickListener() {
        super.initViewClickListener()
        transfer_order.onClick {
            showToast("正在开发中")
        }

        //展开更多信息   上面的
        open_more_info_up.onClick {
            if (open_more_info_up.text.toString() == getString(R.string.sub_expand_more_information)) {
                address_container.visibility = View.VISIBLE
                address_line.visibility = View.VISIBLE
                area_line.visibility = View.VISIBLE
                area_container.visibility = View.VISIBLE
                open_more_info_up.text = getString(R.string.sub_collapse_information)
            } else {
                address_container.visibility = View.GONE
                address_line.visibility = View.GONE
                area_line.visibility = View.GONE
                area_container.visibility = View.GONE
                open_more_info_up.text = getString(R.string.sub_expand_more_information)
            }
        }

        //展开更多信息   下面的
        open_more_info_down.onClick {
            if (open_more_info_down.text.toString() == getString(R.string.sub_expand_more_information)) {
                network_order_container.visibility = View.VISIBLE
                network_order_line.visibility = View.VISIBLE
                color_container.visibility = View.VISIBLE
                color_line.visibility = View.VISIBLE
                online_deposit_line.visibility = View.VISIBLE
                online_deposit_container.visibility = View.VISIBLE
                car_buying_attention_line.visibility = View.VISIBLE
                car_buying_attention_container.visibility = View.VISIBLE

                open_more_info_down.text = getString(R.string.sub_collapse_information)
            } else {
                network_order_container.visibility = View.GONE
                network_order_line.visibility = View.GONE
                color_container.visibility = View.GONE
                color_line.visibility = View.GONE
                online_deposit_line.visibility = View.GONE
                online_deposit_container.visibility = View.GONE
                car_buying_attention_line.visibility = View.GONE
                car_buying_attention_container.visibility = View.GONE

                open_more_info_down.text = getString(R.string.sub_expand_more_information)
            }
        }


        listOf<View>(
                name_container,
                test_drive_container,
                mortgage_container,
                area_container,
                address_container,
                car_buying_attention_container
        ).onClick {
            viewModel?.listDataLiveData?.value?.let {
                start(CustomerNameFragment.newInstance(it))
            }
        }
    }


    override fun onLoadFirstComingData(): Boolean {
        val pcNo = arguments!!.getString(PC_NO)
        viewModel?.loadFirstComingData(pcNo ?: "")
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: SubmarineFollowUpRefreshEventBus) {
        val pcNo = arguments!!.getString(PC_NO)
        viewModel?.loadFirstComingData(pcNo)
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

    companion object {

        private const val PC_NO = "pc_no"

        fun newInstance(pcNo: String): CustomerInfoFragment {
            val fragment = CustomerInfoFragment()
            fragment.arguments = bundleOf(Pair(PC_NO, pcNo))
            return fragment
        }
    }

}