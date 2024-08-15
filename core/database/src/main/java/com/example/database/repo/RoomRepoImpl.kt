package com.example.database.repo

import com.example.database.dao.CryptoDao
import com.example.database.model.RootEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepoImpl @Inject constructor(private val cryptoDao: CryptoDao) : RoomRepository {
    override fun getAllCrypto(): Flow<List<RootEntity>> = cryptoDao.getAllCrypto()
    override suspend fun insertCrypto(insertCrypto: RootEntity) =
        cryptoDao.insertCrypto(insertCrypto)

    override suspend fun updateCrypto(updateCrypto: RootEntity) =
        cryptoDao.updateCrypto(updateCrypto)
}