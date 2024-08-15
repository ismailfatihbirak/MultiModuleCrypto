package com.example.domain

import com.example.database.model.RootEntity
import com.example.database.repo.RoomRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCryptoRoomUseCase @Inject constructor(
    private val roomRepository: RoomRepository
) {
    operator fun invoke(): Flow<List<RootEntity>> {
        return roomRepository.getAllCrypto()
    }
}
