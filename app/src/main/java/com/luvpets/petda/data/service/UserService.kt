package com.luvpets.petda.data.service

import com.luvpets.petda.data.dto.EmailLoginDto
import com.luvpets.petda.data.dto.SignUpDto
import com.luvpets.petda.data.model.UserModel
import com.luvpets.petda.data.response.UserInfoResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("/users/signup")
    fun signup(@Body SignUpDto: SignUpDto): Call<SignUpDto>

    @POST("/users/login")
    fun emailLogin(@Body EmailLoginDto: EmailLoginDto): Call<UserInfoResponse>

    @GET("/users/info")
    fun userInfo(): Call<UserModel>
}