package com.mhmd.countriesapp.framework.datasource.network.implementation

import com.mhmd.countriesapp.framework.datasource.network.CountryResponse
import com.mhmd.countriesapp.framework.datasource.network.Response
import com.mhmd.countriesapp.framework.datasource.network.abstraction.CountryDtoService
import com.mhmd.countriesapp.framework.datasource.network.service.CountryDto

class CountryDtoServiceImpl(
    private val countryDto: CountryDto,
) : CountryDtoService {

    override suspend fun getAll(
    ): CountryResponse {
        return countryDto.getAll().data;
    }
}
