package com.krt.submarine.sms

import com.krt.business.ext.toCustom
import com.krt.frame.ext.onClick
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig
import com.krt.frame.frame.toolbar.style.ToolBarStyle
import com.krt.submarine.R
import kotlinx.android.synthetic.main.sub_fragment_sms_template.*

class SmsTemplateFragment : BaseFragment() {

//    var actionCallBack: ((String, String) -> Unit)? = null

    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(
                    R.layout.sub_fragment_sms_template,
                    toolBarStyle = ToolBarStyle.NORMAL,
                    middleTitle = "短信模板"
            ).toCustom(customAll = true)

    override fun initView() {
        come_to_shop_sms.onClick {
            //            actionCallBack?.invoke("来店短信", "XXXXX/来店短信  ")
            this.pop()
        }

        go_to_the_store_sms.onClick {
            //            actionCallBack?.invoke("去店短信", "XXXXX/去店短信、事事如意:XXXXX")
            this.pop()
        }

        booking_a_message.onClick {
            //            actionCallBack?.invoke("订车短信", "XXXXX/订车短信")
            this.pop()
        }

        holiday_message.onClick {
            //            actionCallBack?.invoke("节日短信", "XXXXX/节日短信")
            this.pop()
        }
    }

}