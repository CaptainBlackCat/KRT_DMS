package com.krt.home.widgets

import android.animation.ValueAnimator
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.krt.base.ext.getDimen
import com.krt.home.R

class RankingListView(name: String, count: Int, maxCount: Int, maxNameCount: Int, context: Context) :
        LinearLayout(context) {

    init {
        val view = View.inflate(context, R.layout.home_ranking_list_view, this)

        val tvName = view.findViewById<TextView>(R.id.name)
        val progress = view.findViewById<View>(R.id.percent_view)

        tvName.maxEms = maxNameCount
        tvName.minEms = maxNameCount

        tvName.text = name
        view.findViewById<TextView>(R.id.count).text = count.toString()

        val maxProcessViewLength = getDimen(R.dimen.base_240)

        val showLength = count * 1.0f / maxCount * maxProcessViewLength

        val animator = ValueAnimator.ofInt(0, showLength.toInt())
        animator.duration = 1500
        animator.addUpdateListener {
            val params = progress.layoutParams
            params.width = it.animatedValue as Int
            progress.layoutParams = params
        }
        animator.start()
    }


}