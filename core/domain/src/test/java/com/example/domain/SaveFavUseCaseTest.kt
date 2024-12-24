package com.example.domain

import android.util.Log
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SaveFavUseCaseTest {

    private lateinit var useCase: SaveFavUseCase
    private val mockRepository: FirestoreRepository = mockk()

    @Before
    fun setUp() {
        useCase = SaveFavUseCase(mockRepository)
    }

    @Test
    fun `invoke should call saveFav with correct parameters`() = runTest {
        val id = "bitcoin"
        val symbol = "btc"
        val name = "Bitcoin"
        val image = "https://image.url"
        val currentPrice = "50000.0"
        val priceChangePercentage = -2.0

        coEvery { mockRepository.saveFav(any(), any(), any(), any(), any(), any()) } just Runs

        useCase.invoke(id, symbol, name, image, currentPrice, priceChangePercentage)

        coVerify(exactly = 1) {
            mockRepository.saveFav(
                id = id,
                symbol = symbol,
                name = name,
                image = image,
                currentPrice = currentPrice,
                priceChangePercentage = priceChangePercentage
            )
        }
    }

    @Test
    fun `invoke should log error when repository throws exception`() = runTest {
        val id = "bitcoin"
        val symbol = "btc"
        val name = "Bitcoin"
        val image = "https://image.url"
        val currentPrice = "50000.0"
        val priceChangePercentage = -2.0
        val exceptionMessage = "Failed to save favorite"

        coEvery { mockRepository.saveFav(any(), any(), any(), any(), any(), any()) } throws Exception(exceptionMessage)

        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0

        useCase.invoke(id, symbol, name, image, currentPrice, priceChangePercentage)

        coVerify(exactly = 1) { Log.e(exceptionMessage, exceptionMessage) }
        coVerify(exactly = 1) {
            mockRepository.saveFav(
                id = id,
                symbol = symbol,
                name = name,
                image = image,
                currentPrice = currentPrice,
                priceChangePercentage = priceChangePercentage
            )
        }

        unmockkStatic(Log::class)
    }
}
