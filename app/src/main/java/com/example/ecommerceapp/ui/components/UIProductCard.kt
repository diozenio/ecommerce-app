package com.example.ecommerceapp.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.R
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.ui.theme.Colors

@SuppressLint("DefaultLocale")
@Composable
fun UIProductCard(product: Product, onUnsaveClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable {}
    ) {
        Box {
            Card(
                shape = RoundedCornerShape(16.dp),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(product.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = product.title,
                    placeholder = painterResource(R.drawable.fallback_square),
                    error = painterResource(R.drawable.fallback_square),
                    modifier = Modifier
                        .aspectRatio(3f / 4f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
            }
            IconButton(
                onClick = onUnsaveClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .background(Color.White, shape = RoundedCornerShape(size = 10.dp))
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