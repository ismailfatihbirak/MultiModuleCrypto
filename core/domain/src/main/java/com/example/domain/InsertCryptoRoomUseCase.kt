package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.model.SparkLineIn7dEntity
import com.example.database.repo.RoomRepository
import com.example.multimodulecrypto.core.model.Root
import javax.inject.Inject

class InsertCryptoRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(crypto: Root) {
        val cryptoEntity = RootEntity(
            id = crypto.id.orEmpty(),
            symbol = crypto.symbol.orEmpty(),
            name = crypto.name.orEmpty(),
            image = crypto.image.orEmpty(),
            currentPrice = crypto.currentPrice ?: 0.0,
            high24h = crypto.high24h ?: 0.0,
            low24h = crypto.low24h ?: 0.0,
            priceChange24h = crypto.priceChange24h ?: 0.0,
            priceChangePercentage24h = crypto.priceChangePercentage24h ?: 0.0,
            lastUpdated = crypto.lastUpdated.orEmpty(),
            sparklineIn7d = crypto.sparklineIn7d?.let { sparkline ->
                SparkLineIn7dEntity(
                    price = ArrayList(sparkline.price)
                )
            }
        )
        roomRepository.insertCrypto(cryptoEntity)
    }
}


