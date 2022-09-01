package com.luvpets.petda.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import com.luvpets.petda.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
  private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)
    
    handleStartKakao()
    handleStartEmail()
    handleStartSignUp()
  }
  
  private fun handleStartKakao() {
    binding.btnStartKakao.setOnClickListener {
//      val keyHash = Utility.getKeyHash(this)
//      Log.d("hash", keyHash)
      if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)){
        UserApiClient.instance.loginWithKakaoTalk(this, callback = callback)
      } else{
        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
      }
    }
  }
  private val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
    if (error != null) {
      when {
        error.toString() == AuthErrorCause.AccessDenied.toString() -> {
          Toast.makeText(this, "접근이 거부 되었습니다.(동의 취소)", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.InvalidClient.toString() -> {
          Toast.makeText(this, "유효하지 않은 앱입니다.", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
          Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태입니다.", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
          Toast.makeText(this, "요청 파라미터 오류입니다.", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.InvalidScope.toString() -> {
          Toast.makeText(this, "유효하지 않은 scope ID입니다.", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.Misconfigured.toString() -> {
          Toast.makeText(this, "설정이 올바르지 않습니다.(android key hash)", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.ServerError.toString() -> {
          Toast.makeText(this, "서버 내부 에러입니다.", Toast.LENGTH_SHORT).show()
        }
        error.toString() == AuthErrorCause.Unauthorized.toString() -> {
          Toast.makeText(this, "앱이 요청 권한이 없습니다.", Toast.LENGTH_SHORT).show()
        }
        else -> { // Unknown
          Toast.makeText(this, "기타 에러입니다.", Toast.LENGTH_SHORT).show()
        }
      }
    }
    else if (token != null) {
      val intent = Intent(this, HomeActivity::class.java)
      startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
      finish()
    }
  }
  private fun handleStartEmail() {
    binding.btnStartEmail.setOnClickListener {
      val intent = Intent(this, EmailLoginActivity::class.java)
      startActivity(intent)
    }
  }
  private fun handleStartSignUp() {
    binding.btnToSignUp.setOnClickListener {
      val intent = Intent(this, SignUpActivity::class.java)
      startActivity(intent)
    }
  }
  
}