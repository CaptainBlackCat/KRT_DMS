package com.krt.base.dialog

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.krt.base.R


object CustomAlertDialog {

    fun show(context: Context,
             title: String? = null,
             content: String? = null,
             positiveText: String? = null,  //左边 按钮
             positiveClickListener: ((dialog: Dialog) -> Unit)? = null,
             negativeText: String? = null,  //右边 按钮
             negativeClickListener: ((dialog: Dialog) -> Unit)? = null
    ): Dialog {

        val dialog = Dialog(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layout = inflater.inflate(R.layout.base_dialog_custom_alert, null)
        dialog.addContentView(layout, ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))

        val titleView = layout.findViewById<TextView>(R.id.dialog_title)
        val contentView = layout.findViewById<TextView>(R.id.dialog_content)
        val positiveButton = layout.findViewById<TextView>(R.id.positive_button)
        val negativeButton = layout.findViewById<TextView>(R.id.negative_button)
        val view1 = layout.findViewById<View>(R.id.view1)
        val view2 = layout.findViewById<View>(R.id.view2)

        if (null != positiveText || null != negativeText) {
            view1.visibility = View.VISIBLE
        }

        if (null != positiveText && null != negativeText) {
            view2.visibility = View.VISIBLE
        }

        title?.let {
            titleView.visibility = View.VISIBLE
            titleView.text = it
        }

        content?.let {
            contentView.visibility = View.VISIBLE
            contentView.text = it
        }

        positiveText?.let {
            positiveButton.visibility = View.VISIBLE
            positiveButton.text = it

            if (positiveClickListener == null) {
                positiveButton.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        negativeText?.let {
            negativeButton.visibility = View.VISIBLE
            negativeButton.text = it

            if (negativeClickListener == null) {
                negativeButton.setOnClickListener {
                    dialog.dismiss()
                }
            }
        }

        positiveClickListener?.let { clickListener ->
            positiveButton.setOnClickListener {
                clickListener.invoke(dialog)
            }
        }

        negativeClickListener?.let { clickListner ->
            negativeButton.setOnClickListener {
                clickListner.invoke(dialog)
            }
        }

        dialog.show()

        return dialog
    }

}