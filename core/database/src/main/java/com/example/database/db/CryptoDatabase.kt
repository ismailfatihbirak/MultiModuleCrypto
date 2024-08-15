package com.example.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.dao.CryptoDao
import com.example.database.model.RootEntity

@Database(entities = [RootEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CryptoDatabase : RoomDatabase() {
    abstract fun getRoomDao(): CryptoDao
}
