package com.app.diploma.domain

import com.app.diploma.data.dto.Currency

interface CryptoRepository {

    suspend fun getAllCurrencies(): Result<List<Currency>>

    suspend fun getRateByCurrency(currency: Currency): Result<Double>

}
