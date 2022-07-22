package com.luvpets.petda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.luvpets.petda.databinding.ActivityEmailLoginBinding

class EmailLoginActivity : AppCompatActivity() {
  private var _binding: ActivityEmailLoginBinding? = null
  private val binding get() = _binding!!
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    
    _binding = ActivityEmailLoginBinding.inflate(layoutInflater)
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