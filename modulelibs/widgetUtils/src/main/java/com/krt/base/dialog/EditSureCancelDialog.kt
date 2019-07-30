package com.krt.base.dialog

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.util.KeyboardUtils
import com.krt.base.R

class EditSureCancelDialog(
        context: Context, val action: (String) -> Unit,
        val title: String? = null, private val maxCount: Int? = null
) : BaseDialog(context, 0) {

    private var mTvMaxCount: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_dialog_edit_sure_cancel)

        val editView = findViewById<EditText>(R.id.editText)

        //输入框有字数限制
        maxCount?.let {
            mTvMaxCount = findViewById(R.id.tv_max_count)
            mTvMaxCount?.apply {
                visibility = View.VISIBLE
                text = "-" + maxCount
            }

            editView?.apply {
                val filters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxCount))
                setFilters(filters)
            }

            editView.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val count = maxCount - editView.text.toString().length

                    if (count > 0) {
                        mTvMaxCount!!.text = "-" + count
                    } else {
                        mTvMaxCount!!.text = 0.toString()
                    }

                }
            })

        }

        title?.let {
            findViewById<TextView>(R.id.tv_title).text = it
        }

        findViewById<View>(R.id.tv_cancel).setOnClickListener {
            KeyboardUtils.hideSoftInput(it)
            dismiss()
        }

        findViewById<View>(R.id.tv_sure).setOnClickListener {
            val content = editView.text.toString().trim()

            if (content.isEmpty()) {
                Toast.makeText(context, "显示名不能为空！", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            action.invoke(content)
            KeyboardUtils.hideSoftInput(it)
            dismiss()
        }
    }

}