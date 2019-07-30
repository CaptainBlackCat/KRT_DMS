package com.krt.base.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.krt.base.R;


public class TextViewWithWordRegular
        extends android.support.v7.widget.AppCompatTextView {

    public TextViewWithWordRegular(Context context) {
        this(context, null);
    }

    public TextViewWithWordRegular(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewWithWordRegular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewWithWordRegular);

        mTextMaxLen = typedArray.getInt(R.styleable.TextViewWithWordRegular_tvMaxFontNum, 4);
        mTextInterval = typedArray.getDimensionPixelSize(R.styleable.TextViewWithWordRegular_text_between_width,
                (int) context.getResources().getDimension(R.dimen.base_0));

        typedArray.recycle();
        init();
    }

    private void init() {
        mTextString = getText().toString().trim();
        mTextNumber = mTextString.length();

        mPaintText = new Paint();
        mPaintText.setAntiAlias(true);
        mPaintText.setColor(getCurrentTextColor());
        mPaintText.setTextSize(getTextSize());
        mTextFontSize = getTextSize();

        mTextAverageWidth = (int) (mTextFontSize + mTextInterval * 2);
        setWidth((int) (mTextFontSize * mTextMaxLen + mTextMaxLen * mTextInterval * 2));
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
        int measureHeight = getMeasuredHeight();
        mBaseLineY = (int) (measureHeight / 2 - fontMetrics.top / 2 - fontMetrics.bottom / 2);

        if (mTextNumber > 2) {
            float middleInterval = getMeasuredWidth() - mTextFontSize * 2 - mTextInterval * 2;
            mMiddleTextInterval = (middleInterval - mTextFontSize * (mTextNumber - 2)) / (mTextNumber - 1);
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < mTextNumber; i++) {
            if (i == 0) {
                canvas.drawText(mTextString, i, i + 1, mTextInterval, mBaseLineY, mPaintText);
            } else if (i == mTextNumber - 1) {
                canvas.drawText(mTextString, i, i + 1, (mTextAverageWidth) * (mTextMaxLen - 1) + mTextInterval, mBaseLineY, mPaintText);
            } else {
                canvas.drawText(mTextString, i, i + 1, mTextInterval + mTextFontSize + mMiddleTextInterval * i + (mTextFontSize) * (i - 1), mBaseLineY, mPaintText);
            }
        }
    }

    private Paint mPaintText;

    private float mTextFontSize;

    private String mTextString = "";

    private int   mTextAverageWidth;
    private float mMiddleTextInterval;   //除了前后两个字外，中间字之间的间隙
    private int   mTextNumber;
    private int   mTextMaxLen = 6;
    private int   mBaseLineY;

    private int mTextInterval;
}
