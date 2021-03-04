package com.kaval.android.myapplication

import android.app.Application
import com.kaval.android.myapplication.dependencies.ViewModelDependency
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@MainApplication)
            modules(provideDependency())
        }
    }


    //List of Koin dependencies
    open fun provideDependency() = listOf(
        ViewModelDependency
    )


}