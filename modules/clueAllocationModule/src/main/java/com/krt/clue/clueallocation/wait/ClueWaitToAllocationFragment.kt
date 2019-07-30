package com.krt.clue.clueallocation.wait

import android.arch.lifecycle.Observer
import com.alibaba.fastjson.JSONObject
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.clueallocation.wait.apapter.ClueWaitToAllocationAdapter
import com.krt.frame.ext.onClick
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_customer_level_detail.*
import kotlinx.android.synthetic.main.clue_view_clue_allocation_operation.*

class ClueWaitToAllocationFragment : BaseLceFragment<ClueWaitToAllocationViewModel>() {

    private lateinit var mAdapter: ClueWaitToAllocationAdapter

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(R.layout.clue_fragment_clue_wait_to_allocation,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "线索待分配"
            ).toCustom(
                    customAll = true
            )

    override fun initViewModelLiveData() {
        viewModel?.resultDataLiveData?.observe(this, Observer {
            it?.let { list ->
                mAdapter.clearHistory()
                mAdapter.setNewData(list)
            }
        })

        viewModel?.customerListLiveData?.observe(this, Observer {
            it?.let { list ->

                val arrayList = ArrayList<SimplePickerDate>()
                for (item in list) {
                    arrayList.add(SimplePickerDate(item.empName ?: "", item))
                }

                CustomPickerView.show(activity!!, arrayList)
                {
                    tv_customer_name.text = it.text
                    tv_customer_name.tag = (it.data as SalesConsultantEntity).empId
                }
            }
        })
    }

    override fun initView() {
        mAdapter = ClueWaitToAllocationAdapter()

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadFirstComingData()
        }

        name_container.onClick {
            viewModel?.showCustomerList()
        }
    }

    override fun initViewClickListener() {
        super.initViewClickListener()
        btn_distribution.onClick {
            val salesConsultant = tv_customer_name.tag as? Int
            if (salesConsultant == null) {
                showToast("请选择新顾问")
                return@onClick
            }
            val list = mAdapter.getCheckedCustomers()

            if (list.isEmpty()) {
                showToast("请选择要分配的客户")
                return@onClick
            }

            viewModel?.distribute(salesConsultant, JSONObject.toJSON(list).toString())
        }

        check_all.onClick {
            mAdapter.checkAll(check_all.isChecked)
        }
    }

    companion object {

        fun newInstance(): ClueWaitToAllocationFragment {
            val fragment = ClueWaitToAllocationFragment()
            return fragment
        }
    }

}