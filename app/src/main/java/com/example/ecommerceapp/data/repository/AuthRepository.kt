package com.example.ecommerceapp.data.repository

import com.example.ecommerceapp.data.auth.AuthResult
import com.example.ecommerceapp.data.auth.RemoteAuthService
import com.example.ecommerceapp.data.auth.UserSessionDao
import com.example.ecommerceapp.model.LoginRequest
import com.example.ecommerceapp.model.SignUpRequest
import com.example.ecommerceapp.model.UserSession
import kotlinx.coroutines.flow.Flow
import java.io.IOException

class AuthRepository(
    private val remoteAuthService: RemoteAuthService,
    private val userSessionDao: UserSessionDao
) {
    suspend fun register(name: String, email: String, password: String): AuthResult {
        return try {
            val request = SignUpRequest(fullName = name, email = email, password = password)
            val response = remoteAuthService.signup(request)

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

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val request = LoginRequest(email = email, password = password)
            val response = remoteAuthService.login(request)

            if (response.isSuccessful && response.body() != null) {
                val userFromApi = response.body()!!.user

                val session = UserSession(
                    id = userFromApi.id,
                    fullName = userFromApi.fullName,
                    email = userFromApi.email
                )
                userSessionDao.saveSession(session)

                AuthResult.Success(user = userFromApi)
            } else {
                AuthResult.ApiError("E-mail ou senha inválidos.")
            }
        } catch (e: IOException) {
            AuthResult.NetworkError
        }
    }

    fun getActiveSession(): Flow<UserSession?> {
        return userSessionDao.getActiveSession()
    }

    suspend fun logout() {
        userSessionDao.deleteSession()
    }

    companion object
}