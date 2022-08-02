package com.luvpets.petda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityHomeBinding
import com.luvpets.petda.fragment.home.*

class HomeActivity : AppCompatActivity() {
  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
  private lateinit var prevActivity: String
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  
    prevActivity = intent.getStringExtra("prevActivity").toString()
    
    initHomeActivity()
    handleClickNavigation()
  }
  
  private fun initHomeActivity() {
    binding.homeNavigation.selectedItemId = R.id.calendar
    if (prevActivity == "null") changeFragment(CalendarFragment())
    else {
      when (prevActivity) {
        "커뮤니티" -> {
          changeFragment(CommunityFragment())
          binding.homeNavigation.selectedItemId = R.id.community
        }
        "강아지산책" -> {
          changeFragment(WalkFragment())
          binding.homeNavigation.selectedItemId = R.id.walk
        }
        "캘린더" -> {
          changeFragment(CalendarFragment())
          binding.homeNavigation.selectedItemId = R.id.calendar
        }
        "알림" -> {
          changeFragment(AlertFragment())
          binding.homeNavigation.selectedItemId = R.id.alert
        }
        else -> {
          changeFragment(MyPageFragment())
          binding.homeNavigation.selectedItemId = R.id.myPage
        }
      }
    }
  }
  private fun handleClickNavigation() {
    binding.homeNavigation.setOnItemSelectedListener { item ->
      changeFragment(
        when (item.itemId) {
          R.id.community -> CommunityFragment()
          R.id.walk -> WalkFragment()
          R.id.calendar -> CalendarFragment()
          R.id.alert -> AlertFragment()
          else -> MyPageFragment()
        }
      )
      true
    }
  }
  private fun changeFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.homeFragment, fragment)
      .commit()
  }
}