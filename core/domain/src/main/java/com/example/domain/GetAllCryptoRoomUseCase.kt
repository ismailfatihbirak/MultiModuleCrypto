package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.model.SparkLineIn7dEntity
import com.example.database.repo.RoomRepository
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.SparklineIn7d
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllCryptoRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke(): Flow<List<Root>> {
        return roomRepository.getAllCrypto().map { entityList ->
            entityList.map { entity ->
                Root(
                    id = entity.id,
                    symbol = entity.symbol,
                    name = entity.name,
                    image = entity.image,
                    currentPrice = entity.currentPrice,
                    high24h = entity.high24h,
                    low24h = entity.low24h,
                    priceChange24h = entity.priceChange24h,
                    priceChangePercentage24h = entity.priceChangePercentage24h,
                    lastUpdated = entity.lastUpdated,
                    sparklineIn7d = entity.sparklineIn7d?.let { sparklineEntity ->
                        SparklineIn7d(
                            price = sparklineEntity.price
                        )
                    }
                )
            }
        }
    }
}

