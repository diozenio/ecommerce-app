package com.example.ecommerceapp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ecommerceapp.data.order.OrderManager
import com.example.ecommerceapp.data.review.ReviewManager

class MyOrdersViewModelFactory(
    private val orderManager: OrderManager,
    private val reviewManager: ReviewManager
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyOrdersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyOrdersViewModel(orderManager, reviewManager) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


