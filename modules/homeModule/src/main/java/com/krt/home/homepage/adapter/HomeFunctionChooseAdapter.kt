package com.krt.home.homepage.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.base.ext.loadImage
import com.krt.frame.app.config.KRT
import com.krt.home.R
import com.krt.home.providers.entity.HomeFunctionChooseModuleEntity

class HomeFunctionChooseAdapter(data: List<HomeFunctionChooseModuleEntity>) :
        BaseQuickAdapter<HomeFunctionChooseModuleEntity, BaseViewHolder>(R.layout.home_item_function_chooseview, data) {

    override fun convert(helper: BaseViewHolder, item: HomeFunctionChooseModuleEntity) {
        helper.setText(R.id.text, item.moduleName)

        helper.getView<ImageView>(R.id.icon)
                .loadImage(KRT.getApplicationContext(), item.moduleIcon ?: "", enableNullPlaceHolder = true)
    }
}
