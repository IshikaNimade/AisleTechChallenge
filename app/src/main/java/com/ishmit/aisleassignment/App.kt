package com.ishmit.aisleassignment

import android.app.Application
import com.ishmit.aisleassignment.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize Koin dependency injection framework
        initializeKoin()
    }

    private fun initializeKoin() {
        // Start Koin with Android context and the defined appModule
        startKoin {
            androidContext(this@App) // Provide the application context
            modules(appModule) // Use the defined module for dependency injection
        }
    }
}
