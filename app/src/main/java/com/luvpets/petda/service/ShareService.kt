package com.luvpets.petda.service

import com.luvpets.petda.dto.ShareDto
import com.luvpets.petda.model.ShareDetailModel
import retrofit2.Call
import retrofit2.http.GET

interface ShareService {
  @GET("/v3/7f31a8d5-4fcf-491b-8a47-120c7617aa23")
  fun getShareList() : Call<ShareDto>
  
  @GET("/v3/a0a7944c-6442-42ad-bf2a-cda17cef06af")
  fun getShareDetail() : Call<ShareDetailModel>
}