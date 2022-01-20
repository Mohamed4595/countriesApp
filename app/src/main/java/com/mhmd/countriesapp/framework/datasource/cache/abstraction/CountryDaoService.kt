package com.mhmd.countriesapp.framework.datasource.cache.abstraction

import com.mhmd.countriesapp.business.domain.model.Country

interface CountryDaoService {

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

    suspend fun updateFavourite(isFavorite: Int, id: Int): Int

}
