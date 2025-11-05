package com.example.ecommerceapp.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedViewModel
import com.example.ecommerceapp.ui.components.UIEmptyState
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
import com.example.ecommerceapp.ui.components.UINavHeader
import com.example.ecommerceapp.ui.components.UIProductCard
import com.example.ecommerceapp.ui.components.UIText
import com.example.ecommerceapp.ui.components.UITextVariant
import com.example.ecommerceapp.ui.components.UITextWeight
import com.example.ecommerceapp.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedScreen(viewModel: SavedViewModel = viewModel()) {
    val hasSavedItems by viewModel.hasSavedItems.collectAsState()
    val savedItems by viewModel.savedItems.collectAsState()

    Scaffold(
        containerColor = Color.White,
        topBar = {
            UINavHeader(
                title = "Saved Items",
                onBackPressed = { /*TODO*/ },
                onNotificationPressed = { /*TODO*/ }
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
                items(savedItems, key = { it.id }) { product ->
                    UIProductCard (product = product, onUnsaveClick = {
                        viewModel.unsaveItem(product)
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