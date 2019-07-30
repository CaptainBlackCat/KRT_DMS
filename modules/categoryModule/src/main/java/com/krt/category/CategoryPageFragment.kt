package com.krt.category

import com.alibaba.android.arouter.facade.annotation.Route
import com.krt.component.routhpath.RouterPathCategory
import com.krt.frame.frame.fragment.BaseFragment
import com.krt.frame.frame.toolbar.ToolBarConfig

@Route(path = RouterPathCategory.CATEGORY_PAGE, name = "Category模块首页入口")
class CategoryPageFragment : BaseFragment() {
    override fun initToolBarConfig(): ToolBarConfig =
            ToolBarConfig(R.layout.cat_fragment_home_page)

    override fun initView() {
    }

}