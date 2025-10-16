package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.model.BottomNavBarItem
import com.example.ecommerceapp.ui.theme.Colors

@Composable
fun UIBottomNavBar(
    bottomNavBarItems: List<BottomNavBarItem>,
    selectedItem: BottomNavBarItem,
    onItemChanged: (BottomNavBarItem) -> Unit,
) {
    NavigationBar(
        containerColor = Colors.Primary0,
        modifier = Modifier.drawBehind {
            drawLine(
                color = Colors.Primary100,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 2.dp.toPx()
            )
        }
    ) {
        bottomNavBarItems.forEach { item ->
            val selected = selectedItem.label == item.label
            NavigationBarItem(
                selected = selected,
                onClick = {
                    onItemChanged(item)
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        UIIcon(
                            icon = item.icon,
                            color = if (selected) Colors.Primary900 else Colors.Primary400
                        )
                        UIText(
                            text = item.label,
                            variant = UITextVariant.B3,
                            weight = UITextWeight.Medium,
                            color = if (selected) Colors.Primary900 else Colors.Primary400
                        )
                    }
                },
                label = null,
                colors = NavigationBarItemColors(
                    selectedIndicatorColor = Color.Transparent,
                    selectedIconColor = Colors.Primary900,
                    selectedTextColor = Colors.Primary900,
                    unselectedIconColor = Colors.Primary400,
                    unselectedTextColor = Colors.Primary400,
                    disabledIconColor = Colors.Primary400,
                    disabledTextColor = Colors.Primary400
                )
            )
        }
    }
}