package com.sugardaddy.bibliotecaolas.data.network

import com.sugardaddy.bibliotecaolas.data.model.Libro
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LibroApiService {
    @GET("libros")
    suspend fun getAll(): List<Libro>

    @GET("libros/{id}")
    suspend fun getById(@Path("id") id: String): Libro

    @POST("libros")
    suspend fun add(@Body libro: Libro): Libro

    @PUT("libros/{id}")
    suspend fun update(@Path("id") id: String, @Body libro: Libro): Libro

    @DELETE("libros/{id}")
    suspend fun delete(@Path("id") id: String): Response<Unit>
}