package com.krt.submarine.newfollowup.defeatreason

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.krt.base.ext.getColor
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.ext.toCustom
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import kotlinx.android.synthetic.main.sub_fragment_defeat_reason.*


class SubDefeatReasonFragment : BaseLceFragment<SubDefeatReasonViewModel>() {

    var actionSuccess: ((ArrayList<Int>) -> Unit)? = null

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_defeat_reason,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "战败原因",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "保存",
            rightViewClickListener = {
                save()
            }
    ).toCustom(customAll = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initView() {
        InformationDataConfig.getInformationConfig()?.reasonForFailure?.let { list ->
            for (item in list) {
                val textView =
                        View.inflate((activity as Context?)!!, R.layout.sub_item_defeat_reason_view, null) as TextView

                textView.text = item.dictValue ?: ""
                defeat_reason_container.addView(textView)
            }

            defeat_reason_container.initClick()
        }
    }

    private fun save() {
        val count = defeat_reason_container.selectedIndexes.size
        if (count < 1) {
            showToast("请选择战败原因")
            return
        }

        actionSuccess?.invoke(defeat_reason_container.selectedIndexes)
        this.pop()
    }

    companion object {

        fun newInstance(): SubDefeatReasonFragment {
            val fragment = SubDefeatReasonFragment()
            return fragment
        }

    }
}
