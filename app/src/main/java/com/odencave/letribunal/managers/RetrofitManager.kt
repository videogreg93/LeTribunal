package com.odencave.letribunal.managers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("$BASE_URL/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val BASE_URL = "https://latribune.ca"
}