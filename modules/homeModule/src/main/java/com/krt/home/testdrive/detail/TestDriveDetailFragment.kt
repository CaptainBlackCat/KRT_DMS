package com.krt.home.testdrive.detail

import com.krt.base.utils.TimeUtils
import com.krt.business.ext.toCustom
import com.krt.business.ext.toSex
import com.krt.frame.ext.obs
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.home.R
import com.krt.home.testdrive.list.TestDriveListConfig
import kotlinx.android.synthetic.main.home_fragment_test_drive_detail.*
import org.jetbrains.anko.bundleOf

class TestDriveDetailFragment : BaseLceFragment<TestDriveDetailViewModel>() {

    override fun initToolBarConfig() =
            ToolBarConfig(R.layout.home_fragment_test_drive_detail,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "试乘试驾详情"
            ).toCustom(
                    customAll = true
            )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        super.initViewModelLiveData()

        viewModel?.resultDataLiveData?.obs(this) {
            it.apply {
                service_type.text = when (status) {
                    TestDriveListConfig.QUEUE -> "待试驾"
                    TestDriveListConfig.DRIVEING -> "试驾中"
                    TestDriveListConfig.TOCOMMENT -> "待评价"
                    TestDriveListConfig.FINISH -> "已评价"
                    else -> ""
                }

                license_plate_type.text = modelPlateNumber
                test_driver.text = soldByName

                testDrivingTime?.let { time ->
                    test_drive_time.text = TimeUtils.formatTimeStamp(time)
                }

                user_name.text = customerName

                customer_number.text = customerMobileNo

                idCardNo?.let {
                    identity_number.text = it
                }

                customerSex?.trim()?.let {
                    sex.text = it.toSex()
                }

                customerBirthday?.let {
                    date_of_birth.text = TimeUtils.formatYearMonthAndDay(it)
                }

                customerAddress?.let {
                    address.text = it
                }

                dlIss?.let {
                    driver_license_effective_date.text = TimeUtils.formatYearMonthAndDay(it)
                }

                dlExp?.let {
                    driver_license_deadline.text = TimeUtils.formatYearMonthAndDay(it)
                }
            }
        }
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        val bookNo = arguments!!.getString(BOOK_NO)

        viewModel?.loadData(bookNo ?: "")
    }

    companion object {

        const val BOOK_NO = "book_no"

        fun newInstance(bookNo: String): TestDriveDetailFragment {
            val fragment = TestDriveDetailFragment()
            fragment.arguments = bundleOf(Pair(BOOK_NO, bookNo))
            return fragment
        }
    }
}