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

class SessionViewModel : ViewModel() {

    private var _onSessionCreated: ((SessionRequest) -> Unit)? = null
    private var _onSessionUpdated: ((Session) -> Unit)? = null

    fun setOnSessionCreatedCallback(callback: (SessionRequest) -> Unit) {
        _onSessionCreated = callback
    }

    fun setOnSessionUpdatedCallback(callback: (Session) -> Unit) {
        _onSessionUpdated = callback
    }
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://calmease-backend.onrender.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val sessionService = retrofit.create(SessionService::class.java)

    private val _sessions = MutableStateFlow<List<Session>>(emptyList())
    val sessions: StateFlow<List<Session>> = _sessions

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

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

    fun createSession(sessionRequest: SessionRequest) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = sessionService.createSession(sessionRequest)
                if (response.isSuccessful) {
                    Log.d("SessionViewModel", "Create session response: ${response.body()}")
                    response.body()?.let {
                        _onSessionCreated?.invoke(sessionRequest) // Notify success with the created session
                    }
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

    fun updateSession(session: Session) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                val response = sessionService.editSession(session)
                if (response.isSuccessful) {
                    Log.d("SessionViewModel", "Update session response: ${response.body()}")
                    response.body()?.let {
                        _onSessionUpdated?.invoke(session) // Notify success with the created session
                    } } else {
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
