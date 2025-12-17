package com.app.diploma.domain

import com.app.diploma.data.dto.Currency
import com.app.diploma.data.dto.Profile

interface CryptoRepository {

    suspend fun getAllCurrencies(): Result<List<Currency>>

    suspend fun getProfileData(): Result<Profile>

}
