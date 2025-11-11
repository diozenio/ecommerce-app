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

data class Category(
    val id: String,
    val title: String,
)

data class Product(
    val id: String,
    val title: String,
    val size: UIProductCardCartSize,
    val price: Float,
    val imageUrl: String,
    val discount: Float = 0F,
    val category: Category,
)
