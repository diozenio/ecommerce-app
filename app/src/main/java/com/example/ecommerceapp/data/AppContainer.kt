package com.example.ecommerceapp.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.MainViewModel
import com.example.ecommerceapp.data.auth.AuthManager
import com.example.ecommerceapp.data.auth.UserSessionDao
import com.example.ecommerceapp.data.core.ApiService
import com.example.ecommerceapp.data.core.DatabaseHelper
import com.example.ecommerceapp.screens.auth.LoginViewModel
import com.example.ecommerceapp.screens.auth.SignUpViewModelFactory

object AppContainer {
    private var database: DatabaseHelper? = null
    private var authManager: AuthManager? = null

    private fun getDatabase(context: Context): DatabaseHelper {
        return database ?: synchronized(this) {
            database ?: DatabaseHelper.getInstance(context.applicationContext).also {
                database = it
            }
        }
    }

    private fun getUserSessionDao(context: Context): UserSessionDao {
        return getDatabase(context).userSessionDao()
    }

    fun getAuthManager(context: Context): AuthManager {
        return authManager ?: synchronized(this) {
            authManager ?: AuthManager(
                authService = ApiService.authService,
                userSessionDao = getUserSessionDao(context)
            ).also {
                authManager = it
            }
        }
    }

    fun provideSignUpViewModelFactory(context: Context): SignUpViewModelFactory {
        return SignUpViewModelFactory(
            authManager = getAuthManager(context),
        )
    }

    fun provideMainViewModelFactory(context: Context): MainViewModelFactory {
        return MainViewModelFactory(
            authManager = getAuthManager(context)
        )
    }

    class MainViewModelFactory(
        private val authManager: AuthManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(authManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun provideLoginViewModelFactory(context: Context): LoginViewModelFactory {
        return LoginViewModelFactory(
            authManager = getAuthManager(context)
        )
    }

    class LoginViewModelFactory(
        private val authManager: AuthManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(authManager) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}