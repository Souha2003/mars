package com.example.marsphotos

import android.app.Application
import com.example.marsphotos.data.AppContainer
import com.example.marsphotos.data.DefaultAppContainer

class MarsPhotosApplication : Application() {
    // Initialisez le conteneur
    val container: AppContainer by lazy { DefaultAppContainer() }
}