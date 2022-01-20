package com.mhmd.countriesapp.business.interactors.ui

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoveFavoriteCountry(
    private val countryCacheDatasource: CountryCacheDatasource,
) {

    fun execute(
        countryItemId: Int
    ): Flow<DataState<Country?>> = flow {
        try {
            emit(DataState.loading())
            val cacheCountryItem: Country? = countryCacheDatasource.getCountry(countryItemId)
            if (cacheCountryItem != null) {
                cacheCountryItem.isFavorite = 0
                countryCacheDatasource.insertCountry(cacheCountryItem)
            }

            emit(DataState.success(cacheCountryItem))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}
