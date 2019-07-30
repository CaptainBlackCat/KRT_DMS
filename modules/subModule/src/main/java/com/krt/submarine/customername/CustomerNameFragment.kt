package com.krt.submarine.customername

import android.arch.lifecycle.Observer
import android.content.Context
import android.text.SpannableStringBuilder
import android.view.View
import android.widget.TextView
import com.krt.base.ext.getColor
import com.krt.base.ext.postEvent
import com.krt.base.picker.AddressPickerView
import com.krt.business.ext.toCustom
import com.krt.frame.ext.onClick
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import com.krt.submarine.customerinfo.bean.CustomerInfoEntity
import com.krt.submarine.followup.eventbus.SubmarineFollowUpRefreshEventBus
import kotlinx.android.synthetic.main.sub_fragment_customer_name.*
import org.jetbrains.anko.bundleOf

class CustomerNameFragment : BaseLceFragment<CustomerNameViewModel>() {

    private var province: String? = null
    private var city: String? = null
    private var county: String? = null

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_customer_name,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "客户名称",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "完成",
            rightViewClickListener = {
                save()
            }
    ).toCustom(customAll = true)

    override fun initViewModelLiveData() {
        viewModel?.saveSuccessLiveData?.observe(this, Observer {
            showToast("修改成功")
            postEvent(SubmarineFollowUpRefreshEventBus())
            this.pop()
        })

        viewModel?.listDataLiveData?.observe(this, Observer {
            it?.let {
                et_client_name.text = SpannableStringBuilder(it.pcName)

                if (it.gender?.trim() == "1") {
                    checkbox_man.isChecked = true
                } else {
                    checkbox_woman.isChecked = true
                }

                is_focus.isChecked = it.focus == 1

                if (it.testDrive == 1) {
                    test_drive_check_true.isChecked = true
                } else {
                    test_drive_check_false.isChecked = true
                }

                if (it.mortgage == 1) {
                    whether_to_mortgage_true.isChecked = true
                } else {
                    whether_to_mortgage_false.isChecked = true
                }

                this.province = it.province
                this.city = it.city
                this.county = it.county

                btn_area_choose.text = (this.province ?: "") + (this.city ?: "") + (this.county ?: "")

                tv_address.text = SpannableStringBuilder(it.address ?: "")

                val selectedIndexes = ArrayList<Int>()

                if (it.focusPerformance == 1) {
                    selectedIndexes.add(0)
                }
                if (it.focusPrice == 1) {
                    selectedIndexes.add(1)
                }
                if (it.focusExterior == 1) {
                    selectedIndexes.add(2)
                }
                if (it.focusInterior == 1) {
                    selectedIndexes.add(3)
                }
                if (it.focusOilwear == 1) {
                    selectedIndexes.add(4)
                }
                if (it.focusAfterSales == 1) {
                    selectedIndexes.add(5)
                }
                if (it.focusAccessories == 1) {
                    selectedIndexes.add(6)
                }
                if (it.focusOther == 1) {
                    selectedIndexes.add(7)
                }

                car_buying_attention_container.initSelected(selectedIndexes)
            }
        })
    }

    override fun initView() {
        for (text in resources.getStringArray(R.array.sub_car_buying_attention)) {
            val textView =
                    View.inflate((activity as Context?)!!, R.layout.sub_item_car_buying_attention_view, null) as TextView

            textView.text = text
            car_buying_attention_container.addView(textView)
        }

        car_buying_attention_container.initClick()
    }

    override fun onLoadFirstComingData(): Boolean {
        (arguments?.get(ENTITY) as?CustomerInfoEntity)?.let {
            viewModel?.listDataLiveData?.value = it
        }
        return true
    }

    override fun initViewClickListener() {
        super.initViewClickListener()

        area_container.onClick {
            AddressPickerView().showPickerView(activity!!) { province, city, county ->
                btn_area_choose.text = province + city + county
                this.province = province
                this.city = city
                this.county = county
            }
        }
    }

    private fun save() {
        val customerName = et_client_name.text.toString().trim()
        if (customerName.isEmpty()) {
            showToast("请输入客户名称")
            return
        }

        var gender = 0
        if (checkbox_man.isChecked) {
            gender = 1
        }
        if (checkbox_woman.isChecked) {
            gender = 2
        }

        val isFocus = if (is_focus.isChecked) {
            1
        } else {
            0
        }

        val isTestDrive = if (test_drive_check_true.isChecked) {
            1
        } else {
            0
        }

        val isMortgage = if (whether_to_mortgage_true.isChecked) {
            1
        } else {
            0
        }

        val address = tv_address.text.toString().trim()

        val attentionList = car_buying_attention_container.selectedIndexes

        val focusPerformance = if (attentionList.contains(0)) 1 else 0
        val focusPrice = if (attentionList.contains(1)) 1 else 0
        val focusExterior = if (attentionList.contains(2)) 1 else 0
        val focusInterior = if (attentionList.contains(3)) 1 else 0
        val focusOilwear = if (attentionList.contains(4)) 1 else 0
        val focusAfterSales = if (attentionList.contains(5)) 1 else 0
        val focusAccessories = if (attentionList.contains(6)) 1 else 0
        val focusOther = if (attentionList.contains(7)) 1 else 0

        viewModel?.save(
                viewModel?.listDataLiveData?.value?.pcNo ?: "",
                customerName,
                gender,
                isFocus,
                isTestDrive,
                isMortgage,
                province,
                city,
                county,
                address,
                focusPerformance,
                focusPrice,
                focusExterior,
                focusInterior,
                focusOilwear,
                focusAfterSales,
                focusAccessories,
                focusOther
        )
    }

    companion object {

        private const val ENTITY = "entity"

        fun newInstance(entity: CustomerInfoEntity): CustomerNameFragment {
            val fragment = CustomerNameFragment()
            fragment.arguments = bundleOf(Pair(ENTITY, entity))
            return fragment
        }
    }

}