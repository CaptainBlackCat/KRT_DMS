//package com.krt.base.widgets
//
//import android.content.Context
//import android.support.constraint.ConstraintLayout
//import android.support.v7.widget.AppCompatTextView
//import android.text.Editable
//import android.text.TextWatcher
//import android.util.AttributeSet
//import android.view.View
//import android.widget.ImageView
//import com.krt.base.R
//
//class CounterView(context: Context, attrs: AttributeSet? = null) : ConstraintLayout(context, attrs) {
//
//    private var downView: ImageView
//    private var upView: ImageView
//    private var showText: AppCompatTextView
//
//    private var isInit = false
//
//    var minCounter: Int = 0
//    var maxCounter: Int = 0
//
//    constructor(context: Context) : this(context, null)
//
//    init {
//
//        val view = View.inflate(context, R.layout.dev_view_counter, this)
//
//        downView = view.findViewById(R.id.counter_down)
//        upView = view.findViewById(R.id.counter_up)
//        showText = view.findViewById<AppCompatTextView>(R.id.counter_show_text)
//
//        downView.setOnClickListener {
//            if (isInit)
//                showText.text = (showText.text.toString().toInt() - 1).toString()
//        }
//
//        upView.setOnClickListener {
//            if (isInit)
//                showText.text = (showText.text.toString().toInt() + 1).toString()
//        }
//    }
//
//    /**
//     * 设置最小、最大数
//     */
//    fun setMinAndMaxCounter(minCounter: Int = 1, maxCounter: Int = Int.MAX_VALUE, defaultCounter: Int = 1, action: (Int) -> Unit) {
//        this.minCounter = minCounter
//        this.maxCounter = maxCounter
//
//        showText.addTextChangedListener(object : TextWatcher {
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val current = s.toString().toInt()
//                downView.isEnabled = current > minCounter
//                upView.isEnabled = current < maxCounter
//
//                action.invoke(current)
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//
//            }
//        })
//
//        showText.text = defaultCounter.toString()
//        isInit = true
//
//    }
//
//}