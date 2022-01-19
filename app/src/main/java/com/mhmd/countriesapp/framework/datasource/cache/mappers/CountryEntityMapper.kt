package com.mhmd.countriesapp.framework.datasource.cache.mappers

import com.mhmd.countriesapp.business.data.util.ModelMapper
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.cache.model.CountryEntity

class CountryEntityMapper : ModelMapper<CountryEntity, Country> {

    override fun mapToModel(model: CountryEntity): Country {
        return Country(
            id = model.id,
            enName = model.enName,
            arName = model.arName,
            countryCode = model.countryCode,
            countryCallingCode = model.countryCallingCode,
            countryFlag = model.countryFlag,
            isFavorite = model.isFavorite,
        )
    }

    override fun mapFromModel(model: Country): CountryEntity {
        return CountryEntity(
            id = model.id,
            enName = model.enName,
            arName = model.arName,
            countryCode = model.countryCode,
            countryCallingCode = model.countryCallingCode,
            countryFlag = model.countryFlag,
            isFavorite = model.isFavorite,
        )
    }

    fun fromEntityList(initial: List<CountryEntity>): List<Country> {
        return initial.map { mapToModel(it) }
    }

    fun toEntityList(initial: List<Country>): List<CountryEntity> {
        return initial.map { mapFromModel(it) }
    }
}
