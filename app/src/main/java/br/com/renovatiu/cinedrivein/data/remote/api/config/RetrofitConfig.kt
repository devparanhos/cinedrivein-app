package br.com.renovatiu.cinedrivein.data.remote.api.config

import br.com.renovatiu.cinedrivein.data.remote.api.service.AncineAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitConfig {
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient
            .Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "jltv1cdnoitt3ud9jnp2p16br90e8i")
                    .addHeader("Content-Type", "application/json")
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://scb.ancine.gov.br/scb/v1.0/")
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    fun provideAncineService(retrofit: Retrofit): AncineAPI = retrofit.create(AncineAPI::class.java)
}