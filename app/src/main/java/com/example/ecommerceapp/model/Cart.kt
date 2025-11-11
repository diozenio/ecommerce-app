package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class CartTaxes(
    val vat: Float,
    val shippingFee: Float
)

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val quantity: Int,
    val size: ProductSize,
    val product: Product,
) : Model
