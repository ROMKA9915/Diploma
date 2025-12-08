package com.app.diploma.data.network

import com.app.diploma.data.dto.Currency
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoService {

    @GET("v3/rates")
    suspend fun getCurrencies(): ApiResponse<Currency>

    @GET("v3/price/bysymbol/{symbol}")
    suspend fun getPrice(@Path("symbol") symbol: String): ApiResponse<Long>

}

@Serializable
data class ApiResponse<PAYLOAD : Any>(
    val data: List<PAYLOAD>,
)
