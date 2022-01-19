package com.mhmd.countriesapp.framework.datasource.network.mappers

import com.mhmd.countriesapp.business.data.util.ModelMapper
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.network.model.CountryDto

class CountryDtoMapper : ModelMapper<CountryDto, Country> {

    override fun mapToModel(model: CountryDto): Country {
        return Country(
            id = model.id,
            arName = model.arName,
            enName = model.enName,
            countryCode = model.countryCode,
            countryCallingCode = model.countryCallingCode,
            countryFlag = model.countryFlag,
            isFavorite = false
        )
    }

    override fun mapFromModel(model: Country): CountryDto {
        return CountryDto(

            id = model.id,
            arName = model.arName,
            enName = model.enName,
            countryCode = model.countryCode,
            countryCallingCode = model.countryCallingCode,
            countryFlag = model.countryFlag,
        )
    }

    fun fromEntityList(initial: List<CountryDto>): List<Country> {
        return initial.map { mapToModel(it) }
    }

    fun toEntityList(initial: List<Country>): List<CountryDto> {
        return initial.map { mapFromModel(it) }
    }
}
