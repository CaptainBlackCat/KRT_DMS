package com.krt.clue.clueallocation.page

import android.arch.lifecycle.Observer
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.clueallocation.eventbus.ClueAllocationRefreshEventBus
import com.krt.clue.clueallocation.search.ClueAllocationSearchFragment
import com.krt.clue.clueallocation.wait.ClueWaitToAllocationFragment
import com.krt.clue.customerlevel.list.CustomerLevelListFragment
import com.krt.clue.saleconsultant.list.SaleConsultantListFragment
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_clue_allocation_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = RouterPathClueAllocation.CLUE_ALLOCATION_PAGE, name = "线索分配")
class ClueAllocationListFragment : BaseLceFragment<ClueAllocationListViewModel>() {

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.clue_fragment_clue_allocation_list,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "线索分配"
    ).toCustom(
            customAll = true
    )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.resultDataLiveData?.observe(this, Observer {
            it?.let { data ->
                tv_clue_to_be_assigned.text = data.unallocated.toString()
                tv_on_the_job_sales_consultant.text = data.onDuty.toString()
                tv_resignation_sales_consultant.text = data.leaveOffice.toString()
                tv_customer_level.text = data.customers.toString()
            }
        })
    }

    override fun initView() {
    }

    override fun initViewClickListener() {
        clue_to_be_assigned_container.onClick {
            start(ClueWaitToAllocationFragment.newInstance())
        }

        on_the_job_sales_consultant_continaer.onClick {
            //TODO 接口设计有问题
            start(SaleConsultantListFragment.newInstance(true))
        }

        resignation_sales_consultant_container.onClick {
            start(SaleConsultantListFragment.newInstance(false))
        }

        customer_level_container.onClick {
            start(CustomerLevelListFragment())
        }

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //关闭软键盘
                hideSoftInput()
                start(ClueAllocationSearchFragment.newInstance(et_search.text.toString().trim()))
            }
            true
        }
    }


    override fun onLoadFirstComingData(): Boolean {
        viewModel?.loadData()
        return true
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: ClueAllocationRefreshEventBus) {
        onLoadFirstComingData()
    }

}