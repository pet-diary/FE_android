package com.luvpets.petda.util.dialog

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.luvpets.petda.R
import com.luvpets.petda.databinding.LayoutAlertDialogBinding

class CustomDialog(
  private val context: AppCompatActivity
) {
  private lateinit var binding: LayoutAlertDialogBinding
  private lateinit var listener: ConfirmListener
  private val dialog = Dialog(context)

  fun showDialog(content: String, status: Boolean) {
    binding = LayoutAlertDialogBinding.inflate(context.layoutInflater)

    binding.alertDialogText.text = content

    dialog.apply {
      setContentView(binding.root)
      setCanceledOnTouchOutside(true)
      setCancelable(true)
      show()
      window?.apply {
        setBackgroundDrawableResource(R.drawable.bg_18r_white)
      }
    }
    binding.btnConfirm.setOnClickListener {
      if (status) listener.onConfirmed()
      dialog.dismiss()
    }
    binding.btnCancel.setOnClickListener {
      dialog.dismiss()
    }
  }

  fun setOnConfirmedListener(listener: () -> Unit) {
    this.listener = object : ConfirmListener {
      override fun onConfirmed() {
        listener()
      }
    }

  }

  interface ConfirmListener {
    fun onConfirmed()
  }
}