package com.luvpets.petda.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.R
import com.luvpets.petda.util.CalendarUtil
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(
  private val dayList: ArrayList<Date>
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
    val prevDay = calendar.get(Calendar.DAY_OF_MONTH)
  
    // 현재 날짜
    val selectYear = CalendarUtil.selectedDate.get(Calendar.YEAR)
    val selectMonth = CalendarUtil.selectedDate.get(Calendar.MONTH) + 1
    val selectDay = CalendarUtil.selectedDate.get(Calendar.DAY_OF_MONTH)
    
    // 넘어온 날짜와 현재 날짜 비교
    if (prevYear == selectYear && prevMonth == selectMonth) {
      holder.dayText.setTextColor(Color.parseColor("#606060"))
      if (selectDay == date) {
        holder.dayText.setBackgroundResource(R.drawable.circle_default)
        holder.dayText.setTextColor(Color.WHITE)
      }
    }
    else holder.dayText.setTextColor(Color.parseColor("#c4c4c4"))
    
    holder.itemView.setOnClickListener {
      val iYear = calendar.get(Calendar.YEAR)
      val iMonth = calendar.get(Calendar.MONTH)
      val iDay = calendar.get(Calendar.DAY_OF_MONTH)
      val yearMonDay = "$iYear 년 $iMonth 월 $iDay 일"
      Toast.makeText(holder.itemView.context, yearMonDay, Toast.LENGTH_SHORT).show()
    }
  }
  
  override fun getItemCount(): Int {
    return dayList.size
  }
}