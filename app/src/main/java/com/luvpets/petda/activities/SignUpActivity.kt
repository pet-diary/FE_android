package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.R
import com.luvpets.petda.data.dto.LoginDto
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.UserService
import com.luvpets.petda.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
  private val binding by lazy { ActivitySignUpBinding.inflate(layoutInflater) }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
      if (is_focus && input.text.isEmpty()) delBtn.visibility = View.INVISIBLE
    }
    delBtn.setOnClickListener {
      input.text = null
      delBtn.visibility = View.INVISIBLE
      if (delBtn == binding.deleteInputPw ||delBtn == binding.deleteInputPwChk ) {
        binding.noticePwChk.visibility = View.INVISIBLE
      }
    }
    handleTextChangeInput(input, delBtn)
  }
  private fun handleTextChangeInput(input: EditText, delBtn: ImageButton) {
    val inputText = input.text
    val btnNextPage = binding.btnNextPage
    val scrollView = binding.scrollSignUp
    val email = binding.inputEmail
    val id = binding.inputId
    val pw = binding.inputPw
    val pwChk = binding.inputPwChk
    input.addTextChangedListener {
      if (inputText.toString().isEmpty()) delBtn.visibility = View.INVISIBLE
      else delBtn.visibility = View.VISIBLE
    
    
      val isActive = email.text.isNotEmpty() && id.text.isNotEmpty() && pw.text.isNotEmpty() && pwChk.text.isNotEmpty()
      if (isActive && (pw.text.toString() == pwChk.text.toString())) {
        btnNextPage.visibility = View.VISIBLE
        scrollView.setPadding(0, 0, 0, 250)
      } else {
        btnNextPage.visibility = View.INVISIBLE
        scrollView.setPadding(0, 0, 0, 0)
      }
    
      when(input) {
        email -> {
          val emailPattern = android.util.Patterns.EMAIL_ADDRESS
          val notifyMsg = binding.noticeEmail
          if (emailPattern.matcher(inputText.toString()).matches()) {
            notifyMsg.visibility = View.INVISIBLE
          } else {
            notifyMsg.visibility = View.VISIBLE
            notifyMsg.text = getString(R.string.email_error)
          }
        }
        pw -> {
          val notifyMsg = binding.noticePw
          if (Pattern.matches("^(?=.*[a-zA-Z])(?=.*[0-9]).{1,8}$", inputText.toString())) {
            notifyMsg.visibility = View.INVISIBLE
          } else {
            notifyMsg.visibility = View.VISIBLE
            notifyMsg.text = getString(R.string.pw_error)
          }
        }
        pwChk -> {
          if (pw.text.toString() == inputText.toString()) {
            binding.noticePwChk.visibility = View.VISIBLE
            binding.noticePwChk.text = getString(R.string.pass_pw_chk)
            binding.noticePwChk.setTextColor(Color.parseColor("#50c99b"))
          } else {
            binding.noticePwChk.visibility = View.VISIBLE
            binding.noticePwChk.text = getString(R.string.fail_pw_chk)
            binding.noticePwChk.setTextColor(Color.parseColor("#f05c5c"))
          }
        }
        else -> {}
      }
    }
  }
  private fun handleUtil() {
    binding.btnNextPage.setOnClickListener {
      val retrofit = Instance().instance
      val loginData = LoginDto(
        "${binding.inputEmail.text}",
        "${binding.inputId.text}",
        "${binding.inputPw.text}"
      )
      retrofit.create(UserService::class.java).also {
        Log.d("signupdata", "retrofit: $loginData")
        it.postLogin(loginData)
          .enqueue(object: Callback<LoginDto> {
            override fun onResponse(call: Call<LoginDto>, response: Response<LoginDto>) {
              Log.d("signupdata", "success: $response")
              
              // code=409, message=Conflict
              // code=201, message=Created,
              if (response.isSuccessful.not()) return
              Log.d("signupdata", "??: $response")
            }

            override fun onFailure(call: Call<LoginDto>, t: Throwable) {
              Log.d("signupdata", "error: $t")
            }
          })
      }
//      val intent = Intent(this, EnterUserInfoActivity::class.java)
//      startActivity(intent)
    }
  }
}