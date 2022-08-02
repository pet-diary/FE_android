package com.luvpets.petda.model

data class ShareDetailModel(
  val id: String,
  val category: String,
  val title: String,
  val content: String,
  val liked: Int,
  val petInfo: ShareDetailPetModel,
  val images: List<ShareDetailImagesModel>
)

data class ShareDetailPetModel(
  val image: String,
  val name: String,
  val birth: String,
  val age: Int,
  val weight: Int,
  val breed: String
)

data class ShareDetailImagesModel(
  val image: String
)
