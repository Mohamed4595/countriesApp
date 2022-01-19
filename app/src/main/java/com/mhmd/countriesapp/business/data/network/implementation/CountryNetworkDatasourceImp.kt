package com.mhmd.countriesapp.business.data.network.implementation

import com.mhmd.countriesapp.business.data.network.abstraction.CountryNetworkDatasource
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.network.abstraction.CountryDtoService
import com.mhmd.countriesapp.framework.datasource.network.mappers.CountryDtoMapper

class CountryNetworkDatasourceImp(
    private val countryDtoService: CountryDtoService,
    private val countryDtoMapper: CountryDtoMapper
) : CountryNetworkDatasource {
    override suspend fun getAll(
    ): List<Country> {
        return countryDtoMapper.fromEntityList(
            countryDtoService.getAll(
            ).items
        )
    }
}
