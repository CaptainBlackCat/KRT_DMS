package com.krt.submarine.newsletter.adapter

import android.graphics.Paint
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.krt.frame.ext.onClick
import com.krt.submarine.R
import com.krt.submarine.config.SubmarineModuleConfig
import com.krt.submarine.newsletter.entity.NewsLetterEntity

class NewsLetterAdapter : BaseQuickAdapter<NewsLetterEntity, BaseViewHolder>(R.layout.sub_item_news_letter, null) {

    var actionClick: ((Int, NewsLetterEntity) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: NewsLetterEntity) {
        helper.getView<TextView>(R.id.tv_name).text = item.foBy
        val tvShouldFollowUp = helper.getView<TextView>(R.id.tv_should_follow_up)
        val tvHasFinished = helper.getView<TextView>(R.id.tv_has_finished)
        val tvNotFinished = helper.getView<TextView>(R.id.tv_not_finished)

        tvShouldFollowUp.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tvHasFinished.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tvNotFinished.paint.flags = Paint.UNDERLINE_TEXT_FLAG


        tvShouldFollowUp.text = item.need.toString()
        tvHasFinished.text = item.finish.toString()
        tvNotFinished.text = item.incomplete.toString()

        tvShouldFollowUp.onClick {
            actionClick?.invoke(SubmarineModuleConfig.FOLLOW_UP_STATUS_NEED, item)
        }

        tvHasFinished.onClick {
            actionClick?.invoke(SubmarineModuleConfig.FOLLOW_UP_STATUS_FINISH, item)
        }

        tvNotFinished.onClick {
            actionClick?.invoke(SubmarineModuleConfig.FOLLOW_UP_STATUS_INCOMPLETE, item)
        }
    }
}
