package com.example.ecommerceapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart")
data class CartItem(
    @PrimaryKey(autoGenerate = true)
    override val id: Int = 0,
    val quantity: Int,
    val product: Product,
) : Model
