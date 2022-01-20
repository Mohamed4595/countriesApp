package com.mhmd.countriesapp.framework.presentation

import android.app.Application
import com.mhmd.countriesapp.di.AppModule
import com.mhmd.countriesapp.di.CacheModule
import com.mhmd.countriesapp.di.InteractorsModule
import com.mhmd.countriesapp.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@BaseApplication)
            // declare modules
            modules(listOf(NetworkModule, CacheModule,InteractorsModule,AppModule))

        }
    }
}
