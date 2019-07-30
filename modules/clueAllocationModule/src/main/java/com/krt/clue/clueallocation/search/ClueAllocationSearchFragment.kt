package com.krt.clue.clueallocation.search

import android.arch.lifecycle.Observer
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.krt.base.ext.initSwipeRefreshLayout
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.clueallocation.search.adapter.ClueAllocationSearchAdapter
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_clue_allocation_search.*
import kotlinx.android.synthetic.main.clue_view_clue_allocation_operation.*
import org.jetbrains.anko.bundleOf

class ClueAllocationSearchFragment : BaseLceFragment<ClueAllocationSearchViewModel>() {

    private lateinit var mAdapter: ClueAllocationSearchAdapter

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(R.layout.clue_fragment_clue_allocation_search,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "线索分配"
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
        check_all.setVisible(View.INVISIBLE)

        if (arguments == null) {
            return
        }

        val searchKey = arguments!!.getString(SEARCH_KEY) ?: ""

        mAdapter = ClueAllocationSearchAdapter()

        initSwipeRefreshLayout(mAdapter, base_lceRefreshView, base_lceRecyclerView, itemDecorationEnabled = true)
        {
            viewModel?.loadData(searchKey)
        }
    }

    override fun initViewClickListener() {
        super.initViewClickListener()
        name_container.onClick {
            viewModel?.showCustomerList()
        }

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
    }

    companion object {

        private const val SEARCH_KEY = "search_key"

        fun newInstance(searchKey: String): ClueAllocationSearchFragment {
            val fragment = ClueAllocationSearchFragment()
            fragment.arguments = bundleOf(Pair(SEARCH_KEY, searchKey))
            return fragment
        }
    }

}