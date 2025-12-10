package com.example.ecommerceapp.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class SignUpRequest(
    val fullName: String,
    val email: String,
    val password: String
)

data class AuthResponse(
    val message: String,
    val user: UserApiResponse
)

data class UserApiResponse(
    val id: String,
    val fullName: String,
    val email: String,
    val password: String
)