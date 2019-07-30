package com.krt.submarine.followup.within

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.submarine.R
import com.krt.submarine.customerinfo.CustomerInfoFragment
import com.krt.submarine.followup.SubmarineFollowUpContainerFragment
import com.krt.submarine.followup.eventbus.SubmarineFollowUpContainerEventBus
import com.krt.submarine.followup.eventbus.SubmarineFollowUpRefreshEventBus
import com.krt.submarine.followup.within.adapter.SubmarineFollowUpListAdapter
import com.krt.submarine.newfollowup.NewFollowUpFragment
import kotlinx.android.synthetic.main.sub_fragment_swipe_recycler_view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.bundleOf

class SubmarineFollowUpFragment : BaseLceFragment<SubmarineFollowUpViewModel>() {

    private lateinit var mAdapter: SubmarineFollowUpListAdapter

    private var mStatus = 0

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_swipe_recycler_view)

    override fun initView() {
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)

        if (arguments == null) {
            return
        }
        mStatus = arguments!!.getInt(SUBMARINE_STATUS)

        mAdapter = SubmarineFollowUpListAdapter(this, mStatus)

        mAdapter.actionFollowUp2Detail = {
            it.pcNo.let {
                getParentFragmentByThis()?.start(CustomerInfoFragment.newInstance(it))
            }
        }

        mAdapter.actionFollowUp2New = {
            if (it.seriesCode == null) {
                showToast("意向车型为空，后台数据有问题!!")
            } else {
                getParentFragmentByThis()?.start(NewFollowUpFragment.newInstance(
                        it.pcName!!,
                        it.leadsLevel!!,
                        it.seriesCode,
                        it.modelCode!!,
                        it.colorCode ?: "",
                        it.nextFoDate!!,
                        it.pcNo)
                )
            }
        }

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView) {
            viewModel?.loadData(mStatus)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onContainerAnimationEnd(event: SubmarineFollowUpContainerEventBus) {
        viewModel?.listDataLiveData?.observe(this, Observer {
            mAdapter.setNewData(it)
            (getParentFragmentByThis() as SubmarineFollowUpContainerFragment).updateDataCount(it?.size ?: 0, mStatus)
        })
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: SubmarineFollowUpRefreshEventBus) {
        base_lceRefreshView.refresh()
    }

    companion object {

        private const val SUBMARINE_STATUS = "submarine_status"

        fun newInstance(status: Int): SubmarineFollowUpFragment {
            val fragment = SubmarineFollowUpFragment()
            fragment.arguments = bundleOf(Pair(SUBMARINE_STATUS, status))
            return fragment
        }
    }

}