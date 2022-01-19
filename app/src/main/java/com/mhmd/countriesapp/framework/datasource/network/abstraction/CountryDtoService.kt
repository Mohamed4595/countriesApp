package com.mhmd.countriesapp.framework.datasource.network.abstraction

import com.mhmd.countriesapp.framework.datasource.network.CountryResponse

interface CountryDtoService {
    suspend fun getAll(): CountryResponse
}
