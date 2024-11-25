package com.example.calmease.viewmodel
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmease.network.Session
import com.example.calmease.network.SessionRequest
import com.example.calmease.network.SessionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//
//class SessionViewModel(private val sessionRepository: SessionRepository) : ViewModel() {
//
//    val sessions = mutableStateOf<List<Session>>(emptyList())
//    val createSessionResponse = mutableStateOf<Response<Unit>?>(null)
//
//    // To handle errors and show loading
//    val isLoading = mutableStateOf(false)
//    val errorMessage = mutableStateOf<String?>(null)
//
//    // Fetch sessions list from API
//    fun fetchSessions(page: Int, pageSize: Int, searchTerm: String) {
//        viewModelScope.launch {
//            isLoading.value = true
//            errorMessage.value = null
//            val response = sessionRepository.getSessions(page, pageSize, searchTerm)
//            if (response.isSuccessful) {
//                sessions.value = response.body()?.data ?: emptyList()
//            } else {
//                errorMessage.value = "Failed to load sessions."
//            }
//            isLoading.value = false
//        }
//    }
//
//
//    // Create a new session
//    fun createSession(sessionRequest: SessionRequest) {
//        viewModelScope.launch {
//            isLoading.value = true
//            errorMessage.value = null
//            val response = sessionRepository.createSession(sessionRequest)
//            createSessionResponse.value = response
//            isLoading.value = false
//        }
//    }
//
//
//    // Update an existing session
//    fun updateSession(session: Session) {
//        isLoading.value = true
//        // Make the API call for updating a session
//        viewModelScope.launch {
//            try {
//                val response = sessionRepository.updateSession(session) // API call
//                if (response.isSuccessful) {
//                    // Handle success, perhaps navigate back or show success
//                } else {
//                    errorMessage.value = "Failed to update session"
//                }
//            } catch (e: Exception) {
//                errorMessage.value = "Error: ${e.localizedMessage}"
//            } finally {
//                isLoading.value = false
//            }
//        }
//    }
//}

class SessionViewModel : ViewModel() {

    // Create an HttpLoggingInterceptor
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // This will log the full body of the request/response
    }

    // Create an OkHttpClient and add the logging interceptor
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Retrofit setup
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://calmease-backend.onrender.com/") // Update with the actual base URL
        .client(client) // Add the OkHttpClient with the interceptor to Retrofit
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val sessionService = retrofit.create(SessionService::class.java)

    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = _sessions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Fetch sessions list from API
    fun fetchSessions(page: Int, pageSize: Int, searchTerm: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = sessionService.getSessions(page, pageSize, searchTerm)
                if (response.isSuccessful) {
                    _sessions.value = response.body()?.data ?: emptyList()
                } else {
                    _errorMessage.value = "Failed to load sessions."
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Create a new session
    fun createSession(sessionRequest: SessionRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = sessionService.createSession(sessionRequest)
                if (response.isSuccessful) {
                    Log.d("SessionViewModel", "Create session response: ${response.body()}")
                } else {
                    Log.e("SessionViewModel", "Failed to create session: ${response.message()}")
                    _errorMessage.value = "Failed to create session"
                }
            } catch (e: Exception) {
                Log.e("SessionViewModel", "Error: ${e.localizedMessage}")
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Update an existing session
    fun updateSession(session: Session) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = sessionService.editSession(session)
                if (response.isSuccessful) {
                    Log.d("SessionViewModel", "Update session response: ${response.body()}")
                } else {
                    Log.e("SessionViewModel", "Failed to update session: ${response.message()}")
                    _errorMessage.value = "Failed to update session"
                }
            } catch (e: Exception) {
                Log.e("SessionViewModel", "Error: ${e.localizedMessage}")
                _errorMessage.value = "Error: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
