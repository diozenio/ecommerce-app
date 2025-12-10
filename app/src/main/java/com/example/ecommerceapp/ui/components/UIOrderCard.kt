package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ecommerceapp.R
import com.example.ecommerceapp.model.Order
import com.example.ecommerceapp.model.OrderStatus
import com.example.ecommerceapp.model.ProductSize
import com.example.ecommerceapp.model.toSizeString
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.util.toCurrencyString

@Composable
fun UIOrderCard(
    modifier: Modifier = Modifier,
    order: Order,
    onTrackOrderClick: () -> Unit = {},
    onLeaveReviewClick: () -> Unit = {}
) {
    val isCompleted = order.status == OrderStatus.DELIVERED
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(
                width = 1.dp,
                color = Colors.Primary100,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Primary0),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            Arrangement.spacedBy(12.dp),
            Alignment.CenterVertically
        ) {
            AsyncImage(
                model = order.imageUrl,
                contentDescription = "${order.title} image",
                placeholder = painterResource(R.drawable.fallback_square),
                error = painterResource(R.drawable.fallback_square),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    UIText(
                        text = order.title,
                        variant = UITextVariant.B1,
                        weight = UITextWeight.SemiBold,
                        color = Colors.Primary900,
                        modifier = Modifier.weight(1f)
                    )
                    
                    OrderStatusBadge(status = order.status)
                }
                
                UIText(
                    text = order.size.toSizeString(),
                    variant = UITextVariant.B2,
                    weight = UITextWeight.Regular,
                    color = Colors.Primary500
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UIText(
                        text = order.price.toCurrencyString(),
                        variant = UITextVariant.B1,
                        weight = UITextWeight.SemiBold,
                        color = Colors.Primary900
                    )
                    
                    if (isCompleted) {
                        order.rating?.let { rating ->
                            RatingBadge(rating = rating)
                        } ?: run {
                            UIButton(
                                text = "Leave Review",
                                onClick = onLeaveReviewClick,
                                height = 36.dp,
                                fullWidth = false,
                                variant = UIButtonVariant.Primary
                            )
                        }
                    } else {
                        UIButton(
                            text = "Track Order",
                            onClick = onTrackOrderClick,
                            height = 36.dp,
                            fullWidth = false,
                            variant = UIButtonVariant.Primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun OrderStatusBadge(status: OrderStatus) {
    val (text, backgroundColor, textColor) = when (status) {
        OrderStatus.PACKING -> Triple("Packing", Colors.Primary100, Colors.Primary900)
        OrderStatus.PICKED -> Triple("Picked", Colors.Primary100, Colors.Primary900)
        OrderStatus.IN_TRANSIT -> Triple("In Transit", Colors.Primary100, Colors.Primary900)
        OrderStatus.DELIVERED -> Triple("Completed", Colors.GreenLight, Colors.GreenDark)
    }
    
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        UIText(
            text = text,
            variant = UITextVariant.B3,
            weight = UITextWeight.Medium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            color = textColor
        )
    }
}

@Composable
fun RatingBadge(rating: Float) {
    Card(
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Colors.Primary0),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            UIIcon(
                icon = UIIconName.Star,
                size = 16.dp,
                color = Colors.Yellow
            )
            UIText(
                text = "${String.format("%.1f", rating)}/5",
                variant = UITextVariant.B3,
                weight = UITextWeight.SemiBold,
                color = Colors.Primary900
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UIOrderCardPreview() {
    val sampleOrder = Order(
        id = "1",
        title = "Regular Fit Slogan",
        size = ProductSize.MEDIUM,
        price = 1190f,
        imageUrl = "https://picsum.photos/id/106/200/200",
        status = OrderStatus.IN_TRANSIT
    )
    
    Column(modifier = Modifier.padding(16.dp)) {
        UIOrderCard(order = sampleOrder)
    }
}

@Preview(showBackground = true)
@Composable
fun UICompletedOrderCardPreview() {
    val completedOrder = Order(
        id = "6",
        title = "Regular Fit Polo",
        size = ProductSize.LARGE,
        price = 1100f,
        imageUrl = "https://picsum.photos/id/107/200/200",
        status = OrderStatus.DELIVERED,
        rating = 4.5f
    )
    
    Column(modifier = Modifier.padding(16.dp)) {
        UIOrderCard(order = completedOrder)
    }
}

