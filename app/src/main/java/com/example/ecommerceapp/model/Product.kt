package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class ProductSize {
    SMALL, MEDIUM, LARGE
}

fun ProductSize.toSizeString(): String {
    return when (this) {
        ProductSize.LARGE -> "Size L"
        ProductSize.MEDIUM -> "Size M"
        ProductSize.SMALL -> "Size S"
    }
}

@Entity(tableName = "category")
data class Category(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
)

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val sizes: List<ProductSize> = emptyList(),
    val price: Float,
    val imageUrl: String,
    val discount: Float = 0F,
    val category: Category,
)