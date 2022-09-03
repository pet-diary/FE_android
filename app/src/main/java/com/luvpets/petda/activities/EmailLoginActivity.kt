package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.data.dto.EmailLoginDto
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.UserService
import com.luvpets.petda.databinding.ActivityEmailLoginBinding
import com.luvpets.petda.util.dialog.CustomDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailLoginActivity : AppCompatActivity() {
  private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    handleInput()
    handleStartSignUp()
    handleClickLogin()
  }

  private fun handleInput() {
    handleInput(binding.inputId, binding.deleteInputId)
    handleInput(binding.inputPw, binding.deleteInputPw)
  }

  private fun handleInput(input: EditText, delBtn: ImageButton) {
    delBtn.setOnClickListener {
      input.text = null
      delBtn.visibility = View.INVISIBLE
      handleTextChangeInput(input, delBtn)
    }
    handleTextChangeInput(input, delBtn)
  }
  private fun handleTextChangeInput(input: EditText, delBtn: ImageButton) {
    val inputText = input.text
    input.addTextChangedListener {
      if (inputText.isEmpty()) {
        delBtn.visibility = View.INVISIBLE
      } else {
        delBtn.visibility = View.VISIBLE
      }
    }
  }

  private fun handleStartSignUp() {
    binding.btnToSignUp.setOnClickListener {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }
  }

  private fun handleClickLogin() {
    binding.btnLogin.setOnClickListener {
      val retrofit = Instance().instance
      val emailLoginDto = EmailLoginDto(
        "${binding.inputId.text}",
        "${binding.inputPw.text}"
      )
      retrofit.create(UserService::class.java).also {
        val dialog = CustomDialog(this@EmailLoginActivity)
        it.emailLogin(emailLoginDto)
          .enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
              if (response.isSuccessful) {
                handleNextStep()
              } else {
                dialog.showDialog(
                  "로그인에 실패하였습니다. error: ${response.message()}",
                  response.isSuccessful
                )
                dialog.setOnConfirmedListener { handleNextStep() }
              }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
              dialog.showDialog("로그인에 실패하였습니다. error: $t", false)
            }
          })
      }

    }
  }

  private fun handleNextStep() {
    val intent = Intent(this, HomeActivity::class.java)
    startActivity(intent)
  }
}