package com.example.ecommerceapp.util

import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * Calculates the percentage of a value in sp.
 *
 * Example: 20.sp.percent(120) = 24.sp
 */
fun TextUnit.percent(percent: Int): TextUnit {
    return (this.value * (percent / 100f)).sp
}