package com.example.ecommerceapp.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecommerceapp.data.repository.CategoryRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val filteredProducts: List<Product> = emptyList(),
    val categories: List<Category> = emptyList(),
    val isLoadingProducts: Boolean = true,
    val isLoadingCategories: Boolean = true,
    val selectedCategory: Category? = null,
    val searchQuery: String = ""
)

class HomeViewModel(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchAllData()
    }

    fun fetchAllData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoadingCategories = true, isLoadingProducts = true) }

            val catDeferred = async {
                val cats = categoryRepository.getCategories()
                val allCategory = Category("all_id", "All")
                listOf(allCategory) + cats
            }

            val prodDeferred = async {
                productRepository.getProducts()
            }

            val categories = catDeferred.await()
            val products = prodDeferred.await()

            _uiState.update {
                it.copy(
                    categories = categories,
                    products = products,
                    selectedCategory = categories.firstOrNull(),
                    isLoadingCategories = false,
                    isLoadingProducts = false
                )
            }
            applyFilters()
        }
    }

    fun onCategorySelected(category: Category) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilters()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    private fun applyFilters() {
        _uiState.update { currentState ->
            val filtered = currentState.products.filter { product ->
                val matchesCategory =
                    currentState.selectedCategory?.title == "All" || product.category.id == currentState.selectedCategory?.id
                val matchesSearch = currentState.searchQuery.isBlank() || product.title.contains(
                    currentState.searchQuery,
                    ignoreCase = true
                )
                matchesCategory && matchesSearch
            }
            currentState.copy(filteredProducts = filtered)
        }
    }


    class Factory(
        private val prodRepo: ProductRepository,
        private val catRepo: CategoryRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(prodRepo, catRepo) as T
        }
    }
}