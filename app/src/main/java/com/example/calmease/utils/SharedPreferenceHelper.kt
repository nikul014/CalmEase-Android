package com.example.calmease.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.calmease.enums.SharedPrefKeys
import com.example.calmease.network.User

class SharedPreferenceHelper private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("CalmEase", Context.MODE_PRIVATE)

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        @Volatile
        private var INSTANCE: SharedPreferenceHelper? = null

        fun getInstance(context: Context): SharedPreferenceHelper {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: SharedPreferenceHelper(context.applicationContext).also {
                    INSTANCE = it
                }
            }
        }
    }

    fun <T> save(key: SharedPrefKeys, value: T) {
        when (value) {
            is String -> editor.putString(key.key, value)
            is Int -> editor.putInt(key.key, value)
            is Boolean -> editor.putBoolean(key.key, value)
            is Float -> editor.putFloat(key.key, value)
            is Long -> editor.putLong(key.key, value)
            else -> throw IllegalArgumentException("Unsupported data type")
        }
        editor.apply()
    }

    fun <T> get(key: SharedPrefKeys, defaultValue: T): T {
        return when (defaultValue) {
            is String -> sharedPreferences.getString(key.key, defaultValue) as T
            is Int -> sharedPreferences.getInt(key.key, defaultValue) as T
            is Boolean -> sharedPreferences.getBoolean(key.key, defaultValue) as T
            is Float -> sharedPreferences.getFloat(key.key, defaultValue) as T
            is Long -> sharedPreferences.getLong(key.key, defaultValue) as T
            else -> throw IllegalArgumentException("Unsupported data type")
        }
    }

    fun clearValue(key: SharedPrefKeys) {
        editor.remove(key.key).apply()
    }

    fun clearAll() {
        editor.clear().apply()
    }



    fun saveUserData(user: User, token: String) {
        try {
            save(SharedPrefKeys.IS_LOGGED_IN, true)

            save(SharedPrefKeys.USER_ID, user.id)
            save(SharedPrefKeys.USER_EMAIL, user.email)
            save(SharedPrefKeys.USER_TYPE, user.userType)
            save(SharedPrefKeys.CREATED_AT, user.createdAt)
            save(SharedPrefKeys.UPDATED_AT, user.updatedAt)
            save(SharedPrefKeys.TOKEN, token)
        } catch (ex: Exception) {
            ex.printStackTrace()
            Log.e("TAGSA","Error occured saving user info:"+ex.stackTraceToString())}
    }


    fun getUser(): User? {
        val id = get(SharedPrefKeys.USER_ID, -1)
        val email = get(SharedPrefKeys.USER_EMAIL, "")
        val userType = get(SharedPrefKeys.USER_TYPE, "")
        val createdAt = get(SharedPrefKeys.CREATED_AT, "")
        val updatedAt = get(SharedPrefKeys.UPDATED_AT, "")

        return if (id != -1 && email.isNotEmpty()) {
            User(id, email, userType, createdAt, updatedAt)
        } else {
            null
        }
    }

    fun getToken(): String? {
        return get(SharedPrefKeys.TOKEN, "")
    }

    fun isLoggedIn(): Boolean {
        return get(SharedPrefKeys.IS_LOGGED_IN, false)
    }



    fun clearUserData() {
        clearValue(SharedPrefKeys.USER_ID)
        clearValue(SharedPrefKeys.USER_EMAIL)
        clearValue(SharedPrefKeys.USER_TYPE)
        clearValue(SharedPrefKeys.CREATED_AT)
        clearValue(SharedPrefKeys.UPDATED_AT)
        clearValue(SharedPrefKeys.TOKEN)
        clearValue(SharedPrefKeys.IS_LOGGED_IN)
    }

}
