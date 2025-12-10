package com.example.ecommerceapp.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.MainViewModel
import com.example.ecommerceapp.data.auth.AuthManager
import com.example.ecommerceapp.data.auth.UserSessionDao
import com.example.ecommerceapp.data.cart.CartDao
import com.example.ecommerceapp.data.core.APIService
import com.example.ecommerceapp.data.core.DatabaseHelper
import com.example.ecommerceapp.data.notification.NotificationDao
import com.example.ecommerceapp.data.order.OrderManager
import com.example.ecommerceapp.data.repository.SavedRepository
import com.example.ecommerceapp.data.review.ReviewManager
import com.example.ecommerceapp.data.saved.SavedDao
import com.example.ecommerceapp.model.SavedViewModel
import com.example.ecommerceapp.screens.auth.LoginViewModel
import com.example.ecommerceapp.screens.auth.SignUpViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

object AppContainer {
    private var database: DatabaseHelper? = null
    private var authManager: AuthManager? = null
    private var reviewManager: ReviewManager? = null
    private var orderManager: OrderManager? = null
    private var savedRepository: SavedRepository? = null
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private fun getDatabase(context: Context): DatabaseHelper {
        return database ?: synchronized(this) {
            database ?: DatabaseHelper.getInstance(context.applicationContext, applicationScope).also {
                database = it
            }
        }
    }

    private fun getUserSessionDao(context: Context): UserSessionDao {
        return getDatabase(context).userSessionDao()
    }

    fun getCartDao(context: Context): CartDao {
        return getDatabase(context).cartDao()
    }

    fun getNotificationDao(context: Context): NotificationDao {
        return getDatabase(context).notificationDao()
    }

    private fun getSavedDao(context: Context): SavedDao {
        return getDatabase(context).savedDao()
    }
    fun getAuthManager(context: Context): AuthManager {
        return authManager ?: synchronized(this) {
            authManager ?: AuthManager(
                authService = APIService.authService,
                userSessionDao = getUserSessionDao(context)
            ).also {
                authManager = it
            }
        }
    }

    fun getReviewManager(context: Context): ReviewManager {
        return reviewManager ?: synchronized(this) {
            reviewManager ?: ReviewManager(
                reviewDao = getDatabase(context).reviewDao()
            ).also {
                reviewManager = it
            }
        }
    }

    fun getOrderManager(context: Context): OrderManager {
        return orderManager ?: synchronized(this) {
            orderManager ?: OrderManager(
                orderApi = APIService.orderApi,
                reviewManager = getReviewManager(context)
            ).also {
                orderManager = it
            }
        }
    }

    fun getSavedRepository(context: Context): SavedRepository {
        return savedRepository ?: synchronized(this) {
            savedRepository ?: SavedRepository(
                savedApi = APIService.savedApi,
                savedDao = getSavedDao(context)
            ).also {
                savedRepository = it
            }
        }
    }

    class SavedViewModelFactory(
        private val repository: SavedRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SavedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SavedViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun provideSavedViewModelFactory(context: Context): SavedViewModelFactory {
        return SavedViewModelFactory(
            repository = getSavedRepository(context)
        )
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