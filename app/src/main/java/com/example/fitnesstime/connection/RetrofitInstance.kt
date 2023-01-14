package com.example.fitnesstime.connection

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit

object RetrofitInstance {
    private const val BASE_URL = "https://fitnesstimedude.000webhostapp.com/androidApi/"
    val retrofit: ApiInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

    }
}