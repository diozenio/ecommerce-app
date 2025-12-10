package com.example.ecommerceapp.screens.orders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.order.OrderManager
import com.example.ecommerceapp.data.review.ReviewManager
import com.example.ecommerceapp.model.Order
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface OrdersUiState {
    data object Loading : OrdersUiState
    data class Success(val orders: List<Order>) : OrdersUiState
    data class Error(val message: String) : OrdersUiState
}

class MyOrdersViewModel(
    private val orderManager: OrderManager,
    private val reviewManager: ReviewManager
) : ViewModel() {

    private val _uiState = MutableStateFlow<OrdersUiState>(OrdersUiState.Loading)
    val uiState: StateFlow<OrdersUiState> = _uiState.asStateFlow()

    fun loadOrders() {
        viewModelScope.launch {
            _uiState.value = OrdersUiState.Loading
            val result = orderManager.loadOrders()
            _uiState.value = if (result.isSuccess) {
                OrdersUiState.Success(result.getOrNull() ?: emptyList())
            } else {
                OrdersUiState.Error("Não foi possível conectar com nossos servidores")
            }
        }
    }

    fun submitReview(order: Order, rating: Float, reviewText: String) {
        viewModelScope.launch {
            reviewManager.saveReview(order.id, rating, reviewText)

            val currentState = _uiState.value
            if (currentState is OrdersUiState.Success) {
                val updatedOrders = currentState.orders.map {
                    if (it.id == order.id) it.copy(rating = rating) else it
                }
                _uiState.value = OrdersUiState.Success(updatedOrders)
            }
        }
    }
}


