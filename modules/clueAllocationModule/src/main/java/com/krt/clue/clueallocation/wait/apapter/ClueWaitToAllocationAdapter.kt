package com.krt.clue.clueallocation.wait.apapter

import android.util.SparseBooleanArray
import android.widget.CheckBox
import android.widget.TextView
import collections.forEach
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.clue.R
import com.krt.clue.customerlevel.detail.entity.CustomerLevelEntity
import com.krt.frame.ext.onClick

class ClueWaitToAllocationAdapter :
        BaseQuickAdapter<CustomerLevelEntity, BaseViewHolder>(R.layout.clue_item_clue_wait_to_allocation_view, null) {

    private val historyArray = SparseBooleanArray()

    override fun convert(helper: BaseViewHolder, item: CustomerLevelEntity) {
        helper.getView<TextView>(R.id.tv_phone).text = item.pcMobile
        helper.getView<TextView>(R.id.tv_name).text = item.pcName
        helper.getView<TextView>(R.id.info_source).text = item.infoSource

        item.nextFoDate?.let {
            val time = StringBuilder()
            val lastTime = System.currentTimeMillis() - it

            val absLastTime = Math.abs(lastTime)
            val day = absLastTime * 1f / 1000 / 60 / 60 / 24
            val hour = (absLastTime - day.toInt() * 24 * 60 * 60 * 1000) * 1f / 1000 / 60 / 60
            val minute =
                    (absLastTime - day.toInt() * 24 * 60 * 60 * 1000 - hour.toInt() * 1000 * 60) * 1f / 1000 / 60 / 60
            if (day > 0) {
                time.append(day.toInt()).append("天")
            }

            if (hour > 0) {
                time.append(hour.toInt()).append("时")
            }

            if (minute > 0) {
                time.append(minute.toInt()).append("分")
            }
            if (lastTime < 0) {
                time.insert(0, "-")
            }

            helper.getView<TextView>(R.id.the_remaining_time).text = time
        }

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
