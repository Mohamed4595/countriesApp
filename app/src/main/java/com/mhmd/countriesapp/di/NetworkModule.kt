package com.mhmd.countriesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.gson.GsonBuilder
import com.mhmd.countriesapp.business.data.network.abstraction.CountryNetworkDatasource
import com.mhmd.countriesapp.business.data.network.implementation.CountryNetworkDatasourceImp
import com.mhmd.countriesapp.framework.datasource.cache.database.AppDatabase
import com.mhmd.countriesapp.framework.datasource.network.abstraction.CountryDtoService
import com.mhmd.countriesapp.framework.datasource.network.implementation.CountryDtoServiceImpl
import com.mhmd.countriesapp.framework.datasource.network.mappers.CountryDtoMapper
import com.mhmd.countriesapp.framework.datasource.network.service.CountryDto
import com.mhmd.countriesapp.framework.datasource.network.utils.HttpRoutes
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module
import java.util.concurrent.Executors

val NetworkModule = module {
    single { provideCountryDto() }
    single { provideDtoCountryMapper() }
    single { provideCountryDtoService(get()) }
    single { provideCountryDatasource(get(), get()) }
    single { ConnectivityManager(androidContext()) }

}

fun provideCountryDto(): CountryDto {
    return Retrofit.Builder()
        .baseUrl(HttpRoutes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
        .create(CountryDto::class.java)
}

fun provideDtoCountryMapper(): CountryDtoMapper {
    return CountryDtoMapper()
}

fun provideCountryDtoService(
    countryDto: CountryDto,
): CountryDtoService {
    return CountryDtoServiceImpl(countryDto)
}

fun provideCountryDatasource(
    countryDtoService: CountryDtoService,
    countryDtoMapper: CountryDtoMapper
): CountryNetworkDatasource {
    return CountryNetworkDatasourceImp(countryDtoService, countryDtoMapper)
}
