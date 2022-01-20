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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.components.AppBar
import com.mhmd.countriesapp.framework.presentation.components.CountryCard
import com.mhmd.countriesapp.framework.presentation.components.NothingHere
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
        val countriesViewModel by sharedViewModel<CountriesListViewModel>()

        countriesViewModel.onTriggerEvent(CountriesListEvent.FavouriteCountriesEvent)

        return ComposeView(requireContext()).apply {
            setContent {

                val countries = countriesViewModel.favouriteCountriesListLive.value

                val loading = countriesViewModel.loading.value

                val dialogQueue = countriesViewModel.dialogQueue

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
                                        countriesViewModel.onChangeFavouriteCountriesScrollPosition(index)
                                        CountryCard(
                                            country = country,
                                            index = index,
                                            isFavorite = country.isFavorite!!,
                                            onFavoriteClick = {

//                                                countriesViewModel.onRefresh(countries[it].title!!)
//                                                favoriteViewModel.onFavorite(countries[it])
                                            }

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
