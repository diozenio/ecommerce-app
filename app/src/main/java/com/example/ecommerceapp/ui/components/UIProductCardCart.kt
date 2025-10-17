package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.model.UIProductCardCartSize
import com.example.ecommerceapp.model.toSizeString
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.util.toCurrencyString


@Composable
fun UIProductCardCart(
    title: String,
    size: UIProductCardCartSize,
    price: Float,
    imageUrl: String,
    quantity: Int = 0,
    onRemoveItem: () -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(
                width = 1.dp,
                color = Colors.Primary100,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(all = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = "$title image",
            placeholder = painterResource(R.drawable.fallback_square),
            error = painterResource(R.drawable.fallback_square),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Column {
                    UIText(text = title, variant = UITextVariant.B2, weight = UITextWeight.SemiBold)
                    UIText(
                        modifier = Modifier.padding(top = 1.dp),
                        text = size.toSizeString(),
                        variant = UITextVariant.B3,
                    )
                }
                UIIcon(
                    icon = UIIconName.Trash,
                    size = 16.dp,
                    color = Colors.Red,
                    modifier = Modifier.clickable { onRemoveItem() }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                UIText(
                    text = price.toCurrencyString(),
                    variant = UITextVariant.B2,
                    weight = UITextWeight.SemiBold
                )
                Row(
                    modifier = Modifier.width(72.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    OutlinedButton(
                        onClick = onDecrement,
                        modifier = Modifier
                            .size(24.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Colors.Primary0,
                            contentColor = Colors.Primary900
                        ),
                        border = BorderStroke(1.dp, Colors.Primary200),
                        enabled = quantity > 1
                    ) {
                        UIIcon(icon = UIIconName.Minus, color = Colors.Primary900, size = 14.dp)
                    }
                    UIText(
                        text = quantity.toString(),
                        variant = UITextVariant.B3,
                        weight = UITextWeight.Medium
                    )
                    OutlinedButton(
                        onClick = onIncrement,
                        modifier = Modifier
                            .size(24.dp),
                        contentPadding = PaddingValues(0.dp),
                        shape = RoundedCornerShape(2.dp),
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = Colors.Primary0,
                            contentColor = Colors.Primary900
                        ),
                        border = BorderStroke(1.dp, Colors.Primary200)
                    ) {
                        UIIcon(icon = UIIconName.Plus, color = Colors.Primary900, size = 14.dp)
                    }
                }
            }
        }
    }
}