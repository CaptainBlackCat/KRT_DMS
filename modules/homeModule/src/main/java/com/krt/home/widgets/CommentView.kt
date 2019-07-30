package com.krt.home.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.krt.frame.ext.onClick
import com.krt.home.R

class CommentView(context: Context, attrs: AttributeSet? = null) : LinearLayout(context, attrs) {

    private var star1: View
    private var star2: View
    private var star3: View
    private var star4: View
    private var star5: View

    private var currentStarCount = 0

    init {
        val view = View.inflate(context, R.layout.home_comment_view, this)

        star1 = view.findViewById<ImageView>(R.id.progress_1)
        star2 = view.findViewById<ImageView>(R.id.progress_2)
        star3 = view.findViewById<ImageView>(R.id.progress_3)
        star4 = view.findViewById<ImageView>(R.id.progress_4)
        star5 = view.findViewById<ImageView>(R.id.progress_5)

        star1.onClick {
            star1.isSelected = true
            star2.isSelected = false
            star3.isSelected = false
            star4.isSelected = false
            star5.isSelected = false

            currentStarCount = 1
        }

        star2.onClick {
            star1.isSelected = true
            star2.isSelected = true
            star3.isSelected = false
            star4.isSelected = false
            star5.isSelected = false

            currentStarCount = 2
        }

        star3.onClick {
            star1.isSelected = true
            star2.isSelected = true
            star3.isSelected = true
            star4.isSelected = false
            star5.isSelected = false

            currentStarCount = 3
        }

        star4.onClick {
            star1.isSelected = true
            star2.isSelected = true
            star3.isSelected = true
            star4.isSelected = true
            star5.isSelected = false

            currentStarCount = 4
        }

        star5.onClick {
            star1.isSelected = true
            star2.isSelected = true
            star3.isSelected = true
            star4.isSelected = true
            star5.isSelected = true

            currentStarCount = 5
        }
    }

    fun getCurrentStartCount() = currentStarCount

    fun setCurrentStatCount(count: Int) {
        when (count) {
            0 -> {
                star1.isSelected = false
                star2.isSelected = false
                star3.isSelected = false
                star4.isSelected = false
                star5.isSelected = false
            }

            1 -> {
                star1.isSelected = true
                star2.isSelected = false
                star3.isSelected = false
                star4.isSelected = false
                star5.isSelected = false
            }

            2 -> {
                star1.isSelected = true
                star2.isSelected = true
                star3.isSelected = false
                star4.isSelected = false
                star5.isSelected = false
            }

            3 -> {
                star1.isSelected = true
                star2.isSelected = true
                star3.isSelected = true
                star4.isSelected = false
                star5.isSelected = false
            }

            4 -> {
                star1.isSelected = true
                star2.isSelected = true
                star3.isSelected = true
                star4.isSelected = true
                star5.isSelected = false
            }

            5 -> {
                star1.isSelected = true
                star2.isSelected = true
                star3.isSelected = true
                star4.isSelected = true
                star5.isSelected = true
            }
        }

        star1.isEnabled = false
        star2.isEnabled = false
        star3.isEnabled = false
        star4.isEnabled = false
        star5.isEnabled = false

    }

}