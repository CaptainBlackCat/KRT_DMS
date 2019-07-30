package com.krt.base.widgets

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.krt.base.R

/**
 * 自定义评价星星类(任何形状)
 * yueleng  on 2017/1/9.
 */

class StarBar : View {
    //星星评分
    /**
     * 获取分数
     */
    var mark = 0.0f
        private set
    //星星个数
    private var starNum = 5
    //星星高度
    private var starHeight: Int = 0
    //星星宽度
    private var starWidth: Int = 0
    //星星间距
    private var starDistance: Int = 0
    //星星背景
    private var starBackgroundBitmap: Drawable? = null
    //动态星星
    private var starDrawDrawable: Bitmap? = null
    //星星变化监听
    private var changeListener: OnStarChangeListener? = null
    //是否可以点击
    private var isClick = true
    //画笔
    private var mPaint: Paint? = null
    //是否可触摸
    private var starTouchable: Boolean = true

    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs) {
        init(mContext, attrs)
    }

    constructor(mContext: Context, attrs: AttributeSet, defStyleAttr: Int) : super(mContext, attrs, defStyleAttr) {
        init(mContext, attrs)
    }

    private fun init(mContext: Context, attrs: AttributeSet) {

        //初始化控件属性
        val typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.star)
        starNum = typedArray.getInteger(R.styleable.star_starsNum, 5)
        starHeight = typedArray.getDimension(R.styleable.star_starHeight, 0f).toInt()
        starWidth = typedArray.getDimension(R.styleable.star_starWidth, 0f).toInt()
        starDistance = typedArray.getDimension(R.styleable.star_starDistance, 0f).toInt()
        isClick = typedArray.getBoolean(R.styleable.star_starClickable, true)
        starBackgroundBitmap = typedArray.getDrawable(R.styleable.star_starBackground)
        starTouchable = typedArray.getBoolean(R.styleable.star_starTouchable, true)

        starDrawDrawable = drawableToBitmap(typedArray.getDrawable(R.styleable.star_starDrawBackground))
        typedArray.recycle()
        isClickable = isClick
        //初始化画笔
        mPaint = Paint()
        //设置抗锯齿
        mPaint!!.isAntiAlias = true
        mPaint!!.shader = BitmapShader(starDrawDrawable!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(starWidth * starNum + starDistance * (starNum - 1), starHeight)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (null == starDrawDrawable || null == starBackgroundBitmap) {
            return
        }
        for (i in 0 until starNum) {
            starBackgroundBitmap!!.setBounds(starDistance * i + starWidth * i, 0, starWidth * (i + 1) + starDistance * i, starHeight)
            starBackgroundBitmap!!.draw(canvas)
        }
        if (mark > 1) {
            canvas.drawRect(0f, 0f, starWidth.toFloat(), starHeight.toFloat(), mPaint!!)
            if (mark - mark.toInt() == 0f) {
                var i = 1
                while (i < mark) {
                    canvas.translate((starDistance + starWidth).toFloat(), 0f)
                    canvas.drawRect(0f, 0f, starWidth.toFloat(), starHeight.toFloat(), mPaint!!)
                    i++
                }
            } else {
                var i = 1
                while (i < mark - 1) {
                    canvas.translate((starDistance + starWidth).toFloat(), 0f)
                    canvas.drawRect(0f, 0f, starWidth.toFloat(), starHeight.toFloat(), mPaint!!)
                    i++
                }
                canvas.translate((starDistance + starWidth).toFloat(), 0f)
                canvas.drawRect(0f, 0f, starWidth * (Math.round((mark - mark.toInt()) * 10) * 1.0f / 10), starHeight.toFloat(), mPaint!!)
            }
        } else {
            canvas.drawRect(0f, 0f, starWidth * mark, starHeight.toFloat(), mPaint!!)
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!starTouchable) {
            return false
        }

        var x = event.x.toInt()
        if (x < 0)
            x = 0
        if (x > measuredWidth)
            x = measuredWidth
        when (event.action) {
            MotionEvent.ACTION_DOWN -> setMark(x * 1.0f / (measuredWidth * 1.0f / starNum))
            MotionEvent.ACTION_MOVE -> setMark(x * 1.0f / (measuredWidth * 1.0f / starNum))
            MotionEvent.ACTION_UP -> setMark(x * 1.0f / (measuredWidth * 1.0f / starNum))
        }
        return true
    }

    /**
     * 设置分数
     */
    fun setMark(mark: Float?) {
        this.mark = Math.round(mark!! * 10) * 1.0f / 10
        if (null != changeListener) {
            changeListener!!.onStarChange(this.mark)
        }
        invalidate()
    }

    /**
     * 设置监听
     */
    fun setStarChangeLister(starChangeLister: OnStarChangeListener) {
        changeListener = starChangeLister
    }

    /**
     * 星星数量变化监听接口
     */
    interface OnStarChangeListener {
        fun onStarChange(mark: Float?)
    }

    /**
     * drawable转bitmap
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable == null) return null
        val bitmap = Bitmap.createBitmap(starWidth, starHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, starWidth, starHeight)
        drawable.draw(canvas)
        return bitmap
    }

}
