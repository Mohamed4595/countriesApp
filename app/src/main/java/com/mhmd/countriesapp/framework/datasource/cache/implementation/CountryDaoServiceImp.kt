package com.mhmd.countriesapp.framework.datasource.cache.implementation

import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.cache.abstraction.CountryDaoService
import com.mhmd.countriesapp.framework.datasource.cache.database.CountryDao
import com.mhmd.countriesapp.framework.datasource.cache.mappers.CountryEntityMapper

class CountryDaoServiceImp(
    private val CountryDao: CountryDao,
    private val CountryEntityMapper: CountryEntityMapper
) :
    CountryDaoService {
    override suspend fun insertCountry(country: Country): Long {
        return CountryDao.insertCountry(CountryEntityMapper.mapFromModel(country))
    }

    override suspend fun getCountry(id: Int): Country? {
        return if (CountryDao.getCountry(id) == null) null
        else CountryEntityMapper.mapToModel(CountryDao.getCountry(id)!!)
    }

    override suspend fun searchCountries(query: String): List<Country> {
        return CountryEntityMapper.fromEntityList(CountryDao.searchCountries(query))
    }

    override suspend fun getAllCountries(): List<Country> {
        return CountryEntityMapper.fromEntityList(CountryDao.getAllCountries())
    }

    override suspend fun getFavouriteCountries(): List<Country> {
        return CountryEntityMapper.fromEntityList(CountryDao.getFavouriteCountries())
    }

    override suspend fun updateFavourite(isFavorite: Int,id: Int): Int {
        return CountryDao.updateFavourite(isFavorite,id)
    }
}
