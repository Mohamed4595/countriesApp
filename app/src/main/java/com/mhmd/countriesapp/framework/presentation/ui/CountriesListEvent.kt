package com.mhmd.countriesapp.framework.presentation.ui

sealed class CountriesListEvent {
    object SearchEvent : CountriesListEvent()
    object CountriesEvent : CountriesListEvent()
    object FavouriteCountriesEvent : CountriesListEvent()

}
