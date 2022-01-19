package com.mhmd.countriesapp.business.interactors.countriesList

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.data.network.abstraction.CountryNetworkDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountriesList(
    private val countryCacheDatasource: CountryCacheDatasource,
    private val countryNetworkDatasource: CountryNetworkDatasource,
) {

    fun execute(
    ): Flow<DataState<List<Country>>> = flow {
        try {
            emit(DataState.loading())
            var countries = emptyList<Country>()
            try {
                countries = countryNetworkDatasource.getAll()
            } catch (e: Exception) {
                // There was a network issue
                e.printStackTrace()
            }

            if (countries.isNotEmpty())
                countries.forEach {
                    var country = countryCacheDatasource.getCountry(it.id!!)
                    if (country != null) {
                        it.isFavorite = country.isFavorite;
                        countryCacheDatasource.insertCountry(it)
                    } else {
                        it.isFavorite = false
                        countryCacheDatasource.insertCountry(it)
                    }
                }
            // emit List<Country> from cache
            val list = countryCacheDatasource.getAllCountries()

            emit(DataState.success(list))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}
