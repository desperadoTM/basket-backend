package ru.basket.features

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel (
    val login : String,
    val password : String
)

@Serializable
data class LoginResponse(
    val token : String
)