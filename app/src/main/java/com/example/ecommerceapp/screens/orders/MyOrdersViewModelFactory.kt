package com.example.ecommerceapp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.data.repository.OrderRepository
import com.example.ecommerceapp.data.repository.ReviewRepository

class MyOrdersViewModelFactory(
    private val orderRepository: OrderRepository,
    private val reviewRepository: ReviewRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyOrdersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyOrdersViewModel(orderRepository, reviewRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


