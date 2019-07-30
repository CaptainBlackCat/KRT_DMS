package com.krt.clue.customerlevel.index

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.SpannableStringBuilder
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.base.widgets.index.suspension.SuspensionDecoration
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.config.information.bean.LeadsLevel
import com.krt.business.ext.toCustom
import com.krt.clue.R
import com.krt.clue.customerlevel.index.adapter.CustomerIndexAdapter
import com.krt.clue.customerlevel.index.bean.CustomerIndexEntity
import com.krt.component.ModuleServiceFactory
import com.krt.component.eventbus.GlobalCustomerIndexFreshEventBus
import com.krt.component.routhpath.RouterPathClueAllocation
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import kotlinx.android.synthetic.main.clue_fragment_customer_index.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.*

@Route(path = RouterPathClueAllocation.POTENTIAL_CUSTOMERS)
class CustomerIndexFragment : BaseLceFragment<CustomerIndexViewModel>() {

    private var mAdapter: CustomerIndexAdapter? = null
    private var mData = ArrayList<CustomerIndexEntity>()
    private var mDecoration: SuspensionDecoration? = null

    private var empId: Int? = null
    private var leadsLevel: Int? = null

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(R.layout.clue_fragment_customer_index,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "潜在客户"
            ).toCustom(
                    customAll = true
            )

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.resultDataLiveData?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                et_search.text = SpannableStringBuilder("")
                initData(it)
            }
        })

        viewModel?.searchLiveData?.observe(this, android.arch.lifecycle.Observer {
            it?.let {
                initData(it)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initView() {
        mAdapter = CustomerIndexAdapter()
        recycler_view.adapter = mAdapter
        val mManager = LinearLayoutManager(context)
        recycler_view.layoutManager = mManager
        mDecoration = SuspensionDecoration(context!!, mData)
        mDecoration!!.isShowSuspension = false
        recycler_view.addItemDecoration(mDecoration!!)

        mAdapter!!.setOnItemClickListener { adapter, view, position ->
            ModuleServiceFactory.instance.submarineService?.direct2SubmarineCustomerInfoFragment(
                    this,
                    (adapter.data[position] as CustomerIndexEntity).pcNo ?: ""
            )
        }

        index_bar.setmPressedShowTextView(tv_side_bar_hint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager)

        et_search.imeOptions = EditorInfo.IME_ACTION_SEARCH
        et_search.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                //关闭软键盘
                hideSoftInput()
                viewModel?.search(et_search.text.toString().trim())
            }
            true
        }
    }

    override fun initViewClickListener() {
        super.initViewClickListener()

        leads_level_container.onClick {
            InformationDataConfig.getInformationConfig()?.leadsLevel?.let {
                val list = ArrayList<SimplePickerDate>()

                for (item in it) {
                    list.add(SimplePickerDate(item.pickerViewText, item))
                }

                list.add(0, SimplePickerDate("全部", LeadsLevel(-1, -1, null, "全部", "")))

                CustomPickerView.show(activity!!, list) {
                    tv_leads_level.text = it.text


                    leadsLevel = (it.data as LeadsLevel).dictKey
                    loadData()
                }
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

    override fun onLoadFirstComingData(): Boolean {
        arguments?.getInt(RouterPathClueAllocation.POTENTIAL_CUSTOMERS_LEADS_LEVEL)?.let {
            InformationDataConfig.getInformationConfig()?.leadsLevel?.let { list ->
                for (item in list) {
                    if (it == item.dictKey) {
                        tv_leads_level.text = item.dictValue
                        return@let
                    }
                }
            }

            val _empId = arguments?.getInt(RouterPathClueAllocation.POTENTIAL_CUSTOMERS_EMD_ID)

            if (_empId != 0) {
                empId = _empId
            }
            leadsLevel = it
            loadData()
        }
        return true
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun reloadData(event: GlobalCustomerIndexFreshEventBus) {
        loadData()
    }

    private fun initData(data: List<CustomerIndexEntity>) {
        mAdapter!!.setNewData(data)

        index_bar.setmSourceDatas(data)//设置数据
                .invalidate()
        mDecoration!!.setmDatas(data)
    }

    private fun loadData() {
        viewModel?.loadData(leadsLevel, empId)
    }

}