package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UIEmptyState(
    icon: UIIconName,
    title: String,
    description: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 4.dp, start = 24.dp, end = 24.dp),
    ) {
        HorizontalDivider(thickness = 1.dp, color = Colors.Primary100)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UIIcon(icon = icon, size = 64.dp, color = Colors.Primary300)
            UIText(
                modifier = Modifier.padding(top = 20.dp, bottom = 12.dp),
                text = title,
                variant = UITextVariant.H4,
                weight = UITextWeight.SemiBold,
                color = Colors.Primary900,
                textAlign = TextAlign.Center
            )
            UIText(
                modifier = Modifier.width(248.dp),
                text = description,
                variant = UITextVariant.B1,
                weight = UITextWeight.Regular,
                color = Colors.Primary500,
                textAlign = TextAlign.Center
            )
        }
    }
}