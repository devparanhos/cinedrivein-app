package br.com.renovatiu.cinedrivein.di.modules.network

import br.com.renovatiu.cinedrivein.data.remote.api.config.RetrofitConfig
import org.koin.dsl.module

val networkModule = module {
    single {
        RetrofitConfig.provideHttpClient()
    }

    single {
        RetrofitConfig.provideConverterFactory()
    }

    single {
        RetrofitConfig.provideRetrofit(
            okHttpClient = get(),
            gsonConverterFactory = get()
        )
    }

    single {
        RetrofitConfig.provideAncineService(
            retrofit =  get()
        )
    }
}