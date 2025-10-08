package com.example.ecommerceapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ecommerceapp.R

val GeneralSans = FontFamily(
    Font(R.font.general_sans_regular, FontWeight.Normal),
    Font(R.font.general_sans_medium, FontWeight.Medium),
    Font(R.font.general_sans_semibold, FontWeight.SemiBold),
)

val Typography = Typography(
    // --- Headings ---
    displayLarge = TextStyle( // H1
        fontFamily = GeneralSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 64.sp,
        lineHeight = 64.sp * 0.80,
        letterSpacing = 0.sp
    ),
    headlineLarge = TextStyle( // H2
        fontFamily = GeneralSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 32.sp,
        lineHeight = 32.sp * 1.0f,
        letterSpacing = 0.sp
    ),
    headlineMedium = TextStyle( // H3
        fontFamily = GeneralSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 24.sp * 1.2f,
        letterSpacing = 0.sp
    ),
    headlineSmall = TextStyle( // H4
        fontFamily = GeneralSans,
        fontWeight = FontWeight.Medium,
        fontSize = 20.sp,
        lineHeight = 20.sp * 1.2f,
        letterSpacing = 0.sp
    ),

    // --- Body ---
    bodyLarge = TextStyle( // B1
        fontFamily = GeneralSans,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 16.sp * 1.4f,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle( // B2
        fontFamily = GeneralSans,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 14.sp * 1.4f,
        letterSpacing = 0.sp
    ),
    bodySmall = TextStyle( // B3
        fontFamily = GeneralSans,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp * 1.4f,
        letterSpacing = 0.sp
    )
)
