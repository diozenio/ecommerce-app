package com.example.ecommerceapp.data.product

import androidx.room.TypeConverter
import com.example.ecommerceapp.model.Category
import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.ProductSize
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromSizes(sizes: List<ProductSize>): String {
        return gson.toJson(sizes)
    }

    @TypeConverter
    fun toSizes(json: String): List<ProductSize> {
        val type = object : TypeToken<List<ProductSize>>() {}.type
        return gson.fromJson(json, type) ?: emptyList()
    }

    @TypeConverter
    fun fromCategory(category: Category): String {
        return gson.toJson(category)
    }

    @TypeConverter
    fun toCategory(json: String): Category {
        val type = object : TypeToken<Category>() {}.type
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun fromProduct(product: Product): String {
        return gson.toJson(product)
    }

    @TypeConverter
    fun toProduct(json: String): Product {
        val type = object : TypeToken<Product>() {}.type
        return gson.fromJson(json, type)
    }
}