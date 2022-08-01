package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityEnterMyInfoBinding
import com.luvpets.petda.fragment.enterInfo.*
import kotlin.concurrent.thread

class EnterMyInfo : AppCompatActivity() {
  private val binding by lazy { ActivityEnterMyInfoBinding.inflate(layoutInflater) }
  private var currentPage = 1
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    initFragment()
    handleSkip()
    handleBack()
    handleNextPage()
  }
  
  // 렌더링 시 1단계 표출.
  private fun initFragment() {
    handleCurrentPages("init")
  }
  // 건너뛰기 클릭 시 스택 전부 제거 후 홈으로 이동.
  private fun handleSkip() {
    binding.btnSkip.setOnClickListener{
      val intent = Intent(this, HomeActivity::class.java)
      startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
      finish()
    }
  }
  // 뒤로 버튼 클릭 시 한 단계씩 뒤로, 1단계 시 로그인 페이지로 이동.
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
  // 다음 페이지 클릭 시 단계 뒤로 이동.
  private fun handleNextPage() {
    binding.btnNextPage.setOnClickListener {
      currentPage += 1
      handleCurrentPages("next")
    }
  }
  private fun handleCurrentPages(type: String) {
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
          // 백그라운드에서 실행
          thread(start=true) {
            Thread.sleep(500)
            // 메인스레드에서 실행
            runOnUiThread {
              val intent = Intent(this, HomeActivity::class.java)
              startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
              finish()
            }
          }
        }
      }
    }
  }
  
  // fragment 연결.
  private fun handleFragment(fragment: Fragment) {
    supportFragmentManager
      .beginTransaction()
      .replace(R.id.enterInfoFragment, fragment)
      .commit()
  }
}