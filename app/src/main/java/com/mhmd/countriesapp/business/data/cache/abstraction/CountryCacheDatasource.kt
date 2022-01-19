package com.mhmd.countriesapp.business.data.cache.abstraction

import com.mhmd.countriesapp.business.domain.model.Country

interface CountryCacheDatasource {

    suspend fun insertCountry(country: Country): Long

    suspend fun getCountry(
        id: Int,
    ): Country?

    suspend fun searchCountries(
        query: String
    ): List<Country>

    suspend fun getAllCountries(
    ): List<Country>

    suspend fun getFavouriteCountries(
    ): List<Country>
}
