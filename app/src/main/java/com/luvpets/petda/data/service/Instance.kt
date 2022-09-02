package com.luvpets.petda.data.service

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

open class Instance {
  open val instance = Retrofit.Builder()
    .baseUrl("http://10.0.2.2:3000/api/")
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
