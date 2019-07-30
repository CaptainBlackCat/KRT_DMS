package com.krt.home.testdrive.start

import android.arch.lifecycle.Observer
import android.content.Intent
import android.text.SpannableStringBuilder
import android.widget.ImageView
import com.krt.base.ext.loadImage
import com.krt.base.ext.postEvent
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.utils.TimeUtils
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.ext.toCustom
import com.krt.frame.ext.onClick
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.home.R
import com.krt.home.testdrive.list.TestDriveListConfig
import com.krt.home.testdrive.list.eventbus.TestDriveRefreshEventBus
import com.krt.home.testdrive.pic.PhotoManager
import com.krt.home.testdrive.start.bean.SexEntity
import kotlinx.android.synthetic.main.home_fragment_start_test_drive.*
import org.jetbrains.anko.bundleOf

class StartTestDriveFragment : BaseLceFragment<StartTestDriveViewModel>() {

    private var mPhotoManager: PhotoManager? = null

    private var testDrive: String? = null

    private var photoDlOriginal: String? = null   //驾驶证正本
    private var photoDlCopy: String? = null     //驾驶证副本图片
    private var photoIdCardFront: String? = null     //身份证照片正面
    private var photoIdCardBack: String? = null      //身份证照片背面

    private var dlIss: Long? = null    //驾驶证签发日期
    private var dlExp: Long? = null   //驾驶证过期日期

    private var customerSex: String? = null   //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private var customerBirthday: String? = null  //客户出生日期

