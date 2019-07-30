package com.krt.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.krt.base.R

/**
 *
 *  描述：自动换行Layout,各行数据个数统一
 */
class AutoLineLayoutUnite constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int
) : ViewGroup(context, attrs, defStyleAttr) {

    constructor(context: Context, attrs: AttributeSet? = null) : this(context, attrs, 0)

    /**
     * 两个子控件之间的横向间隙
     */
    private var horizontalSpace = 0f

    /**
     * 两个子控件之间的垂直间隙
     */
    private var verticalSpace = 0f

    private var horizontalCount = 0

    var selectedIndexes = ArrayList<Int>()

    var actionSelectedCount: ((Int) -> Unit)? = null

    init {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.AutoLineLayout)
        horizontalSpace =
                ta.getDimension(R.styleable.AutoLineLayout_horizontalSpace, resources.getDimension(R.dimen.base_15))
        verticalSpace =
                ta.getDimension(R.styleable.AutoLineLayout_verticalSpace, resources.getDimension(R.dimen.base_10))
        horizontalCount =
                ta.getInt(R.styleable.AutoLineLayout_horizontalMaxCount, 3)

        ta.recycle()
    }

    fun initClick() {
        val count = childCount
        for (i in 0 until count) {
            getChildAt(i).setOnClickListener {
                it.isSelected = !it.isSelected

                if (it.isSelected) {
                    selectedIndexes.add(i)
                } else {
                    selectedIndexes.remove(i)
                }

                actionSelectedCount?.invoke(selectedIndexes.count())
            }
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)

        //AT_MOST
        var width = 0
        var height = 0
        var rawWidth = 0//当前行总宽度
        var rawHeight = 0// 当前行高

        var rowIndex = 0//当前行位置
        val count = childCount

        selectedIndexes.clear()
        for (i in 0 until count) {
            val child = getChildAt(i)
            if (child.isSelected) {
                selectedIndexes.add(i)
            }

            child.measure(
                    MeasureSpec.makeMeasureSpec(
                            ((measuredWidth - horizontalSpace * (horizontalCount - 1)) / horizontalCount).toInt(),
                            View.MeasureSpec.EXACTLY
                    ), heightMeasureSpec
            )

            val lp = child.layoutParams as ViewGroup.MarginLayoutParams
            lp.width = ((measuredWidth - horizontalSpace * (horizontalCount - 1)) / horizontalCount).toInt()

            val childWidth = lp.width + lp.leftMargin + lp.rightMargin
            val childHeight = child.measuredHeight + lp.topMargin + lp.bottomMargin
            if ((rawWidth.toFloat() + childWidth.toFloat() + (if (rowIndex > 0) horizontalSpace else 0f)) > (widthSpecSize - paddingLeft - paddingRight)) {
                //换行
                width = Math.max(width, rawWidth)
                rawWidth = childWidth
                height += (rawHeight + verticalSpace).toInt()
                rawHeight = childHeight
                rowIndex = 0
            } else {
                rawWidth += childWidth
                if (rowIndex > 0) {
                    rawWidth += horizontalSpace.toInt()
                }
                rawHeight = Math.max(rawHeight, childHeight)
            }

            if (i == count - 1) {
                width = Math.max(rawWidth, width)
                height += rawHeight
            }

            rowIndex++
        }

        setMeasuredDimension(
                if (widthSpecMode == View.MeasureSpec.EXACTLY) widthSpecSize else width + paddingLeft + paddingRight,
                if (heightSpecMode == View.MeasureSpec.EXACTLY) heightSpecSize else height + paddingTop + paddingBottom
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val containerHeight = measuredHeight
        val viewWidth = r - l
        var leftOffset = paddingLeft
        var topOffset = paddingTop
        var rowMaxHeight = 0
        var rowIndex = 0//当前行位置
        var childView: View
        var w = 0
        val count = childCount
        while (w < count) {
            childView = getChildAt(w)
            if (childView.visibility == View.GONE) {
                w++
                continue
            }

            val lp = childView.layoutParams as ViewGroup.MarginLayoutParams
            // 如果加上当前子View的宽度后超过了ViewGroup的宽度，就换行
            val occupyWidth = lp.leftMargin + lp.width + lp.rightMargin
            if (leftOffset + occupyWidth + paddingRight > viewWidth) {
                leftOffset = paddingLeft  // 回到最左边
                topOffset += (rowMaxHeight + verticalSpace).toInt()  // 换行
                rowMaxHeight = 0

                rowIndex = 0
            }

            val left = leftOffset + lp.leftMargin
            val top = topOffset + lp.topMargin
            val right = leftOffset + lp.leftMargin + lp.width
            val bottom = topOffset + lp.topMargin + childView.measuredHeight

            if (bottom > containerHeight) {
                return
            }

            childView.layout(left, top, right, bottom)

            // 横向偏移
            leftOffset += occupyWidth
            // 试图更新本行最高View的高度
            val occupyHeight = lp.topMargin + childView.measuredHeight + lp.bottomMargin
            if (rowIndex != count - 1) {
                leftOffset += horizontalSpace.toInt()
            }
            rowMaxHeight = Math.max(rowMaxHeight, occupyHeight)
            rowIndex++
            w++
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(context, attrs)
    }

    override fun generateDefaultLayoutParams(): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(p)
    }

    fun initSelected(selectedIndexes: ArrayList<Int>) {
        for (item in selectedIndexes) {
            getChildAt(item).isSelected = true
        }
    }

}

