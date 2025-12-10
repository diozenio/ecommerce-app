package com.example.ecommerceapp.data.maps

import com.example.ecommerceapp.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GoogleMapsClient {
    private const val BASE_URL = "https://maps.googleapis.com/maps/api/"
    
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    val directionsApi: DirectionsApi = retrofit.create(DirectionsApi::class.java)
    
    val apiKey: String
        get() = BuildConfig.MAPS_API_KEY
}

