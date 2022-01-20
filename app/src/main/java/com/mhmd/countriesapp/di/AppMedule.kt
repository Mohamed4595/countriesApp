package com.mhmd.countriesapp.di

import com.mhmd.countriesapp.framework.presentation.countriesList.CountriesListViewModel
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.compat.ScopeCompat.viewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {


    viewModel {
        CountriesListViewModel(get(), get(), get(), get())
    }
}