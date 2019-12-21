package com.himochi.fif.di.module

import com.himochi.fif.presentation.SenderActivity
import com.himochi.fif.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun senderActivity(): SenderActivity
}