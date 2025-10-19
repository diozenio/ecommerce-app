package com.example.ecommerceapp.model

enum class UIProductCardCartSize {
    SMALL,
    MEDIUM,
    LARGE
}

fun UIProductCardCartSize.toSizeString(): String {
    return when (this) {
        UIProductCardCartSize.LARGE -> "Size L"
        UIProductCardCartSize.MEDIUM -> "Size M"
        UIProductCardCartSize.SMALL -> "Size S"
    }
}

data class Product(
    val id: Int,
    val title: String,
    val size: UIProductCardCartSize,
    val price: Float,
    val imageUrl: String,
    val quantity: Int,
    val isSaved: Boolean = false
)
