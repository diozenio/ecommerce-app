package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UINavHeader(
    title: String,
    onBackPressed: () -> Unit,
    onNotificationPressed: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                bottom = 20.dp,
                start = 24.dp,
                end = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        UIIcon(icon = UIIconName.Arrow, modifier = Modifier.clickable { onBackPressed() })
        UIText(
            text = title,
            variant = UITextVariant.H3,
            weight = UITextWeight.SemiBold,
            color = Colors.Primary900
        )
        UIIcon(icon = UIIconName.Bell, modifier = Modifier.clickable { onNotificationPressed() })
    }
}