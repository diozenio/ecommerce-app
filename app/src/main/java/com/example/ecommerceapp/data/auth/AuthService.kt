package com.example.ecommerceapp.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

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


interface AuthService {
    @POST("signup")
    suspend fun signup(@Body request: SignUpRequest): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}