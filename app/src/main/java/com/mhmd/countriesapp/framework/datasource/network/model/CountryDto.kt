package com.mhmd.countriesapp.framework.datasource.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CountryDto(

    var id: Int?,

    var enName: String?,

    var arName: String?,

    var countryCallingCode: String?,

    var countryCode: String?,

    var countryFlag: String?,

    )
