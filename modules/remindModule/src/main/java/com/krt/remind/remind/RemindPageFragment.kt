package com.krt.remind.remind

import android.arch.lifecycle.Observer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.business.db.AppPostShow
import com.krt.business.ext.toCustom
import com.krt.component.ModuleServiceFactory
import com.krt.component.routhpath.RouterPathRemind
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.remind.R
import com.krt.remind.nofollowup.SubNoFollowUpFragment
import kotlinx.android.synthetic.main.rem_fragment_remind_page.*

@Route(path = RouterPathRemind.REMIND_PAGE)
class RemindPageFragment : BaseLceFragment<RemindPageViewModel>() {

    override fun initToolBarConfig(): ToolBarConfig = ToolBarConfig(
            R.layout.rem_fragment_remind_page,
            toolBarStyle = ToolBarStyle.NORMAL,
            toolBarBottomLineVisible = false,
            leftViewStyle = ToolBarViewStyle.ICON,
            middleTitleRes = R.string.rem_remind,
            leftViewIcon = R.drawable.bus_nav_ic_menu,
            leftViewClickListener = {
                ModuleServiceFactory.instance.appService?.openNavigationView()
            }
    ).toCustom(
            customToolBarBackGround = true
    )

    override fun initViewModelLiveData() {
        viewModel?.consultantInfoLiveData?.observe(this, Observer {
            it?.apply {
                timeout_not_followed.text = (overtimeCount ?: 0).toString()
                within_2_days_customer_to_follow_up.text = (withinTwoDaysCount ?: 0).toString()
                current_reservation_customer.text = (thisMonthAppointment ?: 0).toString()
                history_reservation_customer.text = (historicalAppointment ?: 0).toString()
                current_reservation_not_yet_finished_customer.text = (thisMonthUnfinished ?: 0).toString()
                history_reservation_not_yet_finished_customer.text = (historicalUnfinished ?: 0).toString()
            }
        })

        viewModel?.managerInfoLiveData?.observe(this, Observer {
            it?.apply {
                manager_timeout_not_followed.text = (overtimeCount ?: 0).toString()
                manager_unallocated.text = (unallocated ?: 0).toString()
                defeated_for_review.text = (defeatCount ?: 0).toString()
            }
        })
    }

    override fun initView() {
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        loadData()
    }

    override fun initViewClickListener() {
        //todo
//        timeout_not_followed_contianer.onClick {
//            ModuleServiceFactory.instance.submarineService?.direct2SubmarineFollowUpContainerFragment(
//                this.getParentFragmentByThis(), 2
//            )
//        }
//
//        within_2_days_customer_to_follow_up_container.onClick {
//            ModuleServiceFactory.instance.submarineService?.direct2SubmarineFollowUpContainerFragment(
//                this.getParentFragmentByThis(), 0
//            )
//        }
//
//        current_reservation_customer_container.onClick {
//            ModuleServiceFactory.instance.homeService?.direct2TestDriveContainerFragment(
//                this.getParentFragmentByThis(), 1
//            )
//        }
//
//        current_reservation_not_yet_finished_customer_container.onClick {
//            ModuleServiceFactory.instance.homeService?.direct2TestDriveContainerFragment(
//                this.getParentFragmentByThis(), 2
//            )
//        }

        //经理
        manager_timeout_not_followed_container.onClick {
            viewModel?.managerInfoLiveData?.value?.noFollowGroup?.let {
                this.getParentFragmentByThis()?.start(SubNoFollowUpFragment.newInstance(it))
            }
        }

        //TODO
//        manager_unallocated_container.onClick {
//            ModuleServiceFactory.instance.clueAllocationModuleService?.direct2ClueAllocationFragment(this.getParentFragmentByThis())
//        }
//
//        defeated_for_review_container.onClick {
//            ModuleServiceFactory.instance.submarineService?.direct2DefeatAuditListFragment(this.getParentFragmentByThis())
//        }
    }

    override fun onErrorOrNoNetworkRefreshCallBack() {
        super.onErrorOrNoNetworkRefreshCallBack()
        loadData()
    }

    private fun loadData() {
        if (AppPostShow.isCurrentPostAdviserShow()) {
            consultant_container.setVisible(View.VISIBLE)
            viewModel?.loadDataByConsultant()
        } else {
            manager_container.setVisible(View.VISIBLE)
            viewModel?.loadDataByManager()
        }
    }

}