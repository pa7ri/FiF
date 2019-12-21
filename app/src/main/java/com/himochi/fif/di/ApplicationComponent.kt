package com.himochi.fif.di

import com.himochi.fif.di.module.ActivityModule
import com.himochi.fif.di.module.ApplicationModule
import com.himochi.fif.di.module.PresenterModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    PresenterModule::class,
    ActivityModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>()

}