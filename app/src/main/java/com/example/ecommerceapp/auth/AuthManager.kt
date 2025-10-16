package com.example.ecommerceapp.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AuthManager {
    var isAuthenticated by mutableStateOf(false)
        private set

    fun login() {
        isAuthenticated = true
    }

    fun logout() {
        isAuthenticated = false
    }
}
