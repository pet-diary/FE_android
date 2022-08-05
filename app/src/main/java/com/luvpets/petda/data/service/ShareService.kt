package com.luvpets.petda.data.service

import com.luvpets.petda.dto.PhotoDto
import com.luvpets.petda.dto.ShareDto
import com.luvpets.petda.data.model.ShareDetailModel
import retrofit2.Call
import retrofit2.http.GET

interface ShareService {
  @GET("/v3/85c4fc0f-97a4-4cdc-acd1-4f4f79cb51a8")
  fun getShareList() : Call<ShareDto>
  
  @GET("/v3/fcd7863d-d58b-4f1c-9a0a-2ba90ddf6fd4")
  fun getShareDetail() : Call<ShareDetailModel>
  
  @GET("/v3/a8ae11e9-1947-49ce-8d3a-c59f69280662")
  fun getPhotoList() : Call<PhotoDto>
}