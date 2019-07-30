package com.krt.base.picker

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.krt.base.R
import com.krt.base.ext.getColor
import java.util.*

object CustomPickerView {

    fun show(
            context: Context,
            options1Items: ArrayList<String>,
            options2Items: ArrayList<ArrayList<String>>,
            actionSuccess: (Int, Int) -> Unit
    ) {
        val pickerView = OptionsPickerBuilder(context, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                actionSuccess.invoke(options1, options2)
            }
        }).setContentTextSize(context.resources.getDimension(R.dimen.base_5).toInt())
                .setLineSpacingMultiplier(3f)
                .setCancelColor(getColor(R.color.base_app_font_blue))
                .setSubmitColor(getColor(R.color.base_font_color_blue))
                .build<Any>()

        pickerView.setPicker(options1Items as List<Any>?, options2Items as List<MutableList<Any>>?)

        pickerView.show()
    }

    fun show(context: Context, optionsItems: ArrayList<SimplePickerDate>, actionSuccess: (SimplePickerDate) -> Unit) {
        val pickerView = OptionsPickerBuilder(context, object : OnOptionsSelectListener {

            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                actionSuccess.invoke(optionsItems[options1])
            }
        }).setContentTextSize(context.resources.getDimension(R.dimen.base_5).toInt())
                .setLineSpacingMultiplier(3f)
                .setCancelColor(getColor(R.color.base_app_font_blue))
                .setSubmitColor(getColor(R.color.base_font_color_blue))
                .build<Any>()

        pickerView.setPicker(optionsItems as List<Any>?)

        pickerView.show()
    }

    fun showTimePickerView(
            activity: Activity,
            showDay: Boolean = false,
            showHour: Boolean = false,
            showMinute: Boolean = false,
            actionSuccess: (Date) -> Unit
    ) {
        val pvTime = TimePickerBuilder(activity, OnTimeSelectListener { date, v ->
            actionSuccess.invoke(date)
        })
                .setTimeSelectChangeListener { Log.i("pvTime", "onTimeSelectChanged") }
                .setType(booleanArrayOf(true, true, showDay, showHour, showMinute, false))
                .isDialog(false) //默认设置false ，内部实现将DecorView 作为它的父控件。
                .build()

        val mDialog = pvTime.dialog
        if (mDialog != null) {
            val params = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.BOTTOM
            )

            params.leftMargin = 0
            params.rightMargin = 0
            pvTime.dialogContainerLayout.layoutParams = params

            val dialogWindow = mDialog.window
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_slide_anim)//修改动画样式
                dialogWindow.setGravity(Gravity.BOTTOM)//改成Bottom,底部显示
            }
        }

        pvTime.show()
    }


}