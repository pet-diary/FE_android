package com.luvpets.petda.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import com.luvpets.petda.R
import com.luvpets.petda.databinding.ActivityEmailLoginBinding
import java.util.regex.Pattern

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
    input.setOnFocusChangeListener { _, is_focus ->
      if (is_focus) delBtn.visibility = View.VISIBLE
      else if (input.text.isEmpty()) delBtn.visibility = View.INVISIBLE
    
      delBtn.setOnClickListener {
        input.text = null
        delBtn.visibility = View.INVISIBLE
      }
      input.addTextChangedListener {
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
      val intent = Intent(this, HomeActivity::class.java)
      startActivity(intent)
    }
  }
}