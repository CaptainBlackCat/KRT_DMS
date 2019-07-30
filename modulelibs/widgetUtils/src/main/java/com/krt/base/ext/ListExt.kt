package com.krt.base.ext

import android.view.View
import com.alibaba.fastjson.JSONObject
import com.lzy.okgo.model.HttpParams

fun <E> List<E>.toJson(): String {
    if (this.isEmpty() || this.size % 2 != 0) {
        throw RuntimeException("json transform error,count error !!!!!!!!!") as Throwable
    }

    val jsonMap = HashMap<String, Any?>()

    for ((i, entity) in this.withIndex()) {
        if (i % 2 == 0) {
            jsonMap[entity.toString()] = this[i + 1]
        }
    }
    return JSONObject.toJSONString(jsonMap)
}


fun <E> List<E>.toParams(): HttpParams {
    val httpParams = HttpParams()

    if (this.isEmpty() || this.size % 2 != 0) {
        throw RuntimeException("params transform error,count error !!!!!!!!!")
    }

    for ((i, entity) in this.withIndex()) {

        if (i % 2 != 0) {
            continue
        }

        val value = get(i + 1)

        if (value != null) {
            httpParams.put(entity.toString(), value.toString())
        }
    }

    return httpParams
}


fun List<View>.onClick(l: (v: android.view.View?) -> Unit) {
    for (view in this) {
        view.setOnClickListener {
            l.invoke(view)
        }
    }
}
