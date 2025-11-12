package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UISelector(
    modifier: Modifier = Modifier,
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

@Preview(showBackground = true, name = "UI Selector Preview")
@Composable
fun UISelectorPreview() {
    Column(modifier = Modifier.padding(16.dp)) {
        UIText(text = "Unselected State:", variant = UITextVariant.B2, weight = UITextWeight.SemiBold, color = Color.Black)
        Row(modifier = Modifier.padding(top = 4.dp)) {
            UISelector(text = "Opção 1", onClick = { /* TODO */ }, isSelected = false)
            UISelector(text = "Opção Longa Sem Seleção", onClick = { /* TODO */ }, isSelected = false, modifier = Modifier.padding(start = 8.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        UIText(text = "Selected State:", variant = UITextVariant.B2, weight = UITextWeight.SemiBold, color = Color.Black)
        Row(modifier = Modifier.padding(top = 4.dp)) {
            UISelector(text = "Opção 2", onClick = { /* TODO */ }, isSelected = true)
            UISelector(text = "Opção Longa Selecionada", onClick = { /* TODO */ }, isSelected = true, modifier = Modifier.padding(start = 8.dp))
        }
    }
}

@Composable
fun UISkeletonCategory() {
    Box(
        modifier = Modifier
            .height(40.dp)
            .widthIn(min = 80.dp, max = 120.dp)
            .clip(RoundedCornerShape(size = 8.dp))
            .background(Colors.Primary100)
    )
}