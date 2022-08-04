package com.luvpets.petda.util

import android.annotation.SuppressLint
import java.time.LocalDate

class CalendarUtil {
  companion object{
    @SuppressLint("NewApi")
    var selectedDate: LocalDate = LocalDate.now()
  }
}