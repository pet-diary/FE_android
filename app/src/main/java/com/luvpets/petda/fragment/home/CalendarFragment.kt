package com.luvpets.petda.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luvpets.petda.adapter.CalendarAdapter
import com.luvpets.petda.databinding.FragmentHomeCalendarBinding
import com.luvpets.petda.util.CalendarUtil
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class CalendarFragment: Fragment() {
  private val binding by lazy { FragmentHomeCalendarBinding.inflate(layoutInflater) }
  private lateinit var calendarAdapter: CalendarAdapter
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    initDate()
    initPanel()
    
    return binding.root
  }
  
  @SuppressLint("NewApi")
  private fun initDate() {
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
    calendarAdapter = CalendarAdapter(dayList)
    // 레이아웃 설정
    val manager: RecyclerView.LayoutManager = GridLayoutManager(context, 7)
    binding.calenderRecycler.layoutManager = manager
    binding.calenderRecycler.adapter = calendarAdapter
  }
  
  // 날짜 타임 생성
  @SuppressLint("NewApi")
  private fun monthYearFromDate(calendar: Calendar): String {
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
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
  private fun initPanel() {
    val sliding = binding.sliderCalendarWrap
    sliding.addPanelSlideListener(PanelEventListener())
    binding.calendarSlidePanel.setOnClickListener {
      val isPanelState = binding.sliderCalendarWrap.panelState
      // 닫힌 상태
      if (isPanelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
        sliding.panelState = SlidingUpPanelLayout.PanelState.ANCHORED
      } else if (isPanelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
        sliding.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
      }
    }
  }
  
  inner class PanelEventListener: SlidingUpPanelLayout.PanelSlideListener {
    override fun onPanelSlide(panel: View?, slideOffset: Float) {}
  
    override fun onPanelStateChanged(
      panel: View?,
      previousState: SlidingUpPanelLayout.PanelState?,
      newState: SlidingUpPanelLayout.PanelState?
    ) {
      // TODO 수정 상태로 변경
    }
  }
}