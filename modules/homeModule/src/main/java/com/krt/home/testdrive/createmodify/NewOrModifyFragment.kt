package com.krt.home.testdrive.createmodify

import android.arch.lifecycle.Observer
import android.text.SpannableStringBuilder
import android.view.View
import com.blankj.utilcode.util.RegexUtils
import com.krt.base.ext.getColor
import com.krt.base.ext.no
import com.krt.base.ext.postEvent
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.utils.TimeUtils
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.ext.toCustom
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.home.R
import com.krt.home.testdrive.list.TestDriveListConfig
import com.krt.home.testdrive.list.eventbus.TestDriveRefreshEventBus
import com.krt.home.testdrive.start.StartTestDriveFragment
import kotlinx.android.synthetic.main.home_fragment_new_or_modify_test_drive.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.textColor

class NewOrModifyFragment : BaseLceFragment<NewOrModifyViewModel>() {

    private var customerName: String? = null
    private var customerMobileNo: String? = null
    private var reservationType: String? = null
    private var modelPlateNumber: String? = null
    private var testDrivingTime: Long? = null

    private var rightNowTestDrive: Boolean = false

    override fun initToolBarConfig(): ToolBarConfig = ToolBarConfig(
            R.layout.home_fragment_new_or_modify_test_drive,
            toolBarStyle = ToolBarStyle.NORMAL
    ).toCustom(
            customAll = true
    )

    override fun initViewModelLiveData() {
        viewModel?.createNewSuccessLiveData?.observe(this, Observer {
            if (rightNowTestDrive) {
                it?.let {
                    startWithPop(
                            StartTestDriveFragment.newInstance(
                                    it.bookNo ?: "",
                                    it.customerName ?: "",
                                    it.customerMobileNo ?: ""
                            )
                    )
                }
            } else {
                showToast("创建成功")
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.QUEUE))
                this.pop()
            }
        })
        viewModel?.modifySuccessLiveData?.observe(this, Observer {
            showToast("修改成功")
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.QUEUE))
            this.pop()
        })
    }

    override fun initView() {
        arguments?.containsKey(MODEL_PLATE_NUMBER)
        if (null != arguments && arguments!!.containsKey(CUSTOMER_NAME)) {
            arguments?.apply {
                toolBarConfigHelper.getTitleView().text = "修改试乘试驾"

                btn_modify.setVisible(View.VISIBLE)

                val reservationType = getString(RESERVATION_TYPE).trim()

                if (reservationType == "RIDE") {
                    test_ride.isChecked = true
                } else {
                    test_drive.isChecked = true
                }

                et_customer_name.text = SpannableStringBuilder(getString(CUSTOMER_NAME))
                et_customer_phone.text = SpannableStringBuilder(getString(CUSTOMER_MOBILE_NO))

                license_plate_model.text = SpannableStringBuilder(getString(MODEL_PLATE_NUMBER) ?: "")

                val time = getLong(TEST_DRIVING_TIME)

                test_drive_time.text =
                        if (time > 0) SpannableStringBuilder(TimeUtils.formatTimeStampWithSecond(time)) else ""
            }
        } else {
            toolBarConfigHelper.getTitleView().text = "新增试乘试驾"
            create_btn_container.setVisible(View.VISIBLE)
        }
    }

    override fun initViewClickListener() {

        license_plate_model_container.onClick {
            InformationDataConfig.getInformationConfig()?.platAndModel?.let {
                val list = ArrayList<SimplePickerDate>()
                for (item in it) {
                    list.add(SimplePickerDate(item.modelName!!, item))
                }

                CustomPickerView.show(activity!!, list) {
                    license_plate_model.text = it.text
                    license_plate_model.textColor = getColor(R.color.base_font_color_black)
                }
            }
        }

        test_drive_time_container.onClick {
            CustomPickerView.showTimePickerView(activity!!, true, true, true) {
                val time = TimeUtils.date2Millis(it)
                test_drive_time.text = TimeUtils.formatTimeStampWithSecond(time)
                test_drive_time.tag = time
            }
        }

        btn_modify.onClick {
            if (!checkSuccess()) {
                return@onClick
            }
            val bookNo = arguments!!.getString("BOOK_NO") ?: return@onClick

            viewModel?.modify(
                    customerName,
                    customerMobileNo,
                    reservationType,
                    modelPlateNumber,
                    testDrivingTime,
                    bookNo
            )
        }

        btn_appointment_test_drive.onClick {
            rightNowTestDrive = false
            createTestDrive()
        }

        btn_right_now_test_drive.onClick {
            rightNowTestDrive = true
            createTestDrive()
        }
    }

    private fun checkSuccess(): Boolean {
        reservationType = if (test_drive.isChecked) {
            "DRIVE"
        } else {
            "RIDE"
        }

        modelPlateNumber = license_plate_model.text.toString().trim()
        testDrivingTime = test_drive_time.tag as? Long

        customerName = et_customer_name.text.toString().trim()
        if (customerName!!.isEmpty()) {
            showToast("请输入客户名称")
            return false
        }

        customerMobileNo = et_customer_phone.text.toString().trim()

        if (customerMobileNo!!.isEmpty()) {
            showToast("请输入手机号码")
            return false
        }

        RegexUtils.isMobileSimple(customerMobileNo).no {
            showToast("请输入正确手机号码")
            return false
        }

        return true
    }

    private fun createTestDrive() {
        if (!checkSuccess()) {
            return
        }

        viewModel?.createNewTestDrive(
                customerName,
                customerMobileNo,
                reservationType,
                modelPlateNumber,
                testDrivingTime
        )
    }

    companion object {

        private const val CUSTOMER_NAME = "CUSTOMER_NAME"
        private const val CUSTOMER_MOBILE_NO = "CUSTOMER_MOBILE_NO"
        private const val RESERVATION_TYPE = "RESERVATION_TYPE"
        private const val MODEL_PLATE_NUMBER = "MODEL_PLATE_NUMBER"
        private const val TEST_DRIVING_TIME = "TEST_DRIVING_TIME"
        private const val BOOK_NO = "BOOK_NO"

        fun newInstance(
                customerName: String?,
                customerMobileNo: String?,
                reservationType: String?,
                modelPlateNumber: String?,
                testDrivingTime: Long,
                bookNo: String
        ): NewOrModifyFragment {
            val fragment = NewOrModifyFragment()
            fragment.arguments = bundleOf(
                    Pair(CUSTOMER_NAME, customerName),
                    Pair(CUSTOMER_MOBILE_NO, customerMobileNo),
                    Pair(RESERVATION_TYPE, reservationType),
                    Pair(MODEL_PLATE_NUMBER, modelPlateNumber),
                    Pair(TEST_DRIVING_TIME, testDrivingTime),
                    Pair(BOOK_NO, bookNo)
            )
            return fragment
        }

        fun newInstance(): NewOrModifyFragment {
            val fragment = NewOrModifyFragment()
            return fragment
        }
    }

}