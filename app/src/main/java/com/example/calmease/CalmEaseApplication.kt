package com.example.calmease


import android.app.Application
import com.example.calmease.utils.SharedPreferenceHelper

class CalmEaseApplication : Application() {
    companion object {
        lateinit var sharedPreferenceHelper: SharedPreferenceHelper
    }
    override fun onCreate() {
        super.onCreate()
        sharedPreferenceHelper = SharedPreferenceHelper.getInstance(applicationContext)
    }
}
