package com.example.ecommerceapp.data.auth

import com.example.ecommerceapp.model.AuthResponse
import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.SignUpRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RemoteAuthService {
    @POST("signup")
    suspend fun signup(@Body request: SignUpRequest): Response<AuthResponse>

    @POST("login")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>
}