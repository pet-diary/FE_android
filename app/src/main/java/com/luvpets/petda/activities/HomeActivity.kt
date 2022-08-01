package com.luvpets.petda.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityHomeBinding
import com.luvpets.petda.fragment.home.*

class HomeActivity : AppCompatActivity() {
  private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    initFragment()
  }
  
  private fun initFragment() {
    binding.homeNavigation.setOnItemSelectedListener { item ->
      changeFragment(
        when (item.itemId) {
          R.id.community -> {
            CommunityFragment()
          }
          R.id.walk -> {
            WalkFragment()
          }
          R.id.calendar -> {
            CalendarFragment()
          }
          R.id.alert -> {
            AlertFragment()
          }
          else -> {
            MyPageFragment()
          }
        }
      )
      true
    }
    binding.homeNavigation.selectedItemId = R.id.calendar
  }
  private fun changeFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.homeFragment, fragment)
      .commit()
  }
}