package com.example.ecommerceapp.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.ecommerceapp.ui.components.UIIcon
import com.example.ecommerceapp.ui.components.UIIconName
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
        topBar = {
            TopAppBar(
                title = { UIText(text = "Saved Items", variant = UITextVariant.H2, weight = UITextWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Handle back navigation */ }) {
                        UIIcon(icon = UIIconName.Arrow)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle notification click */ }) {
                        UIIcon(icon = UIIconName.Bell)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
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
                    ProductCard(product = product, onUnsaveClick = {
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

@SuppressLint("DefaultLocale")
@Composable
fun ProductCard(product: Product, onUnsaveClick: () -> Unit) {
    Column {
        Box {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.title,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(
                onClick = onUnsaveClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White, shape = CircleShape)
            ) {
                UIIcon(icon = UIIconName.HeartFilled)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        UIText(
            text = product.title,
            variant = UITextVariant.B2,
            weight = UITextWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(4.dp))

        UIText(
            text = "$ ${String.format("%.2f", product.price)}",
            variant = UITextVariant.B3,
            color = Colors.Primary600
        )
    }
}

@Composable
fun EmptySavedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        UIIcon(
            icon = UIIconName.HeartDuotone,
            color = Colors.Primary300,
            size = 48.dp
        )
        Spacer(modifier = Modifier.height(24.dp))
        UIText(
            text = "No Saved Items",
            variant = UITextVariant.H2,
            weight = UITextWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        UIText(
            text = "You don't have any saved items.",
            variant = UITextVariant.B3
        )
        UIText(
            text = "Go to home and add some items to your saved list.",
            variant = UITextVariant.B3
        )
    }
}
