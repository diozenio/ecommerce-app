package com.example.ecommerceapp.data.saved

import com.example.ecommerceapp.model.Product
import com.example.ecommerceapp.model.SavedItem
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SavedApi {
    @GET("saved")
    suspend fun getSavedItems(): List<SavedItem>

    @POST("saved")
    suspend fun saveItem(@Body product: Product): SavedItem

    @DELETE("saved/{id}")
    suspend fun unsaveItem(@Path("id") productId: String)
}