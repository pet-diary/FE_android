package com.luvpets.petda.data.service

import android.content.Context
import okhttp3.JavaNetCookieJar
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.net.CookieManager

open class Instance(context: Context) {
    private val okHttpClient = OkHttpClient.Builder()
        .cookieJar(JavaNetCookieJar(CookieManager()))
        .addInterceptor(AppInterceptor(context))
        .build()
    open val instance: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.04:3000/api/")
        .client(okHttpClient)
        .addConverterFactory(NullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> {
            if (it.contentLength() == 0L) return@Converter EmptyResponse()
            delegate.convert(it)
        }
    }
}

class EmptyResponse {}
