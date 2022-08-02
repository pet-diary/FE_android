package com.luvpets.petda.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.luvpets.petda.R
import com.luvpets.petda.adapter.CommunityTabAdapter
import com.luvpets.petda.databinding.FragmentHomeCommunityBinding

class CommunityFragment: Fragment() {
  private val binding by lazy { FragmentHomeCommunityBinding.inflate(layoutInflater) }
  private lateinit var communityTabAdapter: CommunityTabAdapter
  
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    
    
    return binding.root
  }
  
  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    // viewPager
    communityTabAdapter = CommunityTabAdapter(this)
    binding.communityContent.adapter = communityTabAdapter
    
    // tabLayout
    TabLayoutMediator(binding.communityTab, binding.communityContent) { tab, position ->
      if (position == 0) {
        tab.text = "정보공유"
        tab.setIcon(R.drawable.ic_tab_paw)
      } else {
        tab.text = "자랑하기"
        tab.setIcon(R.drawable.ic_tab_paw)
      }
    }.attach()
  }
  
}