package com.luvpets.petda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityCommunityDetailBinding
import com.luvpets.petda.databinding.FragmentCommunityShareDetailBinding
import com.luvpets.petda.fragment.community.detail.ShareDetailFragment
import com.luvpets.petda.fragment.community.detail.ShareWriteFragment

class CommunityDetailActivity : AppCompatActivity() {
  private val binding by lazy { ActivityCommunityDetailBinding.inflate(layoutInflater) }
  private lateinit var itemId: String
  private lateinit var fragmentType: String
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
  
    // TODO : 추후 아이템 id로 api 통신
    itemId = intent.getStringExtra("selectedShareItem").toString()
    fragmentType = intent.getStringExtra("fragmentType").toString()
    
    initFragment()
    handleBottomNavigation()
    handleClickBack()
  }
  
  private fun initFragment() {
    when(fragmentType) {
      "shareDetail" -> changeFragment(ShareDetailFragment(), "정보공유")
      "sharePost" -> changeFragment(ShareWriteFragment(), "정보작성")
      else -> changeFragment(ShareWriteFragment(), "자랑하기")
    }
  }
  private fun changeFragment(fragment: Fragment, title: String) {
    binding.communityDetailTitle.text = title
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.communityDetailContent, fragment)
      .commit()
  }
  private fun handleBottomNavigation() {
    binding.homeNavigation.setOnItemSelectedListener { item ->
      val intent = Intent(this, HomeActivity::class.java)
      intent.putExtra("prevActivity", "${item.title}")
      overridePendingTransition(0,0)
      startActivity(intent)
      true
    }
  }
  private fun handleClickBack() {
    binding.btnBack.setOnClickListener {
      onBackPressed()
    }
  }
}