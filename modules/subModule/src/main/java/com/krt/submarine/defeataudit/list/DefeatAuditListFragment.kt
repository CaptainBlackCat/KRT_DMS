package com.krt.submarine.defeataudit.list

import android.arch.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.business.ext.toCustom
import com.krt.component.routhpath.RouterPathSubmarine
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import com.krt.submarine.defeataudit.detail.DefeatAuditDetailFragment
import com.krt.submarine.defeataudit.eventbus.DefeatAuditEventBus
import com.krt.submarine.defeataudit.list.adapter.DefeatAuditListAdapter
import com.krt.submarine.defeataudit.list.entity.DefeatAuditListEntity
import kotlinx.android.synthetic.main.sub_fragment_news_letter.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = RouterPathSubmarine.DEFEATED_AUDIT, name = "战败待审核")
class DefeatAuditListFragment : BaseLceFragment<DefeatAuditListViewModel>() {

    private lateinit var mAdapter: DefeatAuditListAdapter

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_defeat_audit_list,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "战败待审核"
    ).toCustom(customAll = true)

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.listDataLiveData?.observe(this, Observer {
            it?.let { data ->
                mAdapter.setNewData(data)
            }
        })
    }

    override fun initView() {
        mAdapter = DefeatAuditListAdapter()

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData()
        }

        mAdapter.setOnItemClickListener { adapter, _, position ->
            start(
                    DefeatAuditDetailFragment.newInstance(
                            (adapter.data[position] as DefeatAuditListEntity).foBy
                                    ?: ""
                    )
            )
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onContainerAnimationEnd(event: DefeatAuditEventBus) {
        base_lceRefreshView.refresh()
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

}