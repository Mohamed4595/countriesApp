package com.mhmd.countriesapp.di

import com.mhmd.countriesapp.framework.presentation.ui.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {


    viewModel {
        MainViewModel(get(), get(), get(), get(),get())
    }
}