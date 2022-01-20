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
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.components.AppBar
import com.mhmd.countriesapp.framework.presentation.components.CountryCard
import com.mhmd.countriesapp.framework.presentation.components.NothingHere
import com.mhmd.countriesapp.framework.presentation.ui.CountriesListEvent.FavouriteCountriesEvent
import com.mhmd.countriesapp.framework.presentation.theme.CountriesAppTheme
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
class FavoriteListFragment : Fragment() {

    val connectivityManager: ConnectivityManager by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel by sharedViewModel<MainViewModel>()

        mainViewModel.onTriggerEvent(FavouriteCountriesEvent)

        return ComposeView(requireContext()).apply {
            setContent {

                val countries = mainViewModel.favouriteCountriesListLive.value

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
                        AppBar(
                            title = stringResource(R.string.favorites)
                        )
                        if (!loading && countries!!.isEmpty()) {
                            NothingHere()
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxHeight()) {
                                itemsIndexed(
                                    items = countries!!
                                ) { index, country ->
                                    mainViewModel.onChangeFavouriteCountriesScrollPosition(
                                        index
                                    )
                                    CountryCard(
                                        country = country,
                                        onFavoriteClick = {
                                            mainViewModel.onRemoveFavorite(it)

                                        },
                                        onClick = {
                                            mainViewModel.setCountryData(country = country)
                                            findNavController().navigate(
                                                R.id.action_favoriteListFragment_to_countryDetailsFragment
                                            )
                                        }, query = ""


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
