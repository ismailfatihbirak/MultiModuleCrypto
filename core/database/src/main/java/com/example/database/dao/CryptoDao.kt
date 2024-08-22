package com.example.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.database.model.RootEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CryptoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCrypto(insertCrypto: RootEntity)
    @Update
    suspend fun updateCrypto(updateCrypto: RootEntity)
    @Query("SELECT * from crypto_table")
    fun getAllCrypto(): Flow<List<RootEntity>>
}