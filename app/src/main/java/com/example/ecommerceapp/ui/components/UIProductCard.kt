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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ecommerceapp.R
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.UIProductCardCartSize
import com.example.ecommerceapp.ui.theme.Colors

@SuppressLint("DefaultLocale")
@Composable
fun UIProductCard(
    modifier: Modifier = Modifier,
    product: Product,
    onUnsaveClick: () -> Unit,
    isSaved: Boolean = false
) {
    Card(
        modifier = Modifier
            .clickable {},
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Card(
            modifier = Modifier.clickable {},
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                Box {
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
                    IconButton(
                        onClick = onUnsaveClick,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .background(Color.White, shape = RoundedCornerShape(size = 10.dp))
                    ) {
                        UIIcon(icon = if (isSaved) UIIconName.HeartFilled else UIIconName.Heart)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
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
}

@Preview(showBackground = true, name = "Product Card Preview")
@Composable
fun UIProductCardPreview() {
    val sampleProduct = Product(
        id = "id",
        title = "Camiseta de Algodão Premium Azul",
        size = UIProductCardCartSize.MEDIUM,
        price = 12.5f,
        imageUrl = "https://picsum.photos/id/1015/300/400",
        discount = 12f,
        category = Category(id = "id", title = "Camiseta")
    )

    Column(modifier = Modifier.padding(16.dp)) {
        UIProductCard(
            product = sampleProduct,
            onUnsaveClick = { /* Ação de desmarcar */ },
            modifier = Modifier.width(180.dp)
        )
    }
}