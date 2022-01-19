package com.mhmd.countriesapp.framework.datasource.network.service

import com.mhmd.countriesapp.framework.datasource.network.Response
import retrofit2.http.GET

interface CountryDto {

    @GET("GetAll")
    suspend fun getAll(): Response
}
