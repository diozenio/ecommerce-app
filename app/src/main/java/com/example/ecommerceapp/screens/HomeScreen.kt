package com.example.ecommerceapp.screens

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.product.ProductManager
import com.example.ecommerceapp.data.product.category.CategoryManager
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
    val productManager = remember { ProductManager() }
    val categoryManager = remember { CategoryManager() }

    var text by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember { mutableStateOf(categoryManager.categories.first()) }

    LaunchedEffect(Unit) {
        categoryManager.getCategories()
    }

    LaunchedEffect(Unit) {
        productManager.getProducts()
    }

    val filteredProducts = productManager.products.filter { product ->
        (selectedCategory.title == "All" || product.category == selectedCategory) &&
                (text.isBlank() || product.title.contains(text, ignoreCase = true))
    }

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
                text = text,
                onChangeValue = { text = it },
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
            )
        }
        if (categoryManager.loadingState) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(6) {
                    UISkeletonCategory()
                }
            }
        } else if (categoryManager.categories.size > 1) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(categoryManager.categories) { category ->
                    UISelector(
                        text = category.title,
                        isSelected = category == selectedCategory,
                        onClick = { selectedCategory = category }
                    )
                }
            }
        }

        if (productManager.loadingState) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) {
                    UISkeletonProductCard()
                }
            }
        } else if (productManager.products.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(filteredProducts, { it.id }) { product ->
                    UIProductCard(
                        product = product,
                        onUnsaveClick = {},
                    )
                }
            }
        } else {
            UIEmptyState(
                icon = UIIconName.CancelCircle,
                title = "Something went wrong loading your products!",
                description = "We couldn't find any product. Please refresh the page or try again later."
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
