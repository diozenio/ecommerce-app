package com.example.ecommerceapp.data.core

import com.example.ecommerceapp.data.auth.AuthService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://localhost:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val authService: AuthService = retrofit.create(AuthService::class.java)
}