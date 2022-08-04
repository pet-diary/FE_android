package com.luvpets.petda.model

data class PhotoModel(
  val id: String,
  val writer: PhotoWriter,
  val tag: List<String>,
  val content: String,
  val imageUrl: String
)

data class PhotoWriter(
  val name: String,
  val profile: String
)
