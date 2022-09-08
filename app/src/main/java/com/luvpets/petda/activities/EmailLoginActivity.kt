package com.luvpets.petda.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.data.dto.EmailLoginDto
import com.luvpets.petda.data.model.UserModel
import com.luvpets.petda.data.response.UserInfoResponse
import com.luvpets.petda.data.service.Instance
import com.luvpets.petda.data.service.UserService
import com.luvpets.petda.databinding.ActivityEmailLoginBinding
import com.luvpets.petda.util.dialog.CustomDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class EmailLoginActivity : AppCompatActivity() {
  private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
  private lateinit var pref: SharedPreferences
  private lateinit var edit: SharedPreferences.Editor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    initPref()
    handleInput()
    handleStartSignUp()
    handleClickLogin()
  }

  private fun initPref() {
    pref = baseContext.getSharedPreferences("pref", MODE_PRIVATE)
    edit = pref.edit()
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
      val retrofit = Instance(baseContext).instance
      val dialog = CustomDialog(this@EmailLoginActivity)
      val emailLoginDto = EmailLoginDto(
        "${binding.inputId.text}",
        "${binding.inputPw.text}"
      )
      retrofit.create(UserService::class.java).also {

        it.emailLogin(emailLoginDto)
          .enqueue(object : Callback<UserInfoResponse> {
            override fun onResponse(call: Call<UserInfoResponse>, response: Response<UserInfoResponse>) {
              if (response.isSuccessful) {
                val token = response.headers()["set-cookie"]?.split(';')?.get(0)
                Log.d("successdata", "success token ${token}")
                if (token !== "") {
                  edit.putString("token", token)
                  edit.commit()
                }
                requestUserInfo()
              } else {
                Log.d("successdata", "success fail ${response}")
                dialog.showDialog(
                  "로그인에 실패하였습니다. error: ${response.message()}",
                  response.isSuccessful
                )
                dialog.setOnConfirmedListener { handleNextStep() }
              }
            }

            override fun onFailure(call: Call<UserInfoResponse>, t: Throwable) {
              Log.d("successdata", "fail ${t}")
              dialog.showDialog("로그인에 실패하였습니다. error: $t", false)
            }
          })
      }

    }
  }

  private fun requestUserInfo() {
    val retrofit = Instance(baseContext).instance
    val dialog = CustomDialog(this@EmailLoginActivity)
    retrofit.create(UserService::class.java).also {
      it.userInfo()
        .enqueue(object: Callback<UserModel> {
          override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
//            if (response.isSuccessful.not()) return
            Log.d("successdata", "userInfo success ${response}")
//            handleNextStep()
          }

          override fun onFailure(call: Call<UserModel>, t: Throwable) {
            dialog.showDialog("회원정보를 불러오는데 실패하였습니다. error: $t", false)
          }
        })
    }
  }

  private fun handleNextStep() {
    val intent = Intent(this, HomeActivity::class.java)
    startActivity(intent)
  }
}