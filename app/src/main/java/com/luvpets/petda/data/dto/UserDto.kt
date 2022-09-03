package com.luvpets.petda.data.dto

data class SignUpDto (
    val email: String,
    val name: String,
    val password: String
)

data class EmailLoginDto (
    val email: String,
    val password: String
  )
