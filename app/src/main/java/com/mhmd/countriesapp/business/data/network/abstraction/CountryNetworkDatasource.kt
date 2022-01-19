package com.mhmd.countriesapp.business.data.network.abstraction
import com.mhmd.countriesapp.business.domain.model.Country

interface CountryNetworkDatasource {

    suspend fun getAll(
    ): List<Country>
}
