package com.mhmd.countriesapp.framework.presentation.countriesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.components.LoadingCountriesListShimmer
import com.mhmd.countriesapp.framework.presentation.components.CountryCard
import com.mhmd.countriesapp.framework.presentation.components.NothingHere
import com.mhmd.countriesapp.framework.presentation.components.SearchAppBar
import com.mhmd.countriesapp.framework.presentation.theme.CountriesAppTheme
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
         val viewModel by sharedViewModel<CountriesListViewModel>()

        return ComposeView(requireContext()).apply {
            setContent {

                val countries = viewModel.countriesListLive.value

                val query = viewModel.query.value

                val loading = viewModel.loading.value

                val dialogQueue = viewModel.dialogQueue

                CountriesAppTheme(
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                    dialogQueue = dialogQueue.queue.value,
                ) {
                    Box {
                        Column(
                            modifier = Modifier.background(
                                MaterialTheme.colors.background
                            )
                        ) {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = { viewModel.onTriggerEvent(CountriesListEvent.SearchEvent) },
                                onFavoriteList = {
                                    findNavController().navigate(
                                        R.id.action_countriesListFragment_to_favoriteListFragment
                                    )
                                },
                            )
                            if (loading && countries!!.isEmpty()) {
                                LoadingCountriesListShimmer(imageHeight = 50.dp)
                            } else if (countries!!.isEmpty()) {
                                NothingHere()
                            } else {
                                LazyColumn(modifier = Modifier.fillMaxHeight()) {
                                    itemsIndexed(
                                        items = countries
                                    ) { index, country ->
                                        viewModel.onChangeCountriesScrollPosition(index)
                                        CountryCard(
                                            country = country,
                                            index = index,
                                            isFavorite = country.isFavorite!!,
                                            onFavoriteClick = viewModel::onFavorite
                                        )
                                    }
                                }
                            }
                        }

                        if (loading && countries!!.isNotEmpty()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(MaterialTheme.colors.surface)
                                    .align(alignment = Alignment.BottomCenter)
                                    .padding(vertical = 16.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
