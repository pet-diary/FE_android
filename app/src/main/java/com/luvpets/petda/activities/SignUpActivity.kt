package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.data.dto.SignUpDto
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.UserService
import com.luvpets.petda.databinding.ActivitySignUpBinding
import com.luvpets.petda.util.dialog.CustomDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
    handleInput(binding.inputEmail, binding.deleteInputEmail, binding.noticeEmail)
    handleInput(binding.inputId, binding.deleteInputId, binding.noticeId)
    handleInput(binding.inputPw, binding.deleteInputPw, binding.noticePw)
    handleInput(binding.inputPwChk, binding.deleteInputPwChk, binding.noticePwChk)
  }
  private fun handleInput(input: EditText, delBtn: ImageButton, notice: TextView) {
    delBtn.setOnClickListener {
      input.text = null
      delBtn.visibility = View.INVISIBLE
      notice.visibility = View.INVISIBLE
      binding.btnNextPage.visibility = View.INVISIBLE
      handleTextChangeInput(input, delBtn, notice)
    }
    handleTextChangeInput(input, delBtn, notice)
  }
  private fun handleTextChangeInput(input: EditText, delBtn: ImageButton, notice: TextView) {
    val inputText = input.text
    val btnNextPage = binding.btnNextPage
    val scrollView = binding.scrollSignUp
    val email = binding.inputEmail.text
    val id = binding.inputId.text
    val pw = binding.inputPw.text
    val pwChk = binding.inputPwChk.text
    var status: Boolean
    input.addTextChangedListener {
      if (inputText.isEmpty()) {
        delBtn.visibility = View.INVISIBLE
        notice.visibility = View.INVISIBLE
        if (pw.toString() != pwChk.toString()) {
          binding.noticePwChk.text ="*비밀번호가 일치하지 않습니다."
          binding.noticePwChk.setTextColor(Color.parseColor("#f05c5c"))
        }
      } else {
        delBtn.visibility = View.VISIBLE
        status = validation(input.text, notice)
        if (status) notice.visibility = View.VISIBLE
        else notice.visibility = View.INVISIBLE
      }

      val isFull = email.isNotEmpty() && id.isNotEmpty() && pw.isNotEmpty() && pwChk.isNotEmpty()
      if (
        isFull
        && (binding.noticeEmail.visibility == View.INVISIBLE)
        && (binding.noticePw.visibility == View.INVISIBLE)
        && (pw.toString() == pwChk.toString())
      ) {
        btnNextPage.visibility = View.VISIBLE
        scrollView.setPadding(0, 0, 0, 250)
      } else {
        btnNextPage.visibility = View.INVISIBLE
        scrollView.setPadding(0, 0, 0, 0)
      }
    }
  }

  private fun validation(
    input: Editable,
    notice: TextView
  ): Boolean {
    var status = false
    val emailPattern = android.util.Patterns.EMAIL_ADDRESS
    val pwPattern = "^(?=.*[a-zA-Z])(?=.*[0-9]).{1,8}\$"

    when (input) {
      binding.inputEmail.text -> {
        if (input.isNotEmpty() && !emailPattern.matcher(input).matches()) {
          status = true
          notice.text = "*이메일 형식이 올바르지 않습니다."
        }
      }
      binding.inputPw.text -> {
        if (input.isNotEmpty() && !Pattern.matches(pwPattern, input.toString())) {
          status = true
          notice.text = "*비밀번호는 영문,숫자 포함하여 8자 이내로 입력해주세요."
        }
        if (input.toString() != binding.inputPwChk.text.toString()) {
          binding.noticePwChk.text ="*비밀번호가 일치하지 않습니다."
          binding.noticePwChk.setTextColor(Color.parseColor("#f05c5c"))
        } else {
          binding.noticePwChk.text ="*비밀번호가 일치합니다."
          binding.noticePwChk.setTextColor(Color.parseColor("#50c99b"))
        }
      }
      binding.inputPwChk.text -> {
        status = true
        if (input.toString() != binding.inputPw.text.toString()) {
          notice.text ="*비밀번호가 일치하지 않습니다."
          notice.setTextColor(Color.parseColor("#f05c5c"))
        } else {
          notice.text ="*비밀번호가 일치합니다."
          notice.setTextColor(Color.parseColor("#50c99b"))
        }
      }
    }

    return status
  }
  private fun handleUtil() {
    binding.btnNextPage.setOnClickListener {
      val retrofit = Instance().instance
      val signUpDto = SignUpDto(
        "${binding.inputEmail.text}",
        "${binding.inputId.text}",
        "${binding.inputPw.text}"
      )
      retrofit.create(UserService::class.java).also {
        val dialog = CustomDialog(this@SignUpActivity)
        it.signup(signUpDto)
          .enqueue(object: Callback<SignUpDto> {
            override fun onResponse(call: Call<SignUpDto>, response: Response<SignUpDto>) {
              if (response.isSuccessful) {
                dialog.showDialog("회원가입이 완료되었습니다.", response.isSuccessful)
                dialog.setOnConfirmedListener{handleNextStep()}
              } else {
                dialog.showDialog("회원가입에 실패하였습니다. error: ${response.message()}", response.isSuccessful)
                dialog.setOnConfirmedListener{handleNextStep()}
              }
            }
            override fun onFailure(call: Call<SignUpDto>, t: Throwable) {
              dialog.showDialog("회원가입에 실패하였습니다. error: $t", false)
            }
          })
      }
    }
  }

  private fun handleNextStep() {
    val intent = Intent(this, EnterUserInfoActivity::class.java)
    startActivity(intent)
  }
}