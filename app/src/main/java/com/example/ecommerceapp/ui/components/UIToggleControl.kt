package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UIToggleControl(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Colors.Primary100, RoundedCornerShape(16.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(0.dp)
    ) {
        items.forEachIndexed { index, text ->
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(
                        if (selectedIndex == index) Colors.Primary0 else Color.Transparent,
                        RoundedCornerShape(12.dp)
                    )
                    .clickable { onItemSelected(index) }
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                UIText(
                    text = text,
                    variant = UITextVariant.B1,
                    weight = UITextWeight.Medium,
                    color = if (selectedIndex == index) Colors.Primary900 else Colors.Primary400
                )
            }
        }
    }
}

