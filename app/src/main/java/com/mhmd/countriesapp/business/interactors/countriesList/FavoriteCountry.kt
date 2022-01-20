package com.mhmd.countriesapp.business.interactors.countriesList

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteCountry(
    private val countryCacheDatasource: CountryCacheDatasource,
) {

    fun execute(
        countryItem: Country
    ): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading())
            val cacheCountryItem: Country? = countryCacheDatasource.getCountry(countryItem.id!!)
            when {
                cacheCountryItem == null -> {
                    countryItem.isFavorite = true
                }
                cacheCountryItem.isFavorite == null -> {
                    countryItem.isFavorite = true
                }
                else -> {
                    countryItem.isFavorite = countryItem.isFavorite != true
                }
            }
            countryCacheDatasource.insertCountry(countryItem)

            emit(DataState.success(true))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}
