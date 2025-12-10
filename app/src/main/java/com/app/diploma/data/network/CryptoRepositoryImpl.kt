package com.app.diploma.data.network

import com.app.diploma.domain.CryptoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepositoryImpl @Inject constructor(
    private val cryptoService: CryptoService,
) : CryptoRepository {

    override suspend fun getAllCurrencies() = runCatching {
        cryptoService.getCurrencies().data
    }
}
