package com.mhmd.countriesapp.framework.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import coil.compose.rememberImagePainter
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.datasource.network.utils.HttpRoutes
import com.mhmd.countriesapp.framework.presentation.components.CountryCard
import com.mhmd.countriesapp.framework.presentation.components.NothingHere
import com.mhmd.countriesapp.framework.presentation.theme.CountriesAppTheme
import com.mhmd.countriesapp.framework.presentation.theme.StarColor
import com.mhmd.countriesapp.framework.presentation.ui.CountriesListEvent.FavouriteCountriesEvent
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel

@ExperimentalComposeUiApi
@ExperimentalCoroutinesApi
class CountryDetailsFragment : Fragment() {

    val connectivityManager: ConnectivityManager by inject()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel by sharedViewModel<MainViewModel>()

        return ComposeView(requireContext()).apply {
            setContent {

                val country = mainViewModel.countryLive.value

                val loading = mainViewModel.loading.value

                val dialogQueue = mainViewModel.dialogQueue

                CountriesAppTheme(
                    isNetworkAvailable = connectivityManager.isNetworkAvailable.value,
                    dialogQueue = dialogQueue.queue.value,
                    isNoStatusBar = true
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                MaterialTheme.colors.background
                            )
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        if (!loading && country == null) {
                            NothingHere()
                        } else {
                            Box(
                                Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()) {
                                Card(
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier
                                        .padding(
                                            top = 40.dp,
                                            start = 16.dp, end = 16.dp
                                        )
                                        .background(Color.Transparent)
                                        .fillMaxWidth(),
                                    elevation = 8.dp,
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .background(MaterialTheme.colors.surface)
                                            .fillMaxWidth().padding(top = 60.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,

                                        ) {

                                        Text(
                                            text = if (country!!.enName == null) "" else country.enName.toString(),
                                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                                .wrapContentWidth(Alignment.Start),
                                            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
                                        )
                                        Text(
                                            text = if (country.arName == null) "" else country.arName.toString(),
                                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                                .wrapContentWidth(Alignment.Start),
                                            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
                                        )
                                        Text(
                                            text = if (country.countryCode == null) "" else country.countryCode.toString(),
                                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                                .wrapContentWidth(Alignment.Start),
                                            style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
                                        )
                                        Text(
                                            text = if (country.countryCallingCode == null) "" else country.countryCallingCode.toString(),
                                            maxLines = 1, overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp, vertical = 8.dp)
                                                .wrapContentWidth(Alignment.Start),
                                            style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
                                        )
                                        IconButton(
                                            modifier = Modifier
                                                .width(50.dp)
                                                .height(50.dp)
                                                .padding(vertical = 8.dp),
                                            onClick = {
                                                mainViewModel.onFavorite(country.id!!)
                                                mainViewModel.onTriggerEvent(FavouriteCountriesEvent)
                                            },
                                        ) {
                                            Icon(
                                                if (country.isFavorite == 1) Icons.Filled.Star else
                                                    Icons.Outlined.StarBorder,
                                                "",
                                                tint = StarColor
                                            )
                                        }
                                    }
                                }
                                Image(
                                    painter = rememberImagePainter(
                                        data = if (country!!.countryFlag == null) "" else HttpRoutes.Image_URL + country.countryFlag,
                                        builder = {
                                            crossfade(1000)
                                            error(R.drawable.mega_logo)
                                            // placeholder(R.drawable.app_logo)
                                            //transformations(CircleCropTransformation())
                                        }
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .width(80.dp)
                                        .height(80.dp)
                                        .align(Alignment.TopCenter)
                                        .clip(RoundedCornerShape(50.dp)),
                                    contentScale = ContentScale.Crop,
                                )

                            }

                        }
                    }
                }
            }
        }
    }
}
