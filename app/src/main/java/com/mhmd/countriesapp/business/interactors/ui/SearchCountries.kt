package com.mhmd.countriesapp.business.interactors.ui

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchCountries(
    private val countryCacheDatasource: CountryCacheDatasource,
) {

    fun execute(
        query: String?
    ): Flow<DataState<List<Country>>> = flow {
        try {
            emit(DataState.loading())
            var countries = emptyList<Country>()
            try {
                countries = countryCacheDatasource.searchCountries(query = query ?: "")
            } catch (e: Exception) {
                // There was a network issue
                e.printStackTrace()
            }

            emit(DataState.success(countries))
        } catch (e: Exception) {
            emit(DataState.error(e.message ?: "Unknown Error"))
        }
    }
}
