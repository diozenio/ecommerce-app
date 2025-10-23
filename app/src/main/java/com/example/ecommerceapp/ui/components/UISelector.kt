package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UISelector(
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean,
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            UIText(
                text = text,
                variant = UITextVariant.B1,
                weight = UITextWeight.Medium,
                color = if (isSelected) Colors.Primary0 else Colors.Primary900,
                modifier = Modifier
                    .padding(16.dp, 8.dp)
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Colors.Primary900,
            containerColor = Colors.Primary0,
        ),

        border = if (isSelected) null else BorderStroke(1.dp, Colors.Primary100),
        modifier = Modifier
            .wrapContentWidth()
    )
}