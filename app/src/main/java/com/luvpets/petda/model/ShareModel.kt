package com.luvpets.petda.model

data class ShareModel(
  val id: String,
  val category: String,
  val title: String,
  val content: String,
  val date: String,
  val watched: Int,
  val liked: Int,
  val imageUrl: String
)