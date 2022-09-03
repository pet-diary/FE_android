package com.luvpets.petda.util.dialog

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import androidx.appcompat.widget.AppCompatButton
import com.luvpets.petda.R

class CustomDialog(context: Context) {
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener) { onClickListener = listener }

    fun showDialog()
    {
        dialog.setContentView(R.layout.layout_alert_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

        dialog.findViewById<AppCompatButton>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<AppCompatButton>(R.id.btnConfirm).setOnClickListener {
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener { fun onClicked(name: String) }
}