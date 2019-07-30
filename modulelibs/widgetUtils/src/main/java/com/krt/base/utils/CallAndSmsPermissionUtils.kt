package com.krt.base.utils

import android.content.Intent
import android.net.Uri
import com.krt.frame.frame.fragment.BaseFragment

object CallAndSmsPermissionUtils {

    /**
     * 打电话权限校验，同时跳转到打电话界面
     */
    fun checkCallPhonePermission(baseFragment: BaseFragment, phone: String) {
        if (phone.isEmpty()) {
            return
        }

        baseFragment.permissionDelegate?.checkCallPhonePermission {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            baseFragment.context?.startActivity(intent)
        }
    }

    /**
     * 发短信权限校验，同时跳转到打短信界面
     */
    fun checkSendSMSPermission(baseFragment: BaseFragment, phone: String, content: String? = null) {
        if (phone.isEmpty()) {
            return
        }

        baseFragment.permissionDelegate?.checkSendSMSPermission {
            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phone"))
            content?.let {
                intent.putExtra("sms_body", it)
            }
            baseFragment.context?.startActivity(intent)
        }
    }
}