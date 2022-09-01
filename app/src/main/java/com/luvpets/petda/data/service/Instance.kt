package com.luvpets.petda.data.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class Instance {
  open val instance = Retrofit.Builder()
    .baseUrl("http://localhost:3000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
}