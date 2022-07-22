package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityEnterMyInfoBinding
import com.luvpets.petda.fragment.*

class EnterMyInfo : AppCompatActivity() {
  private var _binding: ActivityEnterMyInfoBinding? = null
  private val binding get() = _binding!!
  private var currentPage = 1
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivityEnterMyInfoBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
    initFragment()
    handleSkip()
    handleBack()
    handleNextPage()
  }
  
  private fun initFragment() {
    handleCurrentPages("init")
  }
  private fun handleSkip() {
    binding.btnSkip.setOnClickListener{
      val intent = Intent(this, HomeActivity::class.java)
      // 스택 전부 제거
      startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
      finish()
    }
  }
  private fun handleBack() {
    binding.btnBack.setOnClickListener {
      if (currentPage > 1) {
        currentPage -= 1
        handleCurrentPages("init")
      } else if (currentPage == 1) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
        finish()
      }
    }
  }
  private fun handleNextPage() {
    binding.btnNextPage.setOnClickListener {
      currentPage += 1
      handleCurrentPages("next")
    }
  }
  private fun handleCurrentPages(type: String) {
    Log.d("currentPage", "${currentPage}")
    when (currentPage) {
      1 -> {
        if (type == "init") {
          handleFragment(EnterInfoFragment())
          binding.process1.setTextColor(Color.parseColor("#ffffff"))
          binding.process1.setBackgroundResource(R.drawable.circle_default)
          binding.process2.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process2.setBackgroundResource(R.drawable.circle_outline)
        } else if (type == "next") {
          binding.process1.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process1.setBackgroundResource(R.drawable.circle_outline)
          binding.process2.setTextColor(Color.parseColor("#ffffff"))
          binding.process2.setBackgroundResource(R.drawable.circle_default)
        }
      }
      2 -> {
        if (type == "init") {
          handleFragment(EnterSelectPetFragment())
          binding.process2.setTextColor(Color.parseColor("#ffffff"))
          binding.process2.setBackgroundResource(R.drawable.circle_default)
          binding.process3.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process3.setBackgroundResource(R.drawable.circle_outline)
        } else if (type == "next") {
          handleFragment(EnterSelectPetFragment())
          binding.process1.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process1.setBackgroundResource(R.drawable.circle_outline)
          binding.process2.setTextColor(Color.parseColor("#ffffff"))
          binding.process2.setBackgroundResource(R.drawable.circle_default)
        }
      }
      3 -> {
        if (type == "init") {
          handleFragment(EnterInfoPetsFragment())
          binding.process3.setTextColor(Color.parseColor("#ffffff"))
          binding.process3.setBackgroundResource(R.drawable.circle_default)
          binding.process4.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process4.setBackgroundResource(R.drawable.circle_outline)
        } else if (type == "next") {
          handleFragment(EnterInfoPetsFragment())
          binding.process2.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process2.setBackgroundResource(R.drawable.circle_outline)
          binding.process3.setTextColor(Color.parseColor("#ffffff"))
          binding.process3.setBackgroundResource(R.drawable.circle_default)
        }
      }
      4 -> {
        if (type == "init") {
          handleFragment(EnterTypeFragment())
          binding.process4.setTextColor(Color.parseColor("#ffffff"))
          binding.process4.setBackgroundResource(R.drawable.circle_default)
        } else if (type == "next") {
          handleFragment(EnterTypeFragment())
          binding.process3.setTextColor(Color.parseColor("#5f5f5f"))
          binding.process3.setBackgroundResource(R.drawable.circle_outline)
          binding.process4.setTextColor(Color.parseColor("#ffffff"))
          binding.process4.setBackgroundResource(R.drawable.circle_default)
        }
      }
      else -> {
        if (type == "next") {
          handleFragment(EnterCompleteFragment())
        }
      }
    }
  }
  
  private fun handleFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.enterInfoFragment, fragment)
      .commit()
  }
}