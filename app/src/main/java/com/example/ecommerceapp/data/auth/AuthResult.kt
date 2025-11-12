package com.example.ecommerceapp.data.auth


sealed class AuthResult {
    data class Success(val user: UserApiResponse? = null) : AuthResult()

    data class ApiError(val message: String) : AuthResult()

    object NetworkError : AuthResult()
}