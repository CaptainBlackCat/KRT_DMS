package com.krt.submarine.record.history.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.utils.TimeUtils
import com.krt.submarine.R
import com.krt.submarine.customerinfo.bean.Followup

class HistoryRecordAdapter :
        BaseQuickAdapter<Followup, BaseViewHolder>(R.layout.sub_item_history_record_view, null) {

    override fun convert(helper: BaseViewHolder, item: Followup) {
        helper.getView<TextView>(R.id.history_time)?.text = TimeUtils.formatTimeStamp(item.foDate ?: 0)
        helper.getView<TextView>(R.id.history_content)?.text = item.foProcesse
    }

}
