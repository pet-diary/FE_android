package com.luvpets.petda.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class ShareDetailModel(
  val id: String,
  val category: String,
  val title: String,
  val content: String,
  val liked: Int,
  val petInfo: PetInfoEntity,
  val images: List<ShareDetailImagesModel>
)

data class ShareDetailImagesModel(
  val image: String
)

// entity : 개념 스키마 > 테이블
@Entity(tableName = "petInfo_table")
data class PetInfoEntity(
  @PrimaryKey(autoGenerate = true) val id: Int,
  val image: String,
  val name: String,
  val birth: String,
  val age: Int,
  val weight: Int,
  val breed: String
)
