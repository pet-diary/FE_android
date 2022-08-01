package com.luvpets.petda.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.luvpets.petda.fragment.community.PhotoFragment
import com.luvpets.petda.fragment.community.ShareFragment

class CommunityTabAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
  override fun getItemCount(): Int = 2
  
  override fun createFragment(position: Int): Fragment {
    val fragment: Fragment = if (position == 0) {
      ShareFragment()
    } else {
      PhotoFragment()
    }
    return fragment
  }
}