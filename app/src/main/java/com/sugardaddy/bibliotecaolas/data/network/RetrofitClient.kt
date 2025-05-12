package com.sugardaddy.bibliotecaolas.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://68212042259dad2655ae8674.mockapi.io/api/"

    val libroApi: LibroApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LibroApiService::class.java)
    }
}