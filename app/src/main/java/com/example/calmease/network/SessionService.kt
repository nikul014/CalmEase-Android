package com.example.calmease.network

import android.util.Log
import com.example.calmease.viewmodel.GoodMemory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


suspend fun fetchToken(sessionId: String): String {



    // Create an HttpLoggingInterceptor
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // This will log the full body of the request/response
    }

    // Create an OkHttpClient and add the logging interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit setup
    val retrofit = Retrofit.Builder()
        .baseUrl("https://calmease-backend.onrender.com/") // Update with the actual base URL
        .client(client) // Add the OkHttpClient with the interceptor to Retrofit
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

interface MemoryApiService {
    @POST("api/goodMemories/create")
    suspend fun createMemory(@Body memoryRequest: CreateMemoryRequest): Response<Unit>

    @GET("api/goodMemories/list")
    suspend fun getMemories(
        @Query("searchTerm") searchTerm: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): MemoryResponse
}



data class MemoryResponse(
    val data: List<GoodMemory>,
    val pagination: Pagination
)

data class Pagination(
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)


data class CreateMemoryRequest(
    val title: String,
    val description: String,
    val memory_date_time: String,  // Assuming this is a string for now, but it might be better as a DateTime if appropriate
    val image_url: String,
    val user_id: Int
)


data class Result(
    val isSuccess: Boolean,
    val errorMessage: String? = null
)


suspend fun createMemory(memoryRequest: CreateMemoryRequest): Result {



    // Create an HttpLoggingInterceptor
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // This will log the full body of the request/response
    }

    // Create an OkHttpClient and add the logging interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit setup
    val retrofit = Retrofit.Builder()
        .baseUrl("https://calmease-backend.onrender.com/") // Update with the actual base URL
        .client(client) // Add the OkHttpClient with the interceptor to Retrofit
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val apiService = retrofit.create(MemoryApiService::class.java)

    return try {
        val response = apiService.createMemory(memoryRequest)
        if (response.isSuccessful) {
            Result(isSuccess = true)
        } else {
            Log.e("Tagsa","Memories:"+response.message()+":"+response.body().toString()+":"+response.errorBody()?.string())
            Result(isSuccess = false, errorMessage = "Failed to create memory: ${response.message()}")
        }
    } catch (e: Exception) {
        Result(isSuccess = false, errorMessage = e.message ?: "An unknown error occurred")
    }
}



interface SessionService {

    @POST("api/sessions/create")
    suspend fun createSession(@Body sessionRequest: SessionRequest): Response<Unit>

    @POST("api/sessions/edit")
    suspend fun editSession(@Body sessionEditRequest: Session): Response<Unit>

    @GET("api/sessions/list")
    suspend fun getSessions(
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("searchTerm") searchTerm: String
    ): Response<SessionListResponse>
}


data class SessionRequest(
    val title: String,
    val description: String,
    val session_date: String,
    val session_time: String,
    val expert_id: String,
    val expert_email: String,
    val duration: Int
)

data class SessionListResponse(
    val data: List<Session>,
    val pagination: Pagination
)
data class Session(
    val session_id: Int? = null,
    val title: String,
    val description: String,
    val session_date: String,
    val session_time: String,
    val duration: Int,
    val expert_id: String,
    val expert_email: String,
    val actual_start_time: String? = null,
    val status: String? = null,
    val agora_channel_id: String? = null
)
