package com.krt.clue.saleconsultant.list

import android.arch.lifecycle.Observer
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.clueallocation.eventbus.ClueAllocationRefreshEventBus
import com.krt.clue.saleconsultant.list.adapter.SaleConsultantListAdapter
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_swipe_recycler_view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

class SaleConsultantListFragment : BaseLceFragment<SaleConsultantListViewModel>() {

    private lateinit var mAdapter: SaleConsultantListAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.clue_fragment_swipe_recycler_view,
            toolBarStyle = ToolBarStyle.NORMAL
    ).toCustom(customAll = true)

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.resultDataLiveData?.observe(this, Observer {
            mAdapter.setNewData(it)
        })
    }

    override fun initView() {
        if (arguments == null) {
            return
        }

        val isOnDuty = arguments!!.getInt(ON_DUTY_STATE)

        toolBarConfigHelper.getTitleView().text = when (isOnDuty) {
            IS_ON_DUTY -> {
                "在职销售顾问"
            }
            IS_NOT_ON_DUTY -> {
                "离职销售顾问"
            }
            else -> {
                ""
            }
        }

        mAdapter = SaleConsultantListAdapter(this)

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData(isOnDuty)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: ClueAllocationRefreshEventBus) {
        base_lceRefreshView.refresh()
    }

    companion object {

        const val IS_ON_DUTY = 1
        const val IS_NOT_ON_DUTY = 0

        private const val ON_DUTY_STATE = "on_duty_state"

        fun newInstance(isOnDuty: Boolean): SaleConsultantListFragment {
            val fragment = SaleConsultantListFragment()
            fragment.arguments = bundleOf(Pair(ON_DUTY_STATE, if (isOnDuty) IS_ON_DUTY else IS_NOT_ON_DUTY))
            return fragment
        }
    }

}