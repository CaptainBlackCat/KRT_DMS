package com.krt.base.ext

import android.content.Context
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PreferenceExt<T>(val context: Context, val name: String, val default: T, val prefName: String = "default") :
        ReadWriteProperty<Any?, T> {

    private val prefs by lazy {
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return findPreference(name)
    }

    private fun findPreference(key: String): T {
        return when (default) {
            is Int -> prefs.getInt(key, default)
            is Long -> prefs.getLong(key, default)
            is Float -> prefs.getFloat(key, default)
            is String -> prefs.getString(key, default)
            is Boolean -> prefs.getBoolean(key, default)
            else -> {
                throw IllegalArgumentException("Unsupported type") as Throwable
            }
        } as T
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun putPreference(key: String, value: T) {
        with(prefs.edit())
        {
            when (value) {
                is Int -> putInt(key, value as Int)
                is Long -> putLong(key, value as Long)
                is Float -> putFloat(key, value as Float)
                is String -> putString(key, value as String)
                is Boolean -> putBoolean(key, value as Boolean)
                else -> {
                    throw IllegalArgumentException("Unsupported type")
                }
            }
        }.apply()
    }
}