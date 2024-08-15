package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.repo.RoomRepository
import javax.inject.Inject

class InsertCryptoRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(crypto: RootEntity) {
        roomRepository.insertCrypto(crypto)
    }
}
