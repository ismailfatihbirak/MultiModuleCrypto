package com.example.network.di

import android.content.Context
import com.example.network.repository.CryptoDataSource
import com.example.network.repository.CryptoDataSourceImpl
import com.example.network.repository.CryptoRepo
import com.example.network.retrofit.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

private const val BASE_URL = "https://api.coingecko.com/api/v3/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideCryptoApi(@ApplicationContext context: Context): CryptoApi {
        val cacheSize: Long = 10 * 1024 * 1024
        val cache = Cache(File(context.cacheDir, "http_cache"), cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(com.example.network.interceptor.CacheInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCryptoDataSource(api: CryptoApi): CryptoDataSource {
        return CryptoDataSourceImpl(api = api)
    }

    @Provides
    @Singleton
    fun provideCryptoRepo(cryptoDataSource: CryptoDataSource): CryptoRepo {
        return CryptoRepo(cryptoDataSource)
    }
}

