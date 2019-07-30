package com.krt.submarine.sms

import android.text.SpannableStringBuilder
import com.krt.base.ext.getColor
import com.krt.base.utils.CallAndSmsPermissionUtils
import com.krt.business.ext.toCustom
import com.krt.business.user.UserDefault
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.frame.frame.toolbar.style.ToolBarViewStyle
import com.krt.submarine.R
import kotlinx.android.synthetic.main.sub_fragment_send_sms.*
import org.jetbrains.anko.bundleOf

class SendSmsFragment : BaseFragment() {

    private var phone: String? = null

    override fun initToolBarConfig() = ToolBarConfig(
            R.layout.sub_fragment_send_sms,
            toolBarStyle = ToolBarStyle.NORMAL,
            middleTitle = "发送短信",
            rightViewStyle = ToolBarViewStyle.TEXT,
            rightViewTextFontColor = getColor(R.color.base_white),
            rightViewText = "发送",
            rightViewClickListener = {
                val sendText = sms_text.text.toString().trim() + "\n" + consultant_signature.text.toString().trim()

                CallAndSmsPermissionUtils.checkSendSMSPermission(this, phone ?: "", sendText)
            }
    ).toCustom(customAll = true)

    override fun initView() {
        name.text = arguments?.getString(NAME)

        phone = arguments?.getString(MOBILE) ?: ""

        consultant_signature.text = SpannableStringBuilder("您在杭州KRT店的专属顾问" + UserDefault.empName)
    }

    override fun initViewClickListener() {
        super.initViewClickListener()

        sms_template_container.onClick {
            val fragment = SmsTemplateFragment()
            //TODO
//            fragment.actionCallBack = { name, content ->
//                sms_template.text = name
//                sms_text.text = SpannableStringBuilder(content.replace("XXXXX", "123"))
//            }

            start(fragment)
        }
    }

    companion object {

        private const val NAME = "name"
        private const val MOBILE = "mobile"

        fun newInstance(name: String, mobile: String): SendSmsFragment {
            val fragment = SendSmsFragment()
            fragment.arguments =
                    bundleOf(Pair(NAME, name), Pair(MOBILE, mobile))
            return fragment
        }

    }
}