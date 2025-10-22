package com.example.ecommerceapp.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors

data class NavItem(
    val title: String,
    val icon: UIIconName,
    val onClick: () -> Unit,
    val showRightIcon: Boolean = true
)

data class UINavItemColors(
    val contentColor: Color = Colors.Primary900,
    val leadingIconColor: Color = Colors.Primary900,
    val trailingIconColor: Color = Colors.Primary300,
    val rippleColor: Color = Colors.Primary300
)

@Composable
fun UINavItem(
    title: String,
    onClick: () -> Unit,
    leading: (@Composable () -> Unit)? = null,
    trailing: (@Composable () -> Unit)? = null,
    colors: UINavItemColors = UINavItemColors(),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val customRipple = remember { ripple(color = colors.rippleColor) }

    Box (
        modifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = customRipple,
            onClick = onClick
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                leading?.invoke()
                UIText(
                    text = title,
                    variant = UITextVariant.B1,
                    weight = UITextWeight.Regular,
                    color = colors.contentColor
                )
            }
            trailing?.invoke()
        }
    }
}

@Composable
fun UINavItem(
    title: String,
    leftIcon: UIIconName,
    showRightIcon: Boolean = true,
    onClick: () -> Unit,
    colors: UINavItemColors = UINavItemColors(),
) = UINavItem(
    title = title,
    onClick = onClick,
    colors = colors,
    leading = {
        UIIcon(
            icon = leftIcon,
            color = colors.leadingIconColor,
            size = 24.dp
        )
    },
    trailing =  {
        if(showRightIcon) {
            UIIcon(
                icon = UIIconName.Chevron,
                modifier = Modifier.rotate(-90f),
                size = 24.dp,
                color = colors.trailingIconColor,
            )
        }
    }
)


@Preview(showBackground = true)
@Composable
fun UINavItemPreview() {
    UINavItem(
        title = "My Orders",
        leftIcon = UIIconName.Box,
        onClick = {}
    )
}