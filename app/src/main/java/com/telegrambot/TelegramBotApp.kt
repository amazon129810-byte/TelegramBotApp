package com.telegrambot

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TelegramBotApp : Application() {

    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber for logging
        if (BuildConfig.DEBUG_LOGS) {
            Timber.plant(Timber.DebugTree())
        }
        
        Timber.d("TelegramBotApp initialized")
    }
}
