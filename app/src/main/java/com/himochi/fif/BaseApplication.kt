package com.himochi.fif

import android.app.Application
import com.himochi.fif.domain.GetEncryptedUseCase
import com.himochi.fif.presentation.SenderPresenter
import com.himochi.fif.presentation.SenderView
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(senderModule)
        }
    }

    private val senderModule = module {
        factory { GetEncryptedUseCase() }
        factory { (view: SenderView) ->
            SenderPresenter(view, get())
        }
    }
}