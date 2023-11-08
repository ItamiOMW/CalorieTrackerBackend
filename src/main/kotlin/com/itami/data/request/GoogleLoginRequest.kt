package com.itami.data.request

import kotlinx.serialization.Serializable

@Serializable
data class GoogleLoginRequest(
    val googleIdToken: String,
)
