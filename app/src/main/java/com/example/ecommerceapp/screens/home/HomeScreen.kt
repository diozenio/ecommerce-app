package com.example.ecommerceapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.core.APIService
import com.example.ecommerceapp.data.core.DatabaseHelper
import com.example.ecommerceapp.data.repository.CategoryRepository
import com.example.ecommerceapp.data.repository.ProductRepository
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UIInput
import com.example.ecommerceapp.ui.components.UIProductCard
import com.example.ecommerceapp.ui.components.UISelector
import com.example.ecommerceapp.ui.components.UISkeletonCategory
import com.example.ecommerceapp.ui.components.UISkeletonProductCard
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun HomeScreen(navController: NavHostController) {
    val context = LocalContext.current

    val db = remember { DatabaseHelper.getInstance(context) }

    val productRepository = remember {
        ProductRepository(db.productDao(), APIService.productApi)
    }
    val categoryRepository = remember {
        CategoryRepository(db.categoryDao(), APIService.categoryApi)
    }

    val viewModel: HomeViewModel = viewModel(
        factory = HomeViewModel.Factory(productRepository, categoryRepository)
    )

    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        HomeHeader(navController)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            UIInput(
                text = uiState.searchQuery,
                onChangeValue = { viewModel.onSearchQueryChanged(it) },
                placeholderText = "Search for clothes...",
                leadingIcon = UIIconName.Search,
                trailingIcon = UIIconName.Mic,
                modifier = Modifier.weight(1f)
            )
            UIButton(
                text = "",
                leftIcon = {
                    UIIcon(
                        icon = UIIconName.Filter,
                        color = Colors.Primary0
                    )
                },
                fullWidth = false,
                onClick = {
                    viewModel.fetchAllData()
                }
            )
        }

        if (uiState.isLoadingCategories) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(6) { UISkeletonCategory() }
            }
        } else if (uiState.categories.isNotEmpty()) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(uiState.categories) { category ->
                    UISelector(
                        text = category.title,
                        isSelected = category == uiState.selectedCategory,
                        onClick = { viewModel.onCategorySelected(category) }
                    )
                }
            }
        }

        if (uiState.isLoadingProducts) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) { UISkeletonProductCard() }
            }
        } else if (uiState.filteredProducts.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(uiState.filteredProducts, key = { it.id }) { product ->
                    UIProductCard(
                        product = product,
                        onUnsaveClick = {},
                    )
                }
            }
        } else {
            UIEmptyState(
                icon = UIIconName.CancelCircle,
                title = "No products found",
                description = "Try changing your search or category."
            )
        }
    }
}


@Composable
fun HomeHeader(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        UIText(
            text = "Discover",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold
        )
        UIIcon(
            icon = UIIconName.Bell, modifier = Modifier.clickable {
                navController.navigate("notification")
            }
        )
    }
}
