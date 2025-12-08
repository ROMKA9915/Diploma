package com.app.diploma.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val id: String,
    val symbol: String,
    val currencySymbol: String?,
)
