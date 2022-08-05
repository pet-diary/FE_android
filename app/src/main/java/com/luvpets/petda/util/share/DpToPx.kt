package com.luvpets.petda.util.share

import android.content.Context
import android.util.TypedValue

open class DpToPx {
  open fun dpToPx(context: Context, dp: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
  }
}