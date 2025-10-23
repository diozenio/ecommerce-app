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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.data.cart.CartManager
import com.example.ecommerceapp.ui.components.UIButton
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UIInput
import com.example.ecommerceapp.ui.components.UIProductCard
import com.example.ecommerceapp.ui.components.UISelector
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun HomeScreen() {
    val products = CartManager.products

    var text by remember {
        mutableStateOf("")
    }

    val categories = listOf("All", "Tshirts", "Jeans", "Shoes")

    var selectedCategory by remember { mutableStateOf(categories.first()) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        HomeHeader()
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
        LazyRow (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(categories) { category ->
                UISelector(
                    text = category,
                    isSelected = category == selectedCategory,
                    onClick = { selectedCategory = category }
                )
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(products, { it.id }) { product ->
                UIProductCard(
                    product = product,
                    onUnsaveClick = {},
                )
            }
        }

    }
}

@Composable
fun HomeHeader() {
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
            icon = UIIconName.Bell, modifier = Modifier.clickable { }
        )
    }
}
