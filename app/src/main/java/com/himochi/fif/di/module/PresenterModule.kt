package com.himochi.fif.di.module

import com.himochi.fif.presentation.SenderPresenter
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
internal abstract class PresenterModule {

    @ContributesAndroidInjector
    internal abstract fun senderPresenter(): SenderPresenter
}