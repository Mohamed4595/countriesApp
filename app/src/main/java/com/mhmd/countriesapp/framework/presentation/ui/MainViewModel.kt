package com.mhmd.countriesapp.framework.presentation.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.interactors.ui.*
import com.mhmd.countriesapp.framework.presentation.ui.CountriesListEvent.*
import com.mhmd.countriesapp.framework.presentation.utils.DialogQueue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel
constructor(
    private val searchCountries: SearchCountries,
    private val removeFavoriteCountry: RemoveFavoriteCountry,
    private val countriesList: CountriesList,
    private val favoriteCountry: FavoriteCountry,
    private val favoriteCountries: FavoriteCountries,
) : ViewModel() {

    private val _countriesListLive: MutableLiveData<List<Country>> = MutableLiveData(emptyList())

    val countriesListLive: LiveData<List<Country>>
        get() = _countriesListLive

    private val _countryLive: MutableLiveData<Country> = MutableLiveData()

    val countryLive: LiveData<Country>
        get() = _countryLive


    private val _favouriteCountriesListLive: MutableLiveData<List<Country>> = MutableLiveData(
        emptyList()
    )

    val favouriteCountriesListLive: LiveData<List<Country>>
        get() = _favouriteCountriesListLive

    val query = mutableStateOf("")

    val loading = mutableStateOf(false)
    val loadingRefresh = mutableStateOf(false)


    private var countriesListScrollPosition: Int = 0

    private var favouriteCountriesListScrollPosition: Int = 0

    val dialogQueue = DialogQueue()

    init {
        onTriggerEvent(CountriesEvent)
    }

    fun onTriggerEvent(event: CountriesListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is CountriesEvent -> {
                        getAllCountries()
                    }
                    is SearchEvent -> {
                        search()
                    }
                    is FavouriteCountriesEvent -> {
                        getFavoriteCountries()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getAllCountries() {

        _countriesListLive.value = listOf()
        countriesList.execute(
        ).onEach { dataState ->
            loading.value = dataState.loading
            loadingRefresh.value = dataState.loading
            dataState.data?.let { list ->
                _countriesListLive.value = list
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    private fun search() {

        searchCountries.execute(
            query = query.value,
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                _countriesListLive.value = list
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    private fun getFavoriteCountries() {

        favoriteCountries.execute(
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let { list ->
                _favouriteCountriesListLive.value = list
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    fun onChangeCountriesScrollPosition(position: Int) {
        setListScrollPosition(position)
    }

    fun onChangeFavouriteCountriesScrollPosition(position: Int) {
        setFavouriteListScrollPosition(position)
    }

    fun onQueryChanged(query: String) {
        setQuery(query)
    }

    private fun setListScrollPosition(position: Int) {
        countriesListScrollPosition = position
    }

    private fun setFavouriteListScrollPosition(position: Int) {
        favouriteCountriesListScrollPosition = position
    }

    private fun setQuery(query: String) {
        this.query.value = query
    }

    fun onFavorite(id: Int) {

        favoriteCountry.execute(
            id
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                setCountryData(it)
                search()

            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)


    }

    fun onRemoveFavorite(id: Int) {

        removeFavoriteCountry.execute(
            id
        ).onEach { dataState ->
            loading.value = dataState.loading

            dataState.data?.let {
                getFavoriteCountries()
                search()
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)


    }

    fun setCountryData(country: Country) {
        _countryLive.value = country;
    }
}
