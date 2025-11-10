package com.example.ecommerceapp.data.auth

import com.example.ecommerceapp.model.UserSession
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class AuthManager(
    private val authService: AuthService,
    private val userSessionDao: UserSessionDao
) {
    suspend fun register(name: String, email: String, password: String): Boolean {
        return try {
            val request = SignUpRequest(fullName = name, email = email, password = password)
            val response = authService.signup(request)

            response.isSuccessful
        } catch (e: IOException) {
            false
        }
    }

    suspend fun login(email: String, password: String): Boolean {
        return false
    }

    fun getActiveSession(): Flow<UserSession?> {
        return userSessionDao.getActiveSession()
    }

    suspend fun logout() {
        userSessionDao.deleteSession()
    }
}