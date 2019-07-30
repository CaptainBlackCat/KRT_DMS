package com.krt.base.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 自定义Textview 超过最大行数 末尾显示省略号和最后一个字符
 * 使用这个 控件 getText 的内容是不完整的
 */

public class SymbolTextView extends android.support.v7.widget.AppCompatTextView {

    public SymbolTextView(Context context) {
        super(context);
    }

    public SymbolTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SymbolTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getLineCount() == 0) {
            return;
        }
        if (getLayout() == null) {
            return;
        }
        int ellipsisCount = getLayout().getEllipsisCount(getLineCount() - 1);
        if (ellipsisCount == 0) {
            return;
        }
        String content = getText().toString();
        String lastChar = content.substring(content.length() - 1, content.length());
        int measuredWidth = getMeasuredWidth();
        int lineCount = getLineCount();
        int maxMW = measuredWidth * lineCount;
        while (getPaint().measureText(content + "..." + lastChar) > maxMW) {
            content = content.substring(0, content.length() - 1);
        }
        setText(content + "..." + lastChar);
    }
}

