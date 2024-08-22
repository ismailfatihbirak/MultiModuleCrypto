package com.example.offlinecache.di

import com.example.offlinecache.repository.CacheWorkerRepository
import com.example.offlinecache.repository.CacheWorkerRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {
    @Provides
    @Singleton
    fun provideCacheWorkerRepository(): CacheWorkerRepository {
        return CacheWorkerRepositoryImpl()
    }
}