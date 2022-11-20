package com.example.f34.app

import android.app.Application
import com.example.f34.di.appModule
import com.example.f34.di.dataModule
import com.example.f34.di.domainModule
import org.koin.core.context.startKoin

class KoinApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(appModule, domainModule, dataModule))
        }
    }

}