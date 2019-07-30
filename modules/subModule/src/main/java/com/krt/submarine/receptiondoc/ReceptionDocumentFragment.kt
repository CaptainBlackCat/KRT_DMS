package com.krt.submarine.receptiondoc

import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.RegexUtils
import com.krt.base.ext.getColor
import com.krt.base.ext.otherwise
import com.krt.base.ext.removeTheBlankSpace
import com.krt.base.ext.yes
import com.krt.base.picker.AddressPickerView
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.utils.ContactsUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.config.information.bean.Color
import com.krt.business.config.information.bean.InfoSource
import com.krt.business.config.information.bean.LeadsLevel
import com.krt.business.ext.toCustom
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.ext.onClick
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import com.krt.submarine.record.history.HistoryRecordFragment
import kotlinx.android.synthetic.main.sub_fragment_order_file.*
import org.jetbrains.anko.textColor


@Route(path = RouterPathSubmarine.RECEPTION_DOC, name = "接待建档")
class ReceptionDocumentFragment : BaseLceFragment<ReceptionDocumentViewModel>() {

    private var province: String? = null
    private var city: String? = null
    private var county: String? = null

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_order_file,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "接待建档",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewText = "保存",
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewClickListener = {
                checkAndThenSave()
            }
    ).toCustom(customAll = true)

    override fun initViewModelLiveData() {
        super.initViewModelLiveData()
        viewModel?.checkPhoneUsableLiveData?.observe(this, Observer {
            MaterialDialog(activity!!)
                    .title(R.string.base_warn)
                    .message(text = it)
                    .positiveButton(R.string.base_look_over, click = {
                        start(HistoryRecordFragment.newInstance(et_user_phone.text.toString().trim()))
                    }).negativeButton(R.string.base_cancel)
                    .show()
        })

        viewModel?.saveSuccessLiveData?.observe(this, Observer {
            showToast("建档成功")
            this.pop()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
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

    override fun initViewClickListener() {
        super.initViewClickListener()
        iv_client_name_check.onClick {
            permissionDelegate?.checkReadContactsPermission {
                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                startActivityForResult(intent, INTENT_REQUEST_CONTACT)
            }
        }

        iv_user_phone_check.onClick {
            val phone = et_user_phone.text?.toString()?.trim() ?: ""
            if (phone.isEmpty()) {
                showToast("请输入客户手机")
                return@onClick
            }
            RegexUtils.isMobileSimple(phone).yes {
                viewModel?.checkPhone(phone)
            }.otherwise {
                showToast("手机号码有误")
            }
        }

        area_container.onClick {
            AddressPickerView().showPickerView(activity!!) { province, city, county ->
                btn_area_choose.text = province + city + county
                this.province = province
                this.city = city
                this.county = county
            }
        }

        //展开更多信息   上面的
        open_more_info_up.onClick {
            if (open_more_info_up.text.toString() == getString(R.string.sub_expand_more_information)) {
                address_container.visibility = View.VISIBLE
                line_address.visibility = View.VISIBLE
                line_area.visibility = View.VISIBLE
                area_container.visibility = View.VISIBLE
                open_more_info_up.text = getString(R.string.sub_collapse_information)
            } else {
                address_container.visibility = View.GONE
                line_address.visibility = View.GONE
                line_area.visibility = View.GONE
                area_container.visibility = View.GONE
                open_more_info_up.text = getString(R.string.sub_expand_more_information)
            }
        }

        //展开更多信息   下面的
        open_more_info_down.onClick {
            if (open_more_info_down.text.toString() == getString(R.string.sub_expand_more_information)) {
                attention_container.visibility = View.VISIBLE
                line_attention.visibility = View.VISIBLE
                color_container.visibility = View.VISIBLE
                line_color.visibility = View.VISIBLE
                open_more_info_down.text = getString(R.string.sub_collapse_information)
            } else {
                attention_container.visibility = View.GONE
                line_attention.visibility = View.GONE
                color_container.visibility = View.GONE
                line_color.visibility = View.GONE
                open_more_info_down.text = getString(R.string.sub_expand_more_information)
            }
        }

        //客户来源
        customer_source_container.onClick {
            InformationDataConfig.getInformationConfig()?.infoSource?.let {

                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.pickerViewText, item))
                }

                CustomPickerView.show(activity!!, list) {
                    btn_customer_source.text = it.text
                    btn_customer_source.tag = (it.data as InfoSource).dictKey

                    btn_customer_source.textColor = getColor(R.color.base_font_color_black)
                }
            }
        }

        //客户级别
        customer_level_container.onClick {
            InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item!!.pickerViewText, item))
                }

                CustomPickerView.show(activity!!, list) {
                    btn_customer_level.text = it.text
                    btn_customer_level.tag = (it.data as LeadsLevel).dictKey

                    btn_customer_level.textColor = getColor(R.color.base_font_color_black)
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
                            list2.add(item2.modelName ?: "")
                        }
                    }
                }
                CustomPickerView.show(activity!!, options1Items, options2Items) { options1, options2 ->
                    btn_car_intentional.text = it[options1].seriesName
                    btn_intentional_model.text = it[options1].carModels?.get(options2)?.modelName

                    btn_car_intentional.tag = it[options1].seriesCode

                    btn_intentional_model.tag = it[options1].carModels?.get(options2)?.modelCode

                    btn_car_intentional.textColor = getColor(R.color.base_font_color_black)
                    btn_intentional_model.textColor = getColor(R.color.base_font_color_black)
                }
            }
        }

        //车辆颜色
        color_container.onClick {
            InformationDataConfig.getInformationConfig()?.color?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.pickerViewText, item))
                }

                CustomPickerView.show(activity!!, list) {
                    btn_color_container.text = it.text
                    btn_color_container.tag = (it.data as Color).colorCode

                    btn_color_container.textColor = getColor(R.color.base_font_color_black)
                }
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        back()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == INTENT_REQUEST_CONTACT) {
            data?.let {
                val uri = data.data
                ContactsUtils.getPhoneContacts(context, uri)?.let { array ->
                    et_client_name.setText(array[0].toString().trim())
                    et_user_phone.setText(array[1].toString().removeTheBlankSpace())
                }
            }
        }
    }

    private fun checkAndThenSave() {
        val customerName = et_client_name.text.toString().trim()
        if (customerName.isEmpty()) {
            showToast("请输入客户名称")
            return
        }

        val customerPhone = et_user_phone.text.toString().trim()
        if (customerPhone.isEmpty()) {
            showToast("请输入客户手机")
            return
        }

        RegexUtils.isMobileSimple(customerPhone).yes {
        }.otherwise {
            showToast("手机号码有误")
            return
        }

        var gender = 0
        if (checkbox_man.isChecked) {
            gender = 1
        }
        if (checkbox_woman.isChecked) {
            gender = 2
        }

        if (gender == 0) {
            showToast("请选择客户性别")
            return
        }

        val customerSource = btn_customer_source.tag as? Int

        if (customerSource == null) {
            showToast("请选择客户来源")
            return
        }

        val customerLevel = btn_customer_level.tag as? Int

        if (customerLevel == null) {
            showToast("请选择客户级别")
            return
        }

        val intentionalModel = btn_intentional_model.tag as? String

        if (intentionalModel == null) {
            showToast("请选择意向车型")
            return
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

        val isFocus = if (is_focus.isChecked) {
            1
        } else {
            0
        }

        val address = tv_address.text.toString().trim()
        val seriesCode = btn_car_intentional.tag as? String
        val colorCode = btn_color_container.tag as? String
        val firstComing = first_coming.text.toString().trim()

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
                customerName,
                customerPhone,
                gender,
                customerSource,
                customerLevel,
                isTestDrive,
                isMortgage,
                isFocus,
                province,
                city,
                county,
                address,
                seriesCode,
                intentionalModel,
                colorCode,
                firstComing,
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
        const val INTENT_REQUEST_CONTACT = 1
    }

}