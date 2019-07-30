package com.krt.home.testdrive.list

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import com.krt.base.ext.empty
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.base.ext.postEvent
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.utils.CallAndSmsPermissionUtils
import com.krt.base.utils.TimeUtils
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.home.R
import com.krt.home.testdrive.comment.CommentFragment
import com.krt.home.testdrive.detail.TestDriveDetailFragment
import com.krt.home.testdrive.list.adapter.TestDriveListAdapter
import com.krt.home.testdrive.list.bean.TestDriveListEntity
import com.krt.home.testdrive.list.eventbus.TestDriveContainerEventBus
import com.krt.home.testdrive.list.eventbus.TestDriveRefreshEventBus
import com.krt.home.testdrive.start.StartTestDriveFragment
import kotlinx.android.synthetic.main.home_fragment_test_drive_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

class TestDriveListFragment : BaseLceFragment<TestDriveListViewModel>() {

    private var mDriveType = TestDriveListConfig.ALL

    private lateinit var mAdapter: TestDriveListAdapter

    override fun initToolBarConfig() =
            ToolBarConfig(
                    R.layout.home_fragment_test_drive_list
            )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.deleteSuccessLiveData?.observe(this, Observer {
            showToast("取消试驾单成功")
            mAdapter.remove(it!!)
            mAdapter.notifyItemRemoved(it)

            if (mDriveType == TestDriveListConfig.ALL) {
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.QUEUE))
            } else if (mDriveType == TestDriveListConfig.QUEUE) {
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
            }
        })

        viewModel?.endTestDriveSuccessLiveData?.observe(this, Observer {
            if (mDriveType == TestDriveListConfig.ALL) {
                mAdapter.notifyItemChanged(it!!)
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.DRIVEING))
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.TOCOMMENT))
            } else {
                mAdapter.remove(it!!)
                mAdapter.notifyItemRemoved(it)
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.ALL))
                postEvent(TestDriveRefreshEventBus(TestDriveListConfig.TOCOMMENT))
            }
        })
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        mDriveType = arguments!!.getString(TEST_DRIVE_TYPE) ?: TestDriveListConfig.ALL

        if (mDriveType != TestDriveListConfig.ALL) {
            sort_container.setVisible(View.INVISIBLE)
        }

        val currentTime = TimeUtils.date2StringByYearAndMonth(TimeUtils.millis2Date(System.currentTimeMillis()))

        tv_time.text = currentTime
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        mAdapter = TestDriveListAdapter(this)

        mAdapter.actionPhone = {
            CallAndSmsPermissionUtils.checkCallPhonePermission(this, it)
        }

        mAdapter.actionSMS = {
            CallAndSmsPermissionUtils.checkSendSMSPermission(this, it)
        }

        mAdapter.actionCancel = { entity, position ->
            MaterialDialog(activity!!)
                    .title(R.string.base_warn)
                    .message(R.string.home_are_you_sure_you_want_to_cancel_the_test_drive)
                    .positiveButton(R.string.base_sure, click = {
                        viewModel?.delete(entity, position)
                    }).negativeButton(R.string.base_cancel)
                    .show()
        }

        mAdapter.actionOverTestDrive = { entity, position ->
            MaterialDialog(activity!!)
                    .title(R.string.base_warn)
                    .message(R.string.are_you_sure_you_want_to_end_the_test_drive)
                    .positiveButton(R.string.base_sure, click = {
                        viewModel?.endTestDrive(entity, position)
                    }).negativeButton(R.string.base_cancel)
                    .show()
        }

        mAdapter.actionStartTestDrive = { entity, position ->
            val nextFragment = StartTestDriveFragment.newInstance(
                    entity.bookNo ?: "",
                    entity.customerName ?: "",
                    entity.customerMobileNo ?: ""
            )

            //TODO 因分 开始试驾和立即试驾   所以暂时没做，烦，用EVENTBUS就能解决了
//            nextFragment.actionStartSuccess = {
//                if (mDriveType == TestDriveListConfig.ALL) {
//                    entity.status = TestDriveListConfig.FINISH
//                    mAdapter.notifyItemChanged(it)
//                } else {
//                    mAdapter.notifyItemRemoved(it)
//                }
//            }

            getParentFragmentByThis()?.start(nextFragment)
        }

        mAdapter.actionToComment = { entity, position ->
            val nextFragment = CommentFragment.newInstance(entity.bookNo ?: "", position, false)
            nextFragment.actionCommentSuccess = {
                if (mDriveType == TestDriveListConfig.ALL) {
                    entity.status = TestDriveListConfig.FINISH
                    mAdapter.notifyItemChanged(it)
                } else {
                    mAdapter.remove(it)
                    mAdapter.notifyItemRemoved(it)
                }
            }

            getParentFragmentByThis()?.start(nextFragment)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            getParentFragmentByThis()?.start(
                    TestDriveDetailFragment.newInstance((adapter.data[position] as TestDriveListEntity).bookNo ?: ""
                    )
            )
        }

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView) {
            loadData()
        }

        time_container.onClick {
            CustomPickerView.showTimePickerView(activity!!) {
                tv_time.text = TimeUtils.date2StringByYearAndMonth(it)
                loadData()
            }
        }

        if (mDriveType == TestDriveListConfig.ALL) {
            sort_container.onClick {
                val list = ArrayList<SimplePickerDate>()
                list.add(SimplePickerDate("按时间"))
                list.add(SimplePickerDate("按顾问名称"))
                list.add(SimplePickerDate("按状态"))

                CustomPickerView.show(activity!!, list)
                {
                    sort_by.text = it.text
                    loadData()
                }
            }
        }
    }

    private fun loadData() {
        val text = tv_time.text.toString().trim()
        val searchTime = text.subSequence(0, 4).toString() + "-" + text.subSequence(5, 7).toString()

        val searchText = when (if (sort_container.visibility == View.VISIBLE) sort_by.text.toString().trim() else "") {
            "按状态" -> "status"
            "按顾问名称" -> "soldBy"
            else -> String.empty()
        }

        viewModel?.loadData(searchTime, searchText, if (mDriveType != TestDriveListConfig.ALL) mDriveType else "")
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onContainerAnimationEnd(event: TestDriveContainerEventBus) {
        viewModel?.resultDataLiveData?.observe(this, Observer {
            it?.let {
                mAdapter.setNewData(it)
            }
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: TestDriveRefreshEventBus) {
        if (mDriveType == event.type) {
            base_lceRefreshView.refresh()
        }
    }

    companion object {

        const val TEST_DRIVE_TYPE = "test_drive_type"

        fun newInstance(type: String): TestDriveListFragment {
            val fragment = TestDriveListFragment()
            fragment.arguments = bundleOf(Pair(TEST_DRIVE_TYPE, type))
            return fragment
        }
    }
}