package com.mhmd.countriesapp.business.data.cache.implementation

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.cache.abstraction.CountryDaoService

class CountryCacheDatasourceImp(private val CountryDaoService: CountryDaoService) :
    CountryCacheDatasource {

    override suspend fun insertCountry(country: Country): Long {
        return CountryDaoService.insertCountry(country)
    }

    override suspend fun getCountry(id: Int): Country? {
        return CountryDaoService.getCountry(id)
    }

    override suspend fun searchCountries(query: String): List<Country> {
        return CountryDaoService.searchCountries(query)
    }

    override suspend fun getAllCountries(): List<Country> {
        return CountryDaoService.getAllCountries()
    }

    override suspend fun getFavouriteCountries(): List<Country> {
        return CountryDaoService.getFavouriteCountries()
    }

    override suspend fun updateFavourite(isFavorite: Int, id: Int): Int {
        return CountryDaoService.updateFavourite(isFavorite, id)
    }

}
