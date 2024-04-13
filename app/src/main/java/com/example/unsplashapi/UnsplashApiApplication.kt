package com.example.unsplashapi

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class UnsplashApiApplication:Application() {
    override fun onCreate() {
        super.onCreate()

    }
}