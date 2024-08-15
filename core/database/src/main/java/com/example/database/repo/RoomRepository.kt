package com.example.database.repo

import com.example.database.model.RootEntity
import kotlinx.coroutines.flow.Flow

interface RoomRepository {
    fun getAllCrypto(): Flow<List<RootEntity>>
    suspend fun insertCrypto(insertCrypto: RootEntity)
    suspend fun updateCrypto(updateCrypto: RootEntity)
}