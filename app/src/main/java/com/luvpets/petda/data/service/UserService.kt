package com.luvpets.petda.data.service

import com.luvpets.petda.data.dto.LoginDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/users/signup")
    fun postLogin(@Body loginDto: LoginDto) : Call<LoginDto>
}