package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.model.SparkLineIn7dEntity
import com.example.database.repo.RoomRepository
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
class UpdateCryptoRoomUseCaseTest {

    private lateinit var useCase: UpdateCryptoRoomUseCase
    private val mockRepository: RoomRepository = mockk()

    @Before
    fun setUp() {
        useCase = UpdateCryptoRoomUseCase(mockRepository)
    }

    @Test
    fun `invoke should call updateCrypto with the correct entity`() = runTest {
        val cryptoEntity = RootEntity(
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

        coEvery { mockRepository.updateCrypto(any()) } just Runs

        useCase.invoke(cryptoEntity)

        coVerify(exactly = 1) { mockRepository.updateCrypto(cryptoEntity) }
    }
}
