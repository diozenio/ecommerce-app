package com.example.ecommerceapp.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ecommerceapp.data.AppContainer
import com.example.ecommerceapp.model.SavedViewModel
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIProductCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(
    navController: NavHostController,
    viewModel: SavedViewModel = viewModel(
        factory = AppContainer.provideSavedViewModelFactory(LocalContext.current)
    )
) {
    val hasSavedItems by viewModel.hasSavedItems.collectAsState()
    val savedItems by viewModel.savedItems.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            UINavHeader(
                title = "Saved Items",
                onBackPressed = { /*TODO*/ },
                onNotificationPressed = {
                    navController.navigate("notification")
                }
            )
        }
    ) { paddingValues ->
        if (hasSavedItems) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(savedItems, key = { it.id }) { item ->
                    UIProductCard(product = item.product, onUnsaveClick = {
                        viewModel.unsaveItem(item.product)
                    })
                }
            }
        } else {
            Box(modifier = Modifier.padding(paddingValues)) {
                EmptySavedScreen()
            }
        }
    }
}

@Composable
fun EmptySavedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        UIEmptyState(
            icon = UIIconName.HeartDuotone,
            title = "Your Saved list Is Empty!",
            description = "When you save products, they'll appear here."
        )
    }
}