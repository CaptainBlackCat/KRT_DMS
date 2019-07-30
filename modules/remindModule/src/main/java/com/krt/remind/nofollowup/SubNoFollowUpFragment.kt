package com.krt.remind.nofollowup

import android.view.View
import android.widget.TextView
import com.krt.business.ext.toCustom
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.remind.R
import com.krt.remind.remind.bean.NoFollowGroup
import kotlinx.android.synthetic.main.rem_fragment_sub_no_follow_up.*
import org.jetbrains.anko.bundleOf

class SubNoFollowUpFragment : BaseLceFragment<SubNoFollowUpViewModel>() {

    override fun initToolBarConfig(): ToolBarConfig = ToolBarConfig(
            R.layout.rem_fragment_sub_no_follow_up,
            toolBarStyle = ToolBarStyle.NORMAL,
            toolBarBottomLineVisible = false,
            middleTitle = "潜客超时未跟进"
    ).toCustom(
            customAll = true
    )

    override fun initView() {
        (arguments?.get(LIST) as? List<NoFollowGroup>)?.apply {
            for (item in this) {
                val view = View.inflate(context, R.layout.rem_item_sub_no_follow_up_view, null)
                view.findViewById<TextView>(R.id.name).text = item.soldByName
                view.findViewById<TextView>(R.id.count).text = (item.overtimecount ?: 0).toString()
                container.addView(view)
            }
        }

    }

    override fun onBackPressedSupport(): Boolean {
        this.pop()
        return true
    }

    companion object {

        const val LIST = "list"

        fun newInstance(list: List<NoFollowGroup>): SubNoFollowUpFragment {
            val fragment = SubNoFollowUpFragment()
            fragment.arguments = bundleOf(Pair(LIST, list))
            return fragment
        }
    }

}