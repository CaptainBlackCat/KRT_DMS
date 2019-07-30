package com.krt.base.dialog

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import com.krt.base.R
import com.krt.base.utils.PhotoUtils
import com.krt.frame.permission.PermissionDelegate
import kotlinx.android.synthetic.main.base_dialog_choose_image.*

class ChooseImageDialog(context: Context, val fragment: Fragment) :
        BaseDialog(context, R.style.base_dialog_slide_from_bottom) {

    init {
        val dialogView = LayoutInflater.from(getContext()).inflate(R.layout.base_dialog_choose_image, null)

        setContentView(dialogView)

        layoutParams?.gravity = Gravity.BOTTOM
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_cancel.setOnClickListener {
            this.dismiss()
        }

        tv_camera.setOnClickListener {
            val permissionDelegate = PermissionDelegate.Factory().create(fragment.activity!!)

            permissionDelegate.checkStoragePermission {
                PhotoUtils.openCameraImage(fragment)
            }

            dismiss()
        }

        tv_file.setOnClickListener {
            PhotoUtils.openLocalImage(fragment)
            dismiss()
        }
    }

}