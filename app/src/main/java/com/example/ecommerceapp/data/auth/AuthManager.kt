package com.example.ecommerceapp.data.auth

import com.example.ecommerceapp.model.UserSession
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class AuthManager(
    private val authService: AuthService,
    private val userSessionDao: UserSessionDao
) {
    suspend fun register(name: String, email: String, password: String): AuthResult {
        return try {
            val request = SignUpRequest(fullName = name, email = email, password = password)
            val response = authService.signup(request)

            if (response.isSuccessful) {
                AuthResult.Success()
            } else {
                if (response.code() == 400) {
                    AuthResult.ApiError("Este e-mail já está em uso.")
                } else {
                    AuthResult.ApiError("Erro desconhecido da API.")
                }
            }
        } catch (e: IOException) {
            AuthResult.NetworkError
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