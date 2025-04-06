package com.divya.imperativeassignment

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("transactions")
    suspend fun getTransactions(): List<Transaction>
}