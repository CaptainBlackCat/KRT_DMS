package com.krt.clue.clueallocation.search.adapter

import android.util.SparseBooleanArray
import android.widget.CheckBox
import android.widget.TextView
import collections.forEach
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.clue.R
import com.krt.clue.clueallocation.search.entity.ClueAllocationSearchEntity
import com.krt.frame.ext.onClick

class ClueAllocationSearchAdapter :
        BaseQuickAdapter<ClueAllocationSearchEntity, BaseViewHolder>(R.layout.clue_view_clue_allocation_search, null) {

    private val historyArray = SparseBooleanArray()

    override fun convert(helper: BaseViewHolder, item: ClueAllocationSearchEntity) {
        helper.getView<TextView>(R.id.tv_client_s_name).text = item.pcName
        helper.getView<TextView>(R.id.tv_customer_phone).text = item.pcMobile
        helper.getView<TextView>(R.id.tv_source_class).text = item.infoSource
        helper.getView<TextView>(R.id.tv_clue_state).text = item.pcStatus
        helper.getView<TextView>(R.id.tv_sales_consultant).text = item.soldByName

        val checkBox = helper.getView<CheckBox>(R.id.check_box)

        checkBox.isChecked = historyArray.get(helper.adapterPosition)

        checkBox.onClick {
            historyArray.put(helper.adapterPosition, checkBox.isChecked)
        }
    }

    fun getCheckedCustomers(): List<String> {
        val list = ArrayList<String>()
        historyArray.forEach { position, boolean ->
            if (boolean) {
                list.add(data[position].pcNo ?: "")
            }
        }
        return list
    }

    fun clearHistory() {
        historyArray.clear()
    }
}
