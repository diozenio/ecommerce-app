package com.example.ecommerceapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ecommerceapp.ui.theme.Colors
import com.example.ecommerceapp.ui.theme.EcommerceAppTheme

enum class UIButtonVariant { Primary, Secondary, Disabled }

object UIButtonDefaults {
    val Shape = RoundedCornerShape(10.dp)

    @Composable
    fun colors(variant: UIButtonVariant): ButtonColors = when (variant) {
        UIButtonVariant.Primary -> ButtonDefaults.buttonColors(
            containerColor = Colors.Primary900,
            contentColor = Colors.Primary0
        )

        UIButtonVariant.Secondary -> ButtonDefaults.filledTonalButtonColors(
            containerColor = Colors.Primary0,
            contentColor = Colors.Primary900
        )

        UIButtonVariant.Disabled -> ButtonDefaults.buttonColors(
            disabledContainerColor = Colors.Primary200,
            disabledContentColor = Colors.Primary0
        )
    }
}

@Composable
fun UIButton(
    text: String,
    onClick: () -> Unit = {},
    leftIcon: (@Composable () -> Unit)? = null,
    rightIcon: (@Composable () -> Unit)? = null,
    modifier: Modifier = Modifier,
    height: Dp = 54.dp,
    fullWidth: Boolean = true,
    variant: UIButtonVariant = UIButtonVariant.Primary,
    enabled: Boolean = true,
) {
    val sizeModifier = modifier.height(height)
    val baseModifier = if (fullWidth) sizeModifier.fillMaxWidth() else sizeModifier

    val colors = UIButtonDefaults.colors(variant)
    val shape = UIButtonDefaults.Shape
    val finalEnabled = enabled && (variant != UIButtonVariant.Disabled)

    when (variant) {
        UIButtonVariant.Primary -> {
            Button(
                onClick = onClick,
                modifier = baseModifier,
                enabled = finalEnabled,
                shape = shape,
                colors = colors,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    leftIcon?.invoke()
                    UIText(
                        text,
                        variant = UITextVariant.B1,
                        weight = UITextWeight.Medium,
                        color = colors.contentColor
                    )
                    rightIcon?.invoke()
                }
            }
        }

        UIButtonVariant.Secondary -> {
            OutlinedButton(
                onClick = onClick,
                modifier = baseModifier,
                enabled = finalEnabled,
                shape = shape,
                colors = colors,
                border = BorderStroke(1.dp, Colors.Primary200)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    leftIcon?.invoke()
                    UIText(
                        text,
                        variant = UITextVariant.B1,
                        weight = UITextWeight.Medium,
                        color = colors.contentColor
                    )
                    rightIcon?.invoke()
                }
            }
        }

        UIButtonVariant.Disabled -> {
            Button(
                onClick = {},
                modifier = baseModifier,
                enabled = false,
                shape = shape,
                colors = colors,
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    leftIcon?.invoke()
                    UIText(
                        text,
                        variant = UITextVariant.B1,
                        weight = UITextWeight.Medium,
                        color = colors.disabledContentColor
                    )
                    rightIcon?.invoke()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UIButtonPreview() {
    EcommerceAppTheme {
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            UIButton("Primary", variant = UIButtonVariant.Primary, onClick = {})
            UIButton("Secondary", variant = UIButtonVariant.Secondary, onClick = {})
            UIButton("Disabled", variant = UIButtonVariant.Disabled)
        }
    }
}
