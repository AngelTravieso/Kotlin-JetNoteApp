package com.example.jetnoteapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// Esta anotación se coloca en la clase de Application de tu aplicación Android. Sirve como el punto de entrada de Hilt al proyecto y es necesario para que Hilt comience a realizar la inyección de dependencias
@HiltAndroidApp
class MyApp : Application() {
}