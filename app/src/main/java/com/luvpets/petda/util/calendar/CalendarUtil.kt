package com.luvpets.petda.util.calendar

import android.annotation.SuppressLint
import android.view.View
import java.util.*

class CalendarUtil {
  companion object{
    @SuppressLint("NewApi")
    var selectedDate: Calendar = Calendar.getInstance()
  }
}

interface OnItemListener {
  fun onItemClick(dayText: String)
}