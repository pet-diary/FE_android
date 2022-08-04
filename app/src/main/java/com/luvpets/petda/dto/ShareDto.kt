package com.luvpets.petda.dto

import com.luvpets.petda.model.PhotoModel
import com.luvpets.petda.model.ShareModel

data class ShareDto(
  val items: List<ShareModel>
)

data class PhotoDto(
  val items: List<PhotoModel>
)
