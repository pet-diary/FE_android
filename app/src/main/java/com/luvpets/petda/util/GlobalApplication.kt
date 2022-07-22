package com.luvpets.petda.util

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import com.luvpets.petda.BuildConfig

class GlobalApplication: Application() {
  override fun onCreate() {
    super.onCreate()
    KakaoSdk.init(this, BuildConfig.KAKAO_KEY)
  }
}