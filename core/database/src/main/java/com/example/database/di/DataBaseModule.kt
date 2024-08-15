package com.example.database.di

import android.content.Context
import androidx.room.Room
import com.example.database.dao.CryptoDao
import com.example.database.db.CryptoDatabase
import com.example.database.repo.RoomRepoImpl
import com.example.database.repo.RoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CryptoDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CryptoDatabase::class.java,
            "crypto_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideItemDao(database: CryptoDatabase): CryptoDao {
        return database.getRoomDao()
    }

    @Provides
    @Singleton
    fun provideRoomRepository(cryptoDao: CryptoDao): RoomRepository {
        return RoomRepoImpl(cryptoDao)
    }
}
