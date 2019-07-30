package com.krt.submarine.defeataudit.examine

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.View
import com.krt.base.ext.getColor
import com.krt.base.ext.postEvent
import com.krt.base.picker.CustomPickerView
import com.krt.base.picker.SimplePickerDate
import com.krt.business.bean.SalesConsultantEntity
import com.krt.business.config.information.InformationDataConfig
import com.krt.business.ext.toCustom
import com.krt.frame.ext.onClick
import com.krt.frame.ext.setVisible
import com.krt.frame.ext.showToast
import com.krt.frame.frame.fragment.BaseLceFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import com.krt.submarine.defeataudit.eventbus.DefeatAuditEventBus
import kotlinx.android.synthetic.main.sub_fragment_defeat_audit_examine.*
import org.jetbrains.anko.bundleOf

class DefeatAuditExamineFragment : BaseLceFragment<DefeatAuditExamineViewModel>() {

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_defeat_audit_examine,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "战败待审核",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white)
    ).toCustom(customAll = true)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        InformationDataConfig.loadInformationData()
    }

    override fun initViewModelLiveDataAfterAnimationEnd() {
        viewModel?.upSuccessLiveData?.observe(this, Observer {
            postEvent(DefeatAuditEventBus())
            this.pop()
        })

        viewModel?.customerListLiveData?.observe(this, Observer {
            it?.let { list ->
                val arrayList = ArrayList<SimplePickerDate>()
                for (item in list) {
                    arrayList.add(SimplePickerDate(item.empName ?: "", item))
                }

                CustomPickerView.show(activity!!, arrayList)
                {
                    btn_choose_consultant.text = it.text
                    btn_choose_consultant.tag = (it.data as SalesConsultantEntity).empId
                }
            }
        })
    }

    override fun initView() {
        val style = arguments?.getInt(STYLE) ?: 0
        val foNo = arguments?.getString(FO_NO) ?: ""

        when (style) {
            STYLE_PASS -> {
                tv_opinion.text = "通过意见"
                ev_opinion.text = SpannableStringBuilder("同意")
                ev_opinion.hint = "请输入同意意见"
                toolBarConfigHelper.getRightTextView()?.text = "通过"
                toolBarConfigHelper.getRightTextView()?.onClick {
                    val upText = ev_opinion.text.toString().trim()
                    if (upText.isEmpty()) {
                        showToast("请输入同意意见")
                        return@onClick
                    }

                    viewModel?.upData("PASS", foNo, upText)
                }
            }
            STYLE_REJECT -> {
                tv_opinion.text = "驳回原因"
                ev_opinion.hint = "请输入驳回原因"
                toolBarConfigHelper.getRightTextView()?.text = "驳回"

                toolBarConfigHelper.getRightTextView()?.onClick {
                    val upText = ev_opinion.text.toString().trim()
                    if (upText.isEmpty()) {
                        showToast("请输入驳回原因")
                        return@onClick
                    }

                    viewModel?.upData("REJECT", foNo, upText)
                }
            }
            STYLE_ALLOCATION -> {
                consultant_container.setVisible(View.VISIBLE)
                line.setVisible(View.VISIBLE)
                tv_opinion.text = "分配意见"
                ev_opinion.hint = "请输入分配意见"
                toolBarConfigHelper.getRightTextView()?.text = "分配"

                toolBarConfigHelper.getRightTextView()?.onClick {
                    val empId = btn_choose_consultant.tag as? Int

                    if (empId == null) {
                        showToast("请选择新顾问")
                        return@onClick
                    }

                    val upText = ev_opinion.text.toString().trim()
                    if (upText.isEmpty()) {
                        showToast("请输入分配意见")
                        return@onClick
                    }

                    viewModel?.upData("REDISTRIBUTION", foNo, upText, empId.toString())
                }
            }
        }

        consultant_container.onClick {
            viewModel?.showSalesConsultantList()
        }
    }

    companion object {

        private const val STYLE = "style"
        private const val FO_NO = "fo_no"

        const val STYLE_PASS = 1
        const val STYLE_REJECT = 2
        const val STYLE_ALLOCATION = 3

        fun newInstance(style: Int, foNo: String): DefeatAuditExamineFragment {
            val fragment = DefeatAuditExamineFragment()
            fragment.arguments = bundleOf(Pair(STYLE, style), Pair(FO_NO, foNo))
            return fragment
        }
    }

}