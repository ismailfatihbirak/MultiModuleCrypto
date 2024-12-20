package com.example.multimodulecrypto.core.data

import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreDataSource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import com.example.multimodulecrypto.core.model.Root
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FirestoreRepositoryTest {

    private val mockDataSource: FirestoreDataSource = mockk()
    private lateinit var firestoreRepository: FirestoreRepository

    @Before
    fun setUp() {
        firestoreRepository = FirestoreRepository(mockDataSource)
    }

    @Test
    fun `saveFav calls data source with correct parameters`() = runBlocking {
        val id = "bitcoin"
        val symbol = "btc"
        val name = "Bitcoin"
        val image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400"
        val currentPrice = "102228"
        val priceChangePercentage = 2.5

        coEvery {
            mockDataSource.saveFav(
                id,
                symbol,
                name,
                image,
                currentPrice,
                priceChangePercentage
            )
        } just Runs

        firestoreRepository.saveFav(id, symbol, name, image, currentPrice, priceChangePercentage)

        coVerify {
            mockDataSource.saveFav(
                id,
                symbol,
                name,
                image,
                currentPrice,
                priceChangePercentage
            )
        }
    }

    @Test
    fun `getFavList returns list from data source`() = runBlocking {
        val mockFavList = listOf(
            Root(
                id = "bitcoin",
                symbol = "btc",
                name = "Bitcoin",
                image = "https://coin-images.coingecko.com/coins/images/1/large/bitcoin.png?1696501400",
                currentPrice = 50000.0,
                priceChangePercentage24h = 5.0,
            )
        )
        coEvery { mockDataSource.getFavList() } returns mockFavList

        val result = firestoreRepository.getFavList()

        assertEquals(mockFavList, result)
        coVerify { mockDataSource.getFavList() }
    }

    @Test
    fun `deleteFav returns true when deletion is successful`() = runBlocking {
        val symbol = "btc"
        coEvery { mockDataSource.deleteFav(symbol) } returns true
        val result = firestoreRepository.deleteFav(symbol)

        assertEquals(true, result)
        coVerify { mockDataSource.deleteFav(symbol) }
    }

    @Test
    fun `deleteFav returns false when deletion fails`() = runBlocking {
        val symbol = "btc"
        coEvery { mockDataSource.deleteFav(symbol) } returns false

        val result = firestoreRepository.deleteFav(symbol)

        assertEquals(false, result)
        coVerify { mockDataSource.deleteFav(symbol) }
    }
}
