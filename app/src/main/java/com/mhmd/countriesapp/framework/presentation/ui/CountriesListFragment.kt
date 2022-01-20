package com.mhmd.countriesapp.framework.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.components.LoadingCountriesListShimmer
import com.mhmd.countriesapp.framework.presentation.components.CountryCard
import com.mhmd.countriesapp.framework.presentation.components.NothingHere
import com.mhmd.countriesapp.framework.presentation.components.SearchAppBar
import com.mhmd.countriesapp.framework.presentation.theme.CountriesAppTheme
import com.mhmd.countriesapp.framework.presentation.ui.CountriesListEvent.CountriesEvent
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
class CountriesListFragment : Fragment() {

    val connectivityManager: ConnectivityManager by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel by sharedViewModel<MainViewModel>()

        return ComposeView(requireContext()).apply {
            setContent {

                val countries = mainViewModel.countriesListLive.value

                val query = mainViewModel.query.value

                val loadingRefresh = mainViewModel.loading.value

                val loading = mainViewModel.loading.value

                val dialogQueue = mainViewModel.dialogQueue

                CountriesAppTheme(
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                    dialogQueue = dialogQueue.queue.value,
                ) {
                    Column(
                        modifier = Modifier.background(
                            MaterialTheme.colors.background
                        )
                    ) {
                        SearchAppBar(
                            query = query,
                            onQueryChanged = mainViewModel::onQueryChanged,
                            onExecuteSearch = { mainViewModel.onTriggerEvent(CountriesListEvent.SearchEvent) },
                            onFavoriteList = {
                                findNavController().navigate(
                                    R.id.action_countriesListFragment_to_favoriteListFragment
                                )
                            },
                        )
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(loadingRefresh),
                            onRefresh = {
                                mainViewModel.query.value = ""
                                mainViewModel.onTriggerEvent(CountriesEvent)
                            },
                        ) {
                            if (loading && countries!!.isEmpty()) {
                                LoadingCountriesListShimmer(imageHeight = 50.dp)
                            } else if (countries!!.isEmpty()) {
                                NothingHere()
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                                    itemsIndexed(
                                        items = countries
                                    ) { index, country ->
                                        mainViewModel.onChangeCountriesScrollPosition(index)
                                        CountryCard(
                                            country = country,
                                            onFavoriteClick = mainViewModel::onFavorite,
                                            onClick = {
                                                mainViewModel.setCountryData(country = country)
                                                findNavController().navigate(
                                                    R.id.action_countriesListFragment_to_countryDetailsFragment
                                                )
                                            }, query = query
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
