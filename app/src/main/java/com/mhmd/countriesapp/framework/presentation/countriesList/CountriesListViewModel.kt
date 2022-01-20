package com.mhmd.countriesapp.framework.presentation.countriesList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.business.interactors.countriesList.CountriesList
import com.mhmd.countriesapp.business.interactors.countriesList.FavoriteCountries
import com.mhmd.countriesapp.business.interactors.countriesList.FavoriteCountry
import com.mhmd.countriesapp.business.interactors.countriesList.SearchCountries
import com.mhmd.countriesapp.framework.presentation.countriesList.CountriesListEvent.*
import com.mhmd.countriesapp.framework.presentation.utils.DialogQueue
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

 class CountriesListViewModel
public constructor(
    private val searchCountries: SearchCountries,
    private val countriesList: CountriesList,
    private val favoriteCountry: FavoriteCountry,
    private val favoriteCountries: FavoriteCountries,
) : ViewModel() {

    private val _countriesListLive: MutableLiveData<List<Country>> = MutableLiveData()

    val countriesListLive: LiveData<List<Country>>
        get() = _countriesListLive

    private val _favouriteCountriesListLive: MutableLiveData<List<Country>> = MutableLiveData()

    val favouriteCountriesListLive: LiveData<List<Country>>
        get() = _favouriteCountriesListLive

    val query = mutableStateOf("")

    val loading = mutableStateOf(false)

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

            dataState.data?.let { list ->
                _countriesListLive.value = list
            }

            dataState.error?.let { error ->
                dialogQueue.appendErrorMessage("An Error Occurred", error)
            }
        }.launchIn(viewModelScope)
    }

    private fun search() {

        _countriesListLive.value = listOf()
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

        _favouriteCountriesListLive.value = listOf()
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

    fun onFavorite(index: Int){

    }
//    fun onRefresh(newsTitle: String) {
//        var newsIndex: Int = -1
//        for (element in news.value) {
//            if (element.title == newsTitle) {
//                newsIndex =
//                    news.value.indexOf(element)
//                break
//            }
//        }
//        if (newsIndex != -1) {
//            news.value[newsIndex].isFavorite =
//                !news.value[newsIndex].isFavorite
//        } else {
//            if (selectedNewsItem.value != null) {
//                selectedNewsItem.value!!.isFavorite = !selectedNewsItem.value!!.isFavorite
//                setSelectedNewsItem(selectedNewsItem.value!!)
//            }
//        }
//
//        refreshNews.value =
//            !refreshNews.value
//    }

}
