package com.example.domain

import com.example.multimodulecrypto.core.common.Resource
import com.example.multimodulecrypto.core.data.firestore_repository.FirestoreRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test


class DeleteFavUseCaseTest {

    private val firestoreRepository: FirestoreRepository = mockk()
    private lateinit var deleteFavUseCase: DeleteFavUseCase

    @Before
    fun setUp() {
        deleteFavUseCase = DeleteFavUseCase(firestoreRepository)
    }

    @Test
    fun `invoke emits loading and success when repository deletes favorite successfully`() = runTest {
        val symbol = "btc"
        coEvery { firestoreRepository.deleteFav(symbol) } returns true

        val result = deleteFavUseCase(symbol).toList()
        assert(result[0] is Resource.Loading<*>)
        assert(result[1] is Resource.Success && (result[1] as Resource.Success<Boolean>).data == true)
    }

    @Test
    fun `invoke emits loading and error when repository throws an exception`() = runTest {
        val symbol = "btc"
        val errorMessage = "Error occurred"
        coEvery { firestoreRepository.deleteFav(symbol) } throws Exception(errorMessage)

        val result = deleteFavUseCase(symbol).toList()

        assert(result[0] is Resource.Loading<*>)
        assert(result[1] is Resource.Error && result[1].message == errorMessage)
    }
}