package com.example.multimodulecrypto.core.data.di

import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationDataSource
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationDataSourceImpl
import com.example.multimodulecrypto.core.data.auth_repository.AuthenticationRepository
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreDataSource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreDataSourceImpl
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @IODispatcher
    @Provides
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class IODispatcher

    @Provides
    @Singleton
    fun provideAuthenticationDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): AuthenticationDataSource {
        return AuthenticationDataSourceImpl(ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideAuthenticationRepository(authenticationDataSource: AuthenticationDataSource): AuthenticationRepository {
        return AuthenticationRepository(authenticationDataSource = authenticationDataSource)
    }

    @Provides
    @Singleton
    fun provideFirestoreDataSource(
        @IODispatcher ioDispatcher: CoroutineDispatcher
    ): FirestoreDataSource {
        return FirestoreDataSourceImpl(ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(firestoreDataSource: FirestoreDataSource): FirestoreRepository {
        return FirestoreRepository(firestoreDataSource = firestoreDataSource)
    }
}