package com.mhmd.countriesapp.di

import com.mhmd.countriesapp.business.data.cache.abstraction.CountryCacheDatasource
import com.mhmd.countriesapp.business.data.network.abstraction.CountryNetworkDatasource
import com.mhmd.countriesapp.business.interactors.countriesList.CountriesList
import com.mhmd.countriesapp.business.interactors.countriesList.FavoriteCountry
import com.mhmd.countriesapp.business.interactors.countriesList.SearchCountries
import com.mhmd.countriesapp.business.interactors.countriesList.FavoriteCountries
import org.koin.dsl.module


val InteractorsModule = module {
    single { provideSearchCountries(get()) }
    single { provideFavouriteCountry(get()) }
    single { provideCountriesList(get(), get()) }
    single { provideFavouriteCountries(get()) }

}

fun provideSearchCountries(
    countryCacheDatasource: CountryCacheDatasource,
): SearchCountries {
    return SearchCountries(
        countryCacheDatasource = countryCacheDatasource,
    )
}

fun provideFavouriteCountry(
    countryCacheDatasource: CountryCacheDatasource,
): FavoriteCountry {
    return FavoriteCountry(
        countryCacheDatasource = countryCacheDatasource,
    )
}

fun provideFavouriteCountries(
    countryCacheDatasource: CountryCacheDatasource,
): FavoriteCountries {
    return FavoriteCountries(
        countryCacheDatasource = countryCacheDatasource,
    )
}

fun provideCountriesList(
    countryCacheDatasource: CountryCacheDatasource,
    countryNetworkDatasource: CountryNetworkDatasource,
): CountriesList {
    return CountriesList(
        countryCacheDatasource = countryCacheDatasource,
        countryNetworkDatasource = countryNetworkDatasource,
    )
}
