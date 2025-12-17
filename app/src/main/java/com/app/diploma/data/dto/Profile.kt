package com.app.diploma.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val emailVerified: Boolean,
)
