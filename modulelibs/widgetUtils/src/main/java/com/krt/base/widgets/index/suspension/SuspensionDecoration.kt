package com.krt.base.widgets.index.suspension

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View

/**
 * 介绍：分类、悬停的Decoration
 * 作者：zhangxutong
 * 邮箱：mcxtzhang@163.com
 * 主页：http://blog.csdn.net/zxt0601
 * 时间： 2016/11/7.
 */

class SuspensionDecoration(context: Context, private var mDatas: List<ISuspensionInterface>?) :
        RecyclerView.ItemDecoration() {
    private val mPaint: Paint
    private val mBounds: Rect//用于存放测量文字Rect

    private val mInflater: LayoutInflater

    private var mTitleHeight: Int = 0//title的高

    private var mHeaderViewCount = 0

    /**
     * 是否显示悬挂
     */
    var isShowSuspension = true

    init {
        mPaint = Paint()
        mBounds = Rect()
        mTitleHeight =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30f, context.resources.displayMetrics).toInt()
        mTitleFontSize =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16f, context.resources.displayMetrics).toInt()
        mPaint.textSize = mTitleFontSize!!.toFloat()
        mPaint.isAntiAlias = true
        mInflater = LayoutInflater.from(context)
    }


    fun setmTitleHeight(mTitleHeight: Int): SuspensionDecoration {
        this.mTitleHeight = mTitleHeight
        return this
    }


    fun setColorTitleBg(colorTitleBg: Int): SuspensionDecoration {
        COLOR_TITLE_BG = colorTitleBg
        return this
    }

    fun setColorTitleFont(colorTitleFont: Int): SuspensionDecoration {
        COLOR_TITLE_FONT = colorTitleFont
        return this
    }

    fun setTitleFontSize(mTitleFontSize: Int): SuspensionDecoration {
        mPaint.textSize = mTitleFontSize.toFloat()
        return this
    }

    fun setmDatas(mDatas: List<ISuspensionInterface>): SuspensionDecoration {
        this.mDatas = mDatas
        return this
    }

    fun getHeaderViewCount(): Int {
        return mHeaderViewCount
    }

    fun setHeaderViewCount(headerViewCount: Int): SuspensionDecoration {
        mHeaderViewCount = headerViewCount
        return this
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child
                    .layoutParams as RecyclerView.LayoutParams
            var position = params.viewLayoutPosition
            position -= getHeaderViewCount()
            //pos为1，size为1，1>0? true
            if (mDatas == null || mDatas!!.isEmpty() || position > mDatas!!.size - 1 || position < 0 || !mDatas!![position].isShowSuspension) {
                continue//越界
            }
            //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
            if (position > -1) {
                if (position == 0) {//等于0肯定要有title的
                    drawTitleArea(c, left, right, child, params, position)

                } else {//其他的通过判断
                    if (null != mDatas!![position].suspensionTag && mDatas!![position].suspensionTag != mDatas!![position - 1].suspensionTag) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        drawTitleArea(c, left, right, child, params, position)
                    } else {
                        //none
                    }
                }
            }
        }
    }

    /**
     * 绘制Title区域背景和文字的方法
     *
     * @param c
     * @param left
     * @param right
     * @param child
     * @param params
     * @param position
     */
    private fun drawTitleArea(
            c: Canvas,
            left: Int,
            right: Int,
            child: View,
            params: RecyclerView.LayoutParams,
            position: Int
    ) {//最先调用，绘制在最下层
        mPaint.color = COLOR_TITLE_BG
        c.drawRect(
                left.toFloat(),
                (child.top - params.topMargin - mTitleHeight).toFloat(),
                right.toFloat(),
                (child.top - params.topMargin).toFloat(),
                mPaint
        )
        mPaint.color = COLOR_TITLE_FONT
        /*
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;*/

        mPaint.getTextBounds(mDatas!![position].suspensionTag, 0, mDatas!![position].suspensionTag.length, mBounds)
        c.drawText(
                mDatas!![position].suspensionTag,
                child.paddingLeft.toFloat(),
                (child.top - params.topMargin - (mTitleHeight / 2 - mBounds.height() / 2)).toFloat(),
                mPaint
        )
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {//最后调用 绘制在最上层
        if (!isShowSuspension) {
            return
        }

        var pos = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        pos -= getHeaderViewCount()
        //pos为1，size为1，1>0? true
        if (mDatas == null || mDatas!!.isEmpty() || pos > mDatas!!.size - 1 || pos < 0 || !mDatas!![pos].isShowSuspension) {
            return //越界
        }

        val tag = mDatas!![pos].suspensionTag
        //View child = parent.getChildAt(pos);
        val child =
                parent.findViewHolderForLayoutPosition(pos + getHeaderViewCount())!!.itemView//出现一个奇怪的bug，有时候child为空，所以将 child = parent.getChildAt(i)。-》 parent.findViewHolderForLayoutPosition(pos).itemView

        var flag = false//定义一个flag，Canvas是否位移过的标志
        if (pos + 1 < mDatas!!.size) {//防止数组越界（一般情况不会出现）
            if (null != tag && tag != mDatas!![pos + 1].suspensionTag) {//当前第一个可见的Item的tag，不等于其后一个item的tag，说明悬浮的View要切换了
                Log.d("zxt", "onDrawOver() called with: c = [" + child.top)//当getTop开始变负，它的绝对值，是第一个可见的Item移出屏幕的距离，
                if (child.height + child.top < mTitleHeight) {//当第一个可见的item在屏幕中还剩的高度小于title区域的高度时，我们也该开始做悬浮Title的“交换动画”
                    c.save()//每次绘制前 保存当前Canvas状态，
                    flag = true

                    //一种头部折叠起来的视效，个人觉得也还不错~
                    //可与123行 c.drawRect 比较，只有bottom参数不一样，由于 child.getHeight() + child.getTop() < mTitleHeight，所以绘制区域是在不断的减小，有种折叠起来的感觉
                    //c.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(), parent.getRight() - parent.getPaddingRight(), parent.getPaddingTop() + child.getHeight() + child.getTop());

                    //类似饿了么点餐时,商品列表的悬停头部切换“动画效果”
                    //上滑时，将canvas上移 （y为负数） ,所以后面canvas 画出来的Rect和Text都上移了，有种切换的“动画”感觉
                    c.translate(0f, (child.height + child.top - mTitleHeight).toFloat())
                }
            }
        }
        mPaint.color = COLOR_TITLE_BG
        c.drawRect(
                parent.paddingLeft.toFloat(),
                parent.paddingTop.toFloat(),
                (parent.right - parent.paddingRight).toFloat(),
                (parent.paddingTop + mTitleHeight).toFloat(),
                mPaint
        )
        mPaint.color = COLOR_TITLE_FONT
        mPaint.getTextBounds(tag, 0, tag!!.length, mBounds)
        c.drawText(
                tag, child.paddingLeft.toFloat(),
                (parent.paddingTop + mTitleHeight - (mTitleHeight / 2 - mBounds.height() / 2)).toFloat(),
                mPaint
        )
        if (flag)
            c.restore()//恢复画布到之前保存的状态


        /*        Button button = new Button(parent.getContext());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "啊哈", Toast.LENGTH_SHORT).show();//即使给View设置了点击事件，也是无效的，它仅仅draw了
            }
        });
        ViewGroup.LayoutParams params = button.getLayoutParams();
        if (params == null){
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        button.setLayoutParams(params);
        button.setBackgroundColor(Color.RED);
        button.setText("无哈");
        *//*button.measure(View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY),View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.EXACTLY));
*//*
        //必须经过 测量 和 布局，View才能被正常的显示出来
        button.measure(View.MeasureSpec.makeMeasureSpec(9999,View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(9999,View.MeasureSpec.UNSPECIFIED));
        button.layout(parent.getPaddingLeft(),parent.getPaddingTop(),
                parent.getPaddingLeft()+button.getMeasuredWidth(),parent.getPaddingTop()+button.getMeasuredHeight());
        button.draw(c);*/

        //inflate一个复杂布局 并draw出来
        /*        View toDrawView = mInflater.inflate(R.layout.header_complex, parent, false);
        int toDrawWidthSpec;//用于测量的widthMeasureSpec
        int toDrawHeightSpec;//用于测量的heightMeasureSpec
        //拿到复杂布局的LayoutParams，如果为空，就new一个。
        // 后面需要根据这个lp 构建toDrawWidthSpec，toDrawHeightSpec
        ViewGroup.LayoutParams lp = toDrawView.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);//这里是根据复杂布局layout的width height，new一个Lp
            toDrawView.setLayoutParams(lp);
        }
        if (lp.width == ViewGroup.LayoutParams.MATCH_PARENT) {
            //如果是MATCH_PARENT，则用父控件能分配的最大宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.EXACTLY);
        } else if (lp.width == ViewGroup.LayoutParams.WRAP_CONTENT) {
            //如果是WRAP_CONTENT，则用父控件能分配的最大宽度和AT_MOST构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth() - parent.getPaddingLeft() - parent.getPaddingRight(), View.MeasureSpec.AT_MOST);
        } else {
            //否则则是具体的宽度数值，则用这个宽度和EXACTLY构建MeasureSpec。
            toDrawWidthSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //高度同理
        if (lp.height == ViewGroup.LayoutParams.MATCH_PARENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.EXACTLY);
        } else if (lp.height == ViewGroup.LayoutParams.WRAP_CONTENT) {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight() - parent.getPaddingTop() - parent.getPaddingBottom(), View.MeasureSpec.AT_MOST);
        } else {
            toDrawHeightSpec = View.MeasureSpec.makeMeasureSpec(lp.width, View.MeasureSpec.EXACTLY);
        }
        //依次调用 measure,layout,draw方法，将复杂头部显示在屏幕上。
        toDrawView.measure(toDrawWidthSpec, toDrawHeightSpec);
        toDrawView.layout(parent.getPaddingLeft(), parent.getPaddingTop(),
                parent.getPaddingLeft() + toDrawView.getMeasuredWidth(), parent.getPaddingTop() + toDrawView.getMeasuredHeight());
        toDrawView.draw(c);*/

    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        //super里会先设置0 0 0 0
        super.getItemOffsets(outRect, view, parent, state)
        var position = (view.layoutParams as RecyclerView.LayoutParams).viewLayoutPosition
        position -= getHeaderViewCount()
        if (mDatas == null || mDatas!!.isEmpty() || position > mDatas!!.size - 1) {//pos为1，size为1，1>0? true
            return //越界
        }
        //我记得Rv的item position在重置时可能为-1.保险点判断一下吧
        if (position > -1) {
            val titleCategoryInterface = mDatas!![position]
            //等于0肯定要有title的,
            // 2016 11 07 add 考虑到headerView 等于0 也不应该有title
            // 2016 11 10 add 通过接口里的isShowSuspension() 方法，先过滤掉不想显示悬停的item
            if (titleCategoryInterface.isShowSuspension) {
                if (position == 0) {
                    outRect.set(0, mTitleHeight, 0, 0)
                } else {//其他的通过判断
                    if (null != titleCategoryInterface.suspensionTag && titleCategoryInterface.suspensionTag != mDatas!![position - 1].suspensionTag) {
                        //不为空 且跟前一个tag不一样了，说明是新的分类，也要title
                        outRect.set(0, mTitleHeight, 0, 0)
                    }
                }
            }
        }
    }

    companion object {
        private var COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF")
        private var COLOR_TITLE_FONT = Color.parseColor("#FF999999")
        private var mTitleFontSize: Int? = 0//title字体大小
    }

}
