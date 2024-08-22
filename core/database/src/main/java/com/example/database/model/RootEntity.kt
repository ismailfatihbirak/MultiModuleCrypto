package com.example.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.database.di.tables.cryptoTable

@Entity(tableName = cryptoTable)
data class RootEntity(
    @PrimaryKey
    var id: String,
    var symbol: String,
    var name: String,
    var image: String,
    var currentPrice: Double,
    var high24h: Double,
    var low24h: Double,
    var priceChange24h: Double,
    var priceChangePercentage24h: Double,
    var lastUpdated: String,
    @Embedded
    var sparklineIn7d: SparkLineIn7dEntity? = SparkLineIn7dEntity()
)
