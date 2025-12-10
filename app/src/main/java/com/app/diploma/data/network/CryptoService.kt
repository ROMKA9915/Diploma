package com.app.diploma.data.network

import com.app.diploma.data.dto.Currency
import kotlinx.serialization.Serializable
import retrofit2.http.GET

interface CryptoService {

    @GET("v3/rates")
    suspend fun getCurrencies(): ApiResponse<Currency>

}

@Serializable
data class ApiResponse<PAYLOAD>(
    val data: List<PAYLOAD>,
)
