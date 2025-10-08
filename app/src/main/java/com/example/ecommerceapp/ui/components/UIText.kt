package com.example.ecommerceapp.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.util.percent

/**
 * Variants:
 * H1  SemiBold  64 / 80
 * H2  SemiBold  32 / 100%
 * H3  SemiBold  24 / 120%
 * H4  SemiBold|Medium 20 / 120%
 * B1  Regular|Medium|SemiBold 16 / 140%
 * B2  Regular|Medium|SemiBold 14 / 140%
 * B3  Regular|Medium|SemiBold 12 / 140%
 */
enum class UITextVariant { H1, H2, H3, H4, B1, B2, B3 }
enum class UITextWeight { Regular, Medium, SemiBold }

@Composable
fun UIText(
    text: String,
    modifier: Modifier = Modifier,
    variant: UITextVariant = UITextVariant.B1,
    weight: UITextWeight = UITextWeight.Regular,
    color: Color = MaterialTheme.colorScheme.onSurface,
    textAlign: TextAlign? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = null,
) {
    val style = uiTextStyle(
        variant = variant,
        weight = weight,
        letterSpacing = letterSpacing,
        fontFamily = fontFamily
    )
    Text(
        text = text,
        modifier = modifier,
        style = style,
        color = color,
        textAlign = textAlign,
        maxLines = maxLines,
        overflow = overflow
    )
}

@Composable
private fun uiTextStyle(
    variant: UITextVariant,
    weight: UITextWeight,
    letterSpacing: TextUnit,
    fontFamily: FontFamily?
): TextStyle {
    // Weight
    val fw = when (weight) {
        UITextWeight.Regular -> FontWeight.Normal
        UITextWeight.Medium -> FontWeight.Medium
        UITextWeight.SemiBold -> FontWeight.SemiBold
    }

    // Size + LineHeight
    return when (variant) {
        UITextVariant.H1 -> {
            val fontSize = 64.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(80),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.displayLarge.fontFamily
            )
        }

        UITextVariant.H2 -> {
            val fontSize = 32.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(100),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.headlineLarge.fontFamily
            )
        }

        UITextVariant.H3 -> {
            val fontSize = 24.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(120),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.headlineMedium.fontFamily
            )
        }

        UITextVariant.H4 -> {
            val fontSize = 20.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(120),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.headlineSmall.fontFamily
            )
        }

        UITextVariant.B1 -> {
            val fontSize = 16.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(140),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.bodyLarge.fontFamily
            )
        }

        UITextVariant.B2 -> {
            val fontSize = 14.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(140),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.bodyMedium.fontFamily
            )
        }

        UITextVariant.B3 -> {
            val fontSize = 12.sp
            TextStyle(
                fontSize = fontSize,
                lineHeight = fontSize.percent(140),
                fontWeight = fw,
                letterSpacing = letterSpacing,
                fontFamily = fontFamily ?: MaterialTheme.typography.bodySmall.fontFamily
            )
        }
    }
}
