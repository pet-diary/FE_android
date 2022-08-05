package com.luvpets.petda.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Instance {
  open val instance = Retrofit.Builder()
    .baseUrl("https://run.mocky.io/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}