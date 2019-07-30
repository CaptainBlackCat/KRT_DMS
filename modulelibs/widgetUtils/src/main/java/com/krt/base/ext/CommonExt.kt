package com.krt.base.ext

import android.os.Handler
import org.greenrobot.eventbus.EventBus

//eventBus
fun registerEvents(any: Any) {
    EventBus.getDefault().register(any)
}

fun unregisterEvents(any: Any) {
    EventBus.getDefault().unregister(any)
}

fun postEvent(event: Any) {
    EventBus.getDefault().post(event)
}

fun postSticky(event: Any) {
    EventBus.getDefault().postSticky(event)
}

fun unPostSticky(event: Any) {
    EventBus.getDefault().removeStickyEvent(event)
}

fun handlerDelay(time: Long = 1000, action: () -> Unit) {
    Handler().postDelayed({ action.invoke() }, time)
}

//just for test
fun testForDelay(action: () -> Unit) {
    Handler().postDelayed({ action.invoke() }, 500)
}

fun testForDelay2(action: () -> Unit) {
    Handler().postDelayed({ action.invoke() }, 3000)
}

fun testForDelay3(action: () -> Unit) {
    Handler().postDelayed({ action.invoke() }, 1500)
}
