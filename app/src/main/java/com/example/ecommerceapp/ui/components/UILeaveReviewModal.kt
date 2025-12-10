package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UILeaveReviewModal(
    orderTitle: String,
    onDismiss: () -> Unit,
    onSubmit: (rating: Float, reviewText: String) -> Unit
) {
    var rating by remember { mutableStateOf(0) }
    var reviewText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f))
            .clickable { onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable(enabled = false, onClick = {}),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Colors.Primary0),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UIText(
                        text = "Leave a Review",
                        variant = UITextVariant.H4,
                        weight = UITextWeight.SemiBold,
                        color = Colors.Primary900
                    )
                    UIIcon(
                        icon = UIIconName.Cancel,
                        size = 24.dp,
                        color = Colors.Primary500,
                        modifier = Modifier.clickable { onDismiss() }
                    )
                }

                UIText(
                    text = "How was your order?",
                    variant = UITextVariant.B1,
                    weight = UITextWeight.SemiBold,
                    color = Colors.Primary900
                )

                UIText(
                    text = "Please give your rating and also your review.",
                    variant = UITextVariant.B2,
                    weight = UITextWeight.Regular,
                    color = Colors.Primary500
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..5) {
                        UIIcon(
                            icon = UIIconName.Star,
                            size = 40.dp,
                            color = if (i <= rating) Colors.Primary900 else Colors.Primary200,
                            modifier = Modifier.clickable {
                                rating = i
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = reviewText,
                    onValueChange = { reviewText = it },
                    placeholder = {
                        UIText(
                            text = "Write your review...",
                            variant = UITextVariant.B1,
                            weight = UITextWeight.Regular,
                            color = Colors.Primary400
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    minLines = 4,
                    maxLines = 6,
                    singleLine = false,
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Colors.Primary100,
                        focusedBorderColor = Colors.Primary900,
                        focusedTextColor = Colors.Primary900,
                    ),
                )

                Spacer(modifier = Modifier.height(8.dp))

                UIButton(
                    text = "Submit",
                    onClick = {
                        if (rating > 0) {
                            onSubmit(rating.toFloat(), reviewText)
                            onDismiss()
                        }
                    },
                    enabled = rating > 0,
                    variant = UIButtonVariant.Primary
                )
            }
        }
    }
}

