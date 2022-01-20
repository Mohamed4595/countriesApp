package com.mhmd.countriesapp.framework.presentation.countriesList

sealed class CountriesListEvent {
    object SearchEvent : CountriesListEvent()
    object CountriesEvent : CountriesListEvent()
    object FavouriteCountriesEvent : CountriesListEvent()
}
