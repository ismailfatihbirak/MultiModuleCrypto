package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.model.SparkLineIn7dEntity
import com.example.database.repo.RoomRepository
import com.example.multimodulecrypto.core.model.Root
import com.example.multimodulecrypto.core.model.SparklineIn7d
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class InsertCryptoRoomUseCaseTest {

    private lateinit var useCase: InsertCryptoRoomUseCase
    private val mockRepository: RoomRepository = mockk()

    @Before
    fun setUp() {
        useCase = InsertCryptoRoomUseCase(mockRepository)
    }

    @Test
    fun `invoke should call insertCrypto with correct entity`() = runTest {
        val mockCrypto = Root(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://image.url",
            currentPrice = 50000.0,
            high24h = 51000.0,
            low24h = 49000.0,
            priceChange24h = -1000.0,
            priceChangePercentage24h = -2.0,
            lastUpdated = "2024-12-24T12:00:00Z",
            sparklineIn7d = SparklineIn7d(arrayListOf(50000.0, 50500.0, 49000.0))
        )

        val expectedEntity = RootEntity(
            id = "bitcoin",
            symbol = "btc",
            name = "Bitcoin",
            image = "https://image.url",
            currentPrice = 50000.0,
            high24h = 51000.0,
            low24h = 49000.0,
            priceChange24h = -1000.0,
            priceChangePercentage24h = -2.0,
            lastUpdated = "2024-12-24T12:00:00Z",
            sparklineIn7d = SparkLineIn7dEntity(price = arrayListOf(50000.0, 50500.0, 49000.0))
        )

        coEvery { mockRepository.insertCrypto(any()) } just Runs

        useCase.invoke(mockCrypto)

        coVerify(exactly = 1) { mockRepository.insertCrypto(expectedEntity) }
    }
}
