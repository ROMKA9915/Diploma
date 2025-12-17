package com.app.diploma.data.network

import com.app.diploma.data.dto.Currency
import com.app.diploma.data.dto.Profile
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoService {

    @GET("v3/rates")
    suspend fun getCurrencies(@Query("ids") ids: String): ApiListResponse<Currency>

    @GET("v3/account")
    suspend fun getAccountData(): ApiDataResponse<Profile>

}

@Serializable
data class ApiListResponse<PAYLOAD>(
    val data: List<PAYLOAD>,
)

@Serializable
data class ApiDataResponse<PAYLOAD>(
    val data: PAYLOAD,
)
