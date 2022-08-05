package com.luvpets.petda.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.R
import com.luvpets.petda.util.calendar.CalendarUtil
import com.luvpets.petda.util.calendar.OnItemListener
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(
  private val dayList: ArrayList<Date>,
  private val onItemListener: OnItemListener
  ): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
  class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val dayText: TextView = view.findViewById(R.id.calendarDay)
    
  }
  
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    return ViewHolder(inflater.inflate(R.layout.recycler_calender_day, parent, false))
  }
  
  @SuppressLint("NewApi")
  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    // 날짜
    val day = dayList[holder.adapterPosition]
    // 캘린더 초기화
    val calendar = Calendar.getInstance()
    // 날짜 담기
    calendar.time = day
    // 캘린더 - 날짜 담기
    val date = calendar.get(Calendar.DAY_OF_MONTH)
    holder.dayText.text = date.toString()
    
    // 넘어온 날짜
    val prevYear = calendar.get(Calendar.YEAR)
    val prevMonth = calendar.get(Calendar.MONTH) + 1
  
    // 현재 날짜
    val selectYear = CalendarUtil.selectedDate.get(Calendar.YEAR)
    val selectMonth = CalendarUtil.selectedDate.get(Calendar.MONTH) + 1
    val selectDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH)
    
    // 오늘 날짜
    val today = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d")
    val todayText = today.format(formatter)
  
    // 넘어온 날짜와 현재 날짜 비교
    if (prevYear == selectYear && prevMonth == selectMonth) {
      holder.dayText.setTextColor(Color.parseColor("#606060"))
      val isToday = "${prevYear}.${prevMonth}.${date}" == todayText
      if (isToday) {
        holder.dayText.setBackgroundResource(R.drawable.circle_default)
        holder.dayText.setTextColor(Color.WHITE)
      }
    }
    else holder.dayText.setTextColor(Color.parseColor("#c4c4c4"))
    
    holder.itemView.setOnClickListener {
      val iMonth = calendar.get(Calendar.MONTH) + 1
      val iDay = calendar.get(Calendar.DAY_OF_MONTH)
      val selected = calendar.get(Calendar.DAY_OF_WEEK)
      
      onItemListener.onItemClick(handleDateText(selected, iMonth, iDay))
    }
  }
  
  override fun getItemCount(): Int {
    return dayList.size
  }
  
  private fun handleDateText(selected: Any, month: Int, day: Int): String {
    val date = when (selected) {
      (selected == 1) -> "일"
      (selected == 2) -> "월"
      (selected == 3) -> "화"
      (selected == 4) -> "수"
      (selected == 5) -> "목"
      (selected == 6) -> "금"
      else -> "토"
    }
    return "${month}월 ${day}일 (${date})"
  }
}