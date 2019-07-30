package com.krt.clue.customerlevel.list

import android.arch.lifecycle.Observer
import android.view.View
import android.widget.TextView
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.clueallocation.eventbus.ClueAllocationRefreshEventBus
import com.krt.clue.customerlevel.detail.CustomerLevelDetailFragment
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_customer_level_list.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CustomerLevelListFragment : BaseLceFragment<CustomerLevelListViewModel>() {

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(R.layout.clue_fragment_customer_level_list,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "客户等级"
            ).toCustom(
                    customAll = true
            )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.resultDataLiveData?.observe(this, Observer {
            it?.let { list ->
                for (item in list) {
                    val view = View.inflate(context, R.layout.clue_item_customer_level_list_view, null)
                    view.findViewById<TextView>(R.id.tv_customer_level).text = item.dictValue
                    view.findViewById<TextView>(R.id.tv_customer_level_count).text = item.cueCount.toString()

                    view.onClick {
                        item.leadsLevel?.let {
                            start(CustomerLevelDetailFragment.newInstance(it))
                        }
                    }
                    ll_container.addView(view)
                }
            }
        })
    }

    override fun initView() {
    }

    override fun onLoadFirstComingData(): Boolean {
        viewModel?.loadData()
        return true
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onRefresh(event: ClueAllocationRefreshEventBus) {
        onLoadFirstComingData()
    }

}