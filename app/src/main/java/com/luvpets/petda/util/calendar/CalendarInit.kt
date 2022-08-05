package com.luvpets.petda.util.calendar

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.adapter.CalendarAdapter
import com.luvpets.petda.databinding.FragmentHomeCalendarBinding
import java.util.*
import kotlin.collections.ArrayList

class CalendarInit(
  private val binding: FragmentHomeCalendarBinding,
  private var context: Context,
  private val onItemListener: OnItemListener
) {
  private lateinit var calendarAdapter: CalendarAdapter
  @SuppressLint("NewApi")
  fun initDate() {
    setMonthView()
    // 이전달 버튼
    binding.prevMonth.setOnClickListener {
      // 이전 달 담기
      CalendarUtil.selectedDate.add(Calendar.MONTH, -1)
      setMonthView()
    }
    // 다음달 버튼
    binding.nextMonth.setOnClickListener {
      CalendarUtil.selectedDate.add(Calendar.MONTH, 1)
      setMonthView()
    }
  }
  
  private fun setMonthView() {
    binding.currentMonth.text = monthYearFromDate(CalendarUtil.selectedDate)
    val dayList = dayInMonthArray()
    // 어댑터 초기화
    calendarAdapter = CalendarAdapter(dayList, onItemListener)
    // 레이아웃 설정
    val manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
    binding.calenderRecycler.layoutManager = manager
    binding.calenderRecycler.adapter = calendarAdapter
  }
  
  // 날짜 타임 생성
  @SuppressLint("NewApi")
  private fun monthYearFromDate(calendar: Calendar): String {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH) + 1
    return "${year}년 ${month}월"
  }
  // 날짜 생성
  @SuppressLint("NewApi")
  private fun dayInMonthArray(): ArrayList<Date>{
    val dayList = ArrayList<Date>()
    val monthCalendar = CalendarUtil.selectedDate.clone() as Calendar
    
    // 날짜는 1일로 셋팅
    monthCalendar[Calendar.DAY_OF_MONTH] = 1
    // 1일의 요일
    val firstDayOfMonth = monthCalendar[Calendar.DAY_OF_WEEK] - 1
    
    // 요일 숫자만큼 이전 날짜로 설정
    monthCalendar.add(Calendar.DAY_OF_MONTH, - firstDayOfMonth)
    while (dayList.size < 35) {
      dayList.add(monthCalendar.time)
      monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
    }
    return dayList
  }
}