package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivitySignUpBinding
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
  private var _binding: ActivitySignUpBinding?= null
  private val binding get() = _binding!!
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    _binding = ActivitySignUpBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
    initInput()
    handleUtil()
  }
  
  private fun initInput() {
    handleInput(binding.inputEmail, binding.deleteInputEmail)
    handleInput(binding.inputId, binding.deleteInputId)
    handleInput(binding.inputPw, binding.deleteInputPw)
    handleInput(binding.inputPwChk, binding.deleteInputPwChk)
  }
  private fun handleInput(input: EditText, delBtn: ImageButton) {
    input.setOnFocusChangeListener { _, is_focus ->
      if (is_focus) delBtn.visibility = View.VISIBLE
      else if (input.text.isEmpty()) delBtn.visibility = View.INVISIBLE
      else {
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        val email = binding.inputEmail.text.toString()
        if (emailPattern.matcher(email).matches()) {
          binding.noticeEmail.visibility = View.INVISIBLE
        } else {
          binding.noticeEmail.visibility = View.VISIBLE
          binding.noticeEmail.text = getString(R.string.email_error)
        }
        
        val pw = binding.inputPw.text.toString()
        if (Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{1,8}$", pw)) {
          binding.noticePw.visibility = View.INVISIBLE
        } else {
          binding.noticePw.visibility = View.VISIBLE
          binding.noticePw.text = getString(R.string.pw_error)
        }
      }
    }
    delBtn.setOnClickListener {
      input.text = null
      delBtn.visibility = View.INVISIBLE
      if (delBtn == binding.deleteInputPw ||delBtn == binding.deleteInputPwChk ) {
        binding.noticePwChk.visibility = View.INVISIBLE
      }
    }
    input.addTextChangedListener {
      delBtn.visibility = View.VISIBLE
      val btnNextPage = binding.btnNextPage
      val scrillView = binding.scrollSignUp
      val isActive = binding.inputEmail.text.isNotEmpty() && binding.inputId.text.isNotEmpty() && binding.inputPw.text.isNotEmpty() && binding.inputPwChk.text.isNotEmpty()
      if (isActive) {
        btnNextPage.visibility = View.VISIBLE
        scrillView.setPadding(0, 0, 0, 250)
      }
      else {
        btnNextPage.visibility = View.INVISIBLE
        scrillView.setPadding(0, 0, 0, 0)
      }
  
      val pw = binding.inputPw.text.toString()
      val pwChk = binding.inputPwChk.text.toString()
      if (pw.isNotEmpty()) {
        if (pw == pwChk) {
          binding.noticePwChk.visibility = View.VISIBLE
          binding.noticePwChk.text = getString(R.string.pass_pw_chk)
          binding.noticePwChk.setTextColor(Color.parseColor("#50c99b"))
        } else {
          binding.noticePwChk.visibility = View.VISIBLE
          binding.noticePwChk.text = getString(R.string.fail_pw_chk)
          binding.noticePwChk.setTextColor(Color.parseColor("#f05c5c"))
        }
      }
    }
  }
  private fun handleUtil() {
    binding.btnNextPage.setOnClickListener {
      val intent = Intent(this, EnterMyInfo::class.java)
      startActivity(intent)
    }
  }
}