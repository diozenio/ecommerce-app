package com.example.ecommerceapp.util

import java.util.Locale

/**
 * Formats a Float value as a currency string with thousands separator and two decimal places.
 *
 * Example: 1100.25f.toCurrencyString() = "$ 1,100.25"
 */
fun Float.toCurrencyString(): String {
    return if (this % 1 == 0f) {
        "$ " + String.format(Locale.US, "%,.0f", this)
    } else {
        "$ " + String.format(Locale.US, "%,.2f", this)
    }
}