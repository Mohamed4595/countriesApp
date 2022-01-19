package com.mhmd.countriesapp.framework.datasource.network

import kotlinx.serialization.Serializable

@Serializable
data class Response(
    var success: Boolean,
    var code: Int,
    var message: String,
    var errors: String,
    var data: CountryResponse,
)
