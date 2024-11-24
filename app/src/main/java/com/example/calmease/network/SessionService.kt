package com.example.calmease.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


suspend fun fetchToken(sessionId: String): String {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://your.api.endpoint/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java)
    return apiService.getToken(sessionId).token
}

interface ApiService {
    @GET("api/getToken")
    suspend fun getToken(@Query("sessionId") sessionId: String): TokenResponse
}

data class TokenResponse(val token: String)
