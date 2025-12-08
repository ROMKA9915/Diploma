package com.app.diploma.data.network

import android.util.Log
import com.app.diploma.data.dto.Currency
import com.app.diploma.domain.CryptoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoRepositoryImpl @Inject constructor(
    private val cryptoService: CryptoService,
) : CryptoRepository {

    override suspend fun getAllCurrencies() = runCatching {
        cryptoService.getCurrencies().data
    }.onFailure {
        Log.d("TAG228", "$it")
    }

    override suspend fun getRateByCurrency(currency: Currency) = runCatching {
        cryptoService.getPrice(currency.symbol).data.firstOrNull()!!
    }
}
