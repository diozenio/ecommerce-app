package com.example.ecommerceapp.data.maps

import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApi {
    @GET("directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String,
        @Query("mode") mode: String = "driving",
        @Query("language") language: String = "pt-BR"
    ): DirectionsResponse
}

