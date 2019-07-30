package com.krt.base.picker

import android.app.Activity
import android.view.View
import com.alibaba.fastjson.JSONObject
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.blankj.utilcode.util.ResourceUtils
import com.krt.base.R
import com.krt.base.ext.getColor
import com.krt.base.picker.addressbean.AddressEntity
import java.util.*

class AddressPickerView {

    fun showPickerView(activity: Activity, actionSuccess: (String, String, String) -> Unit) {
        val JsonData = ResourceUtils.readAssets2String("province.json")//获取assets目录下的json文件数据

        val options1Items = JSONObject.parseArray(JsonData, AddressEntity::class.java)
        val options2Items = ArrayList<ArrayList<String>>()
        val options3Items = ArrayList<ArrayList<ArrayList<String>>>()

        for (i in options1Items.indices) {//遍历省份
            val cityList = ArrayList<String>()//该省的城市列表（第二级）
            val provinceAreaList = ArrayList<ArrayList<String>>()//该省的所有地区列表（第三极）

            for (c in 0 until options1Items.get(i).city?.size!!) {//遍历该省份的所有城市
                val CityName = options1Items.get(i).city?.get(c)?.name ?: ""
                cityList.add(CityName)//添加城市
                val City_AreaList = ArrayList<String>()//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (options1Items[i].city?.get(c)?.area == null
                        || options1Items.get(i).city?.get(c)?.area?.size === 0
                ) {
                    City_AreaList.add("")
                } else {
                    City_AreaList.addAll(options1Items.get(i)?.city?.get(c)?.area as MutableList<String>)
                }
                provinceAreaList.add(City_AreaList)//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(cityList)

            /**
             * 添加地区数据
             */
            options3Items.add(provinceAreaList)
        }


        val pickerView = OptionsPickerBuilder(activity, object : OnOptionsSelectListener {
            override fun onOptionsSelect(options1: Int, options2: Int, options3: Int, v: View?) {
                actionSuccess.invoke(
                        options1Items[options1].pickerViewText,
                        options2Items[options1][options2],
                        options3Items[options1][options2][options3]
                )
            }
        }).setContentTextSize(activity.resources.getDimension(R.dimen.base_5).toInt())
                .setLineSpacingMultiplier(3f)
                .setCancelColor(getColor(R.color.base_app_font_blue))
                .setSubmitColor(getColor(R.color.base_font_color_blue))
                .build<Any>()

        pickerView.setPicker(
                options1Items as List<Any>?, options2Items as List<MutableList<Any>>?,
                options3Items as List<MutableList<MutableList<Any>>>?
        )

        pickerView.show()
    }

}