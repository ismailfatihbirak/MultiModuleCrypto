package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.model.SparkLineIn7dEntity
import com.example.database.repo.RoomRepository
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.SparklineIn7d
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetAllCryptoRoomUseCaseTest {

    private val roomRepository: RoomRepository = mockk()
    private lateinit var getAllCryptoRoomUseCase: GetAllCryptoRoomUseCase

    @Before
    fun setUp() {
        getAllCryptoRoomUseCase = GetAllCryptoRoomUseCase(roomRepository)
    }

    @Test
    fun `invoke returns transformed list of Root`() = runTest {
        val entityList = listOf(
            RootEntity(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                currentPrice = 50000.0,
                high24h = 51000.0,
                low24h = 49000.0,
                priceChange24h = 1000.0,
                priceChangePercentage24h = 2.0,
                lastUpdated = "2024-12-23",
                sparklineIn7d = SparkLineIn7dEntity(price = arrayListOf(106918.825614381, 106993.981361168, 106657.001698474))
            ),
            RootEntity(
                id = "ethereum",
                symbol = "eth",
                name = "Ethereum",
                image = "eth.png",
                currentPrice = 4000.0,
                high24h = 4200.0,
                low24h = 3900.0,
                priceChange24h = 50.0,
                priceChangePercentage24h = 1.5,
                lastUpdated = "2024-12-23",
                sparklineIn7d = SparkLineIn7dEntity(price = arrayListOf(3800.0, 3900.0, 4000.0))
            )
        )

        coEvery { roomRepository.getAllCrypto() } returns flowOf(entityList)

        val result = getAllCryptoRoomUseCase().toList()

        val expected = listOf(
            listOf(
                Root(
                    id = "bitcoin",
                    symbol = "btc",
                    name = "Bitcoin",
                    image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                    currentPrice = 50000.0,
                    high24h = 51000.0,
                    low24h = 49000.0,
                    priceChange24h = 1000.0,
                    priceChangePercentage24h = 2.0,
                    lastUpdated = "2024-12-23",
                    sparklineIn7d = SparklineIn7d(price =  arrayListOf(106918.825614381, 106993.981361168, 106657.001698474))
                ),
                Root(
                    id = "ethereum",
                    symbol = "eth",
                    name = "Ethereum",
                    image = "eth.png",
                    currentPrice = 4000.0,
                    high24h = 4200.0,
                    low24h = 3900.0,
                    priceChange24h = 50.0,
                    priceChangePercentage24h = 1.5,
                    lastUpdated = "2024-12-23",
                    sparklineIn7d = SparklineIn7d(price = arrayListOf(3800.0, 3900.0, 4000.0))
                )
            )
        )

        assertEquals(expected, result)
    }
}
