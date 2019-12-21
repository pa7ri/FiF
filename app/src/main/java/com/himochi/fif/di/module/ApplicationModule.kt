package com.himochi.fif.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.android.DaggerApplication

@Module
class ApplicationModule {

    @Provides
    fun provideApplication(application: DaggerApplication): Application = application

    @Provides
    fun provideApplicationContext(application: DaggerApplication): Context = application.applicationContext
}