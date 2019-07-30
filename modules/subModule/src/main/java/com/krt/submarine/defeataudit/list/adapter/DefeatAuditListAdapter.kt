package com.krt.submarine.defeataudit.list.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.submarine.R
import com.krt.submarine.defeataudit.list.entity.DefeatAuditListEntity

class DefeatAuditListAdapter :
        BaseQuickAdapter<DefeatAuditListEntity, BaseViewHolder>(R.layout.sub_item_defeat_audit_list, null) {

    override fun convert(helper: BaseViewHolder, item: DefeatAuditListEntity) {
        helper.getView<TextView>(R.id.tv_name).text = item.foBy
        helper.getView<TextView>(R.id.tv_defeat_count).text = item.passCount.toString()
        helper.getView<TextView>(R.id.tv_audit_count).text = item.unauditedCount.toString()
    }
}
