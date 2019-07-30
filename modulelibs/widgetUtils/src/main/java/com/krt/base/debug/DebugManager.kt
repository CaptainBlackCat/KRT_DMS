package com.krt.base.debug


/**
 * Created by admin on 2018/1/8.
 */

class DebugManager {

    private var lastTime: Long = 0
    private var index = 0

    fun fastClickShow() {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastTime <= 1000) {
            index++
        } else {
            index = 0
        }
        lastTime = currentTime

        if (index > 20) {
            index = 0
            lastTime = 0

            //TODO
//            SchemeUrlHandler.directToFragment(TO_DEBUG)
        }

    }
}
