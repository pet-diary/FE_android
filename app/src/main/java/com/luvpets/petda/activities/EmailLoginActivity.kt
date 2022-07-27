package com.luvpets.petda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luvpets.petda.databinding.ActivityEmailLoginBinding

class EmailLoginActivity : AppCompatActivity() {
  private val binding by lazy { ActivityEmailLoginBinding.inflate(layoutInflater) }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    handleStartSignUp()
  }
  private fun handleStartSignUp() {
    binding.btnToSignUp.setOnClickListener {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }
  }
}