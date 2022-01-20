package com.mhmd.countriesapp.business.interactors.ui

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteCountries(
    private val countryCacheDatasource: CountryCacheDatasource,
) {

    fun execute(
    ): Flow<DataState<List<Country>>> = flow {
        try {
            emit(DataState.loading())
            var country = emptyList<Country>()
            try {
                country = countryCacheDatasource.getFavouriteCountries()
            } catch (e: Exception) {
                // There was a network issue
                e.printStackTrace()
            }

            emit(DataState.success(country))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}
