package com.luvpets.petda.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.luvpets.petda.databinding.FragmentHomeCalendarBinding
import com.luvpets.petda.util.calendar.CalendarInit
import com.luvpets.petda.util.calendar.OnItemListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarFragment: Fragment(), OnItemListener {
  private val binding by lazy { FragmentHomeCalendarBinding.inflate(layoutInflater) }
  private lateinit var calendarInit: CalendarInit
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    calendarInit = context?.let { CalendarInit(binding, it, this) }!!
    calendarInit.initDate()
    initPanel()
    initDate()
    
    return binding.root
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
  @SuppressLint("NewApi")
  private fun initDate() {
    val today = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("MM월 dd일 (E)").withLocale(Locale.forLanguageTag("ko"))
    val todayText = today.format(formatter)
    onItemClick(todayText)
  }
  
  override fun onItemClick(dayText: String) {
    binding.selectedDate.text = dayText
    binding.calendarSlideInner.selectedDate.text = dayText
  }
}