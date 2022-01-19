package com.mhmd.countriesapp.framework.datasource.network

import com.mhmd.countriesapp.framework.datasource.network.model.CountryDto
import kotlinx.serialization.Serializable

@Serializable
data class CountryResponse(
    var pageSize: Int,
    var currentPage: Int,
    var totalItems: Int,
    var totalPages: Int,
    var totalUnSeen: Int,
    var items: List<CountryDto>,
)
