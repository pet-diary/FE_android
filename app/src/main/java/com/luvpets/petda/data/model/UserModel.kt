package com.luvpets.petda.data.model

data class UserModel(
    val id: Int,
    val email: String,
    val name: String,
    val profileImage: String,
    val family: FamilyModel
)

data class FamilyModel(
    val id: Int
)