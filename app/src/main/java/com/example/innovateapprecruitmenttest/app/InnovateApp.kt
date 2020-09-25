package com.example.innovateapprecruitmenttest.app

import android.app.Application
import com.example.innovateapprecruitmenttest.di.appModule
import com.example.innovateapprecruitmenttest.di.networkModule
import com.example.innovateapprecruitmenttest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class InnovateApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@InnovateApp)
            modules(listOf(networkModule(), appModule(), viewModelModule()))
        }
    }
}