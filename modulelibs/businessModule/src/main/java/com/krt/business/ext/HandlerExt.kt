package com.krt.business.ext

import com.krt.frame.app.config.KRT

fun postFragmentAnimDelay(action: () -> Unit) {
    KRT.getHandler()?.postDelayed({
        action.invoke()
    }, KRT.getFragmentAnimSkipTime())
}