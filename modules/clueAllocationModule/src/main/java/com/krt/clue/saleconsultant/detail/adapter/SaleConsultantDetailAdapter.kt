package com.krt.clue.saleconsultant.detail.adapter

import android.util.SparseBooleanArray
import android.widget.CheckBox
import android.widget.TextView
import collections.forEach
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.clue.R
import com.krt.clue.customerlevel.detail.entity.CustomerLevelEntity
import com.krt.frame.ext.onClick

class SaleConsultantDetailAdapter :
        BaseQuickAdapter<CustomerLevelEntity, BaseViewHolder>(R.layout.clue_item_customer_level_detail2_view, null) {

    private val historyArray = SparseBooleanArray()

    override fun convert(helper: BaseViewHolder, item: CustomerLevelEntity) {
        helper.getView<TextView>(R.id.tv_phone).text = item.pcMobile
        helper.getView<TextView>(R.id.tv_name).text = item.pcName
        helper.getView<TextView>(R.id.tv_sold_by).text = item.soldByName
        helper.getView<TextView>(R.id.info_source).text = item.infoSource

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

    fun checkAll(checked: Boolean) {
        val dataSize = data.size

        for (position in 0 until dataSize) {
            historyArray.put(position, checked)
        }
        notifyDataSetChanged()
    }

    fun clearHistory() {
        historyArray.clear()
    }
}
