package com.example.ecommerceapp.data.auth

import com.example.ecommerceapp.model.UserApiResponse


sealed class AuthResult {
    data class Success(val user: UserApiResponse? = null) : AuthResult()

    data class ApiError(val message: String) : AuthResult()

    object NetworkError : AuthResult()
}