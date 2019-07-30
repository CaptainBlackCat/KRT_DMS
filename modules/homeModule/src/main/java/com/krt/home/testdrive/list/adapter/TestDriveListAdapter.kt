package com.krt.home.testdrive.list.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.ext.setVisibleAndOtherGone
import com.krt.base.utils.TimeUtils
import com.krt.business.db.AppPostShow
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.home.R
import com.krt.home.testdrive.comment.CommentFragment
import com.krt.home.testdrive.createmodify.NewOrModifyFragment
import com.krt.home.testdrive.list.TestDriveListConfig
import com.krt.home.testdrive.list.bean.TestDriveListEntity

class TestDriveListAdapter(val baseFragment: BaseFragment) :
        BaseQuickAdapter<TestDriveListEntity, BaseViewHolder>(R.layout.home_item_test_drive_view, null) {

    var actionPhone: ((String) -> Unit)? = null

    var actionSMS: ((String) -> Unit)? = null

    var actionCancel: ((TestDriveListEntity, Int) -> Unit)? = null

    var actionOverTestDrive: ((TestDriveListEntity, Int) -> Unit)? = null

    var actionToComment: ((TestDriveListEntity, Int) -> Unit)? = null

    var actionStartTestDrive: ((TestDriveListEntity, Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: TestDriveListEntity) {
        helper.setText(R.id.tv_time, TimeUtils.formatTimeStamp(item.createdTime))
        helper.setText(R.id.name, item.customerName)

        val tvCarType = helper.getView<TextView>(R.id.tv_car_type)

        if (null != item.modelPlateNumber) {
            tvCarType.setVisible(View.VISIBLE)
            tvCarType.text = item.modelPlateNumber
        } else {
            tvCarType.setVisible(View.GONE)
        }

        changeBtnVisible(helper, item.status ?: "")

        helper.getView<View>(R.id.btn_phone).onClick {
            actionPhone?.invoke(item.customerMobileNo ?: "")
        }

        helper.getView<View>(R.id.btn_sms).onClick {
            actionSMS?.invoke(item.customerMobileNo ?: "")
        }

        helper.getView<View>(R.id.btn_modify).onClick {
            baseFragment.getParentFragmentByThis()?.start(
                    NewOrModifyFragment.newInstance(
                            item.customerName,
                            item.customerMobileNo,
                            item.reservationType,
                            item.modelPlateNumber,
                            item.testDrivingTime,
                            item.bookNo ?: ""
                    )
            )
        }

        helper.getView<View>(R.id.btn_cancel).onClick {
            actionCancel?.invoke(item, helper.adapterPosition)
        }

        helper.getView<View>(R.id.btn_start_drive).onClick {
            actionStartTestDrive?.invoke(item, helper.adapterPosition)
        }

        helper.getView<View>(R.id.btn_to_comment).onClick {
            actionToComment?.invoke(item, helper.adapterPosition)
        }

        helper.getView<View>(R.id.btn_end_test_drive).onClick {
            actionOverTestDrive?.invoke(item, helper.adapterPosition)
        }

        helper.getView<View>(R.id.btn_comment_detail).onClick {
            baseFragment.getParentFragmentByThis()?.start(CommentFragment.newInstance(item.bookNo ?: "", helper.adapterPosition, true))
        }
    }

    private fun changeBtnVisible(helper: BaseViewHolder, status: String) {
        var isContainerShow = true

        if (!AppPostShow.isCurrentPostAdviserShow()) {
            helper.getView<View>(R.id.btn_container).setVisible(View.GONE)
            isContainerShow = false
        }

        val container = helper.getView<ViewGroup>(R.id.btn_container)
        when (status) {
            TestDriveListConfig.QUEUE -> {
                helper.setText(R.id.test_drive_type, "待试驾")
                if (isContainerShow)
                    container.setVisibleAndOtherGone(listOf(R.id.btn_modify, R.id.btn_cancel, R.id.btn_start_drive))
            }
            TestDriveListConfig.DRIVEING -> {
                helper.setText(R.id.test_drive_type, "试驾中")
                if (isContainerShow)
                    container.setVisibleAndOtherGone(listOf(R.id.btn_end_test_drive))

            }
            TestDriveListConfig.TOCOMMENT -> {
                helper.setText(R.id.test_drive_type, "待评价")
                if (isContainerShow)
                    container.setVisibleAndOtherGone(listOf(R.id.btn_to_comment))
            }
            TestDriveListConfig.FINISH -> {
                helper.setText(R.id.test_drive_type, "已评价")
                if (isContainerShow)
                    container.setVisibleAndOtherGone(listOf(R.id.btn_comment_detail))
            }
        }
    }
}

