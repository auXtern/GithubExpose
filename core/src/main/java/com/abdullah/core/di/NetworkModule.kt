package com.abdullah.core.di

import com.abdullah.core.BuildConfig
import com.abdullah.core.data.source.remote.network.ApiConfig
import com.abdullah.core.data.source.remote.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return ApiConfig.provideOkHttpClient()
    }

    @Provides
    fun provideApiService(client: OkHttpClient): ApiService {
        return ApiConfig.provideApiService(client)
    }
}