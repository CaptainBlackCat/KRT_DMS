package com.krt.base.widgets.htextview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Base TextView
 * Created by hanks on 2017/3/13.
 */

public abstract class HTextView extends android.support.v7.widget.AppCompatTextView {
    public HTextView(Context context) {
        this(context, null);
    }

    public HTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void setAnimationListener(AnimationListener listener);

    public abstract void setProgress(float progress);

    public abstract void animateText(CharSequence text);
}
