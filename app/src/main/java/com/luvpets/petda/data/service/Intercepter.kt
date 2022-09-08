package com.luvpets.petda.data.service

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AppInterceptor(context: Context) : Interceptor {
    private val prefsFilename = "pref"
    private val prefsKeyEdt = "token"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)
    private val token = prefs.getString(prefsKeyEdt, "")

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain) : Response = with(chain) {
        val newRequest = request().newBuilder()
            .addHeader("Cookie", "$token")
            .build()

        proceed(newRequest)
    }
}