    private var photoAgreement: String? = null      //试驾协议照片

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.home_fragment_start_test_drive,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "补充试乘试驾信息"
    ).toCustom(
            customAll = true
    )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.customerListLiveData?.observe(this, Observer {
            it?.let { list ->

                val arrayList = ArrayList<SimplePickerDate>()
                for (item in list) {
                    arrayList.add(SimplePickerDate(item.empName ?: "", item))
                }

                CustomPickerView.show(activity!!, arrayList)
                {
                    please_select_test_drive_personnel.text = it.text
                    testDrive = (it.data as SalesConsultantEntity).empId.toString()
                }
            }
        })

        viewModel?.saveSuccessLiveData?.observe(this, Observer {
            showToast("保存成功")
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.QUEUE))
            postEvent(TestDriveRefreshEventBus(TestDriveListConfig.DRIVEING))
            this.pop()
        })
    }

    override fun initView() {
        arguments?.let {
            please_enter_the_customer_name.text = SpannableStringBuilder(it.getString(NAME))
            please_enter_customer_phone_number.text = SpannableStringBuilder(it.getString(PHONE))
        }
    }

    override fun initViewClickListener() {
        please_select_test_drive_personnel_contianer.onClick {
            viewModel?.showCustomerList()
        }

        iv_original_driver_s_license.onClick {
            showPhotoManager(it!! as ImageView) {
                photoDlOriginal = it
            }
        }

        iv_copy_of_driver_s_license.onClick {
            showPhotoManager(it!! as ImageView) {
                photoDlCopy = it
            }
        }

        iv_id_card_front.onClick {
            showPhotoManager(it!! as ImageView) {
                photoIdCardFront = it
            }
        }

        iv_back_of_id_card.onClick {
            showPhotoManager(it!! as ImageView) {
                photoIdCardBack = it
            }
        }

        effective_date_container.onClick {
            CustomPickerView.showTimePickerView(activity!!, true) {
                dlIss = TimeUtils.date2Millis(it)
                effective_date.text = TimeUtils.formatYearMonthAndDay(dlIss!!)
            }
        }

        deadline_container.onClick {
            CustomPickerView.showTimePickerView(activity!!, true) {
                dlExp = TimeUtils.date2Millis(it)
                tv_deadline.text = TimeUtils.formatYearMonthAndDay(dlExp!!)
            }
        }

        please_select_gender_container.onClick {
            val arrayList = ArrayList<SimplePickerDate>()
            arrayList.add(SimplePickerDate("男", SexEntity("男", 1)))
            arrayList.add(SimplePickerDate("女", SexEntity("女", 2)))
            arrayList.add(SimplePickerDate("未知", SexEntity("未知", 0)))

            CustomPickerView.show(context!!, arrayList) {
                please_select_gender.text = (it.data as SexEntity).sex
                customerSex = (it.data as SexEntity).sexNum.toString()
            }
        }

        please_select_the_date_of_birth_container.onClick {
            CustomPickerView.showTimePickerView(activity!!, true) {
                customerBirthday = TimeUtils.formatYearMonthAndDay(TimeUtils.date2Millis(it))
                please_select_the_date_of_birth.text = customerBirthday
            }
        }

        test_drive_agreement.onClick {
            showPhotoManager(it!! as ImageView) {
                photoAgreement = it
            }
        }

        btn_submit.onClick {
            save()
        }
    }

    private fun save() {
        val bookNo = arguments?.getString(BOOK_NO)
        if (bookNo == null) {
            showToast("找不到单号")
            return
        }

        if (testDrive == null) {
            showToast("请选择试驾人员")
            return
        }

        if (photoDlOriginal == null || photoDlCopy == null) {
            showToast("请上传驾驶证信息")
            return
        }

        if (dlIss == null) {
            showToast("请选择生效日期")
            return
        }

        if (dlExp == null) {
            showToast("请选择截止日期")
            return
        }

        if (photoIdCardFront == null || photoIdCardBack == null) {
            showToast("请上传身份证信息")
            return
        }

        val customerName = please_enter_the_customer_name.text.toString().trim()
        if (customerName.isEmpty()) {
            showToast("请输入客户姓名")
            return
        }

        val idCardNo = identity_number.text.toString().trim()
        if (idCardNo.isEmpty()) {
            showToast("请输入身份证号")
            return
        }

        val customerMobileNo = please_enter_customer_phone_number.text.toString().trim()
        if (customerMobileNo.isEmpty()) {
            showToast("请输入客户手机号")
            return
        }

        if (customerSex == null) {
            showToast("请选择性别")
            return
        }

        if (customerBirthday == null) {
            showToast("请选择出生日期")
            return
        }

        val customerAddress = please_select_an_address.text.toString().trim()
        if (customerAddress.isEmpty()) {
            showToast("请输入地址")
            return
        }

        if (photoAgreement == null) {
            showToast("请上传试驾协议")
            return
        }


        viewModel?.save(
                bookNo,
                TestDriveListConfig.DRIVEING,
                testDrive!!,
                photoDlOriginal!!,
                photoDlCopy!!,
                dlIss!!,
                dlExp!!,
                photoIdCardFront!!,
                photoIdCardBack!!,
                customerName,
                idCardNo,
                customerMobileNo,
                customerSex!!,
                customerBirthday!!,
                customerAddress,
                photoAgreement!!
        )
    }

    private fun showPhotoManager(view: ImageView, action: (String) -> Unit) {
        mPhotoManager = PhotoManager(this@StartTestDriveFragment) { file ->
            viewModel?.upLoadFile(file) { actionNetFilePath ->
                action.invoke(actionNetFilePath)
                view.loadImage(this@StartTestDriveFragment, file, enableNullPlaceHolder = true)
            }
        }
        mPhotoManager!!.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPhotoManager?.onActivityResult(requestCode, resultCode, data)
    }

    companion object {

        private const val BOOK_NO = "book_no"
        private const val NAME = "name"
        private const val PHONE = "phone"

        fun newInstance(bookNo: String, name: String, phone: String): StartTestDriveFragment {
            val fragment = StartTestDriveFragment()
            fragment.arguments = bundleOf(Pair(BOOK_NO, bookNo), Pair(NAME, name), Pair(PHONE, phone))
            return fragment
        }
    }
}