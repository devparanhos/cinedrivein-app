package br.com.renovatiu.cinedrivein.di.application

import android.app.Application
import br.com.renovatiu.cinedrivein.di.modules.database.databaseModule
import br.com.renovatiu.cinedrivein.di.modules.network.networkModule
import br.com.renovatiu.cinedrivein.di.modules.repository.repositoryModule
import br.com.renovatiu.cinedrivein.di.modules.usecase.usecaseModule
import br.com.renovatiu.cinedrivein.di.modules.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    networkModule,
                    viewModelModule,
                    databaseModule,
                    usecaseModule,
                    repositoryModule
                )
            )
        }
    }
}