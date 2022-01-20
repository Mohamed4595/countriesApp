package com.mhmd.countriesapp.framework.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.business.domain.model.Country
import com.mhmd.countriesapp.framework.datasource.network.utils.HttpRoutes.Image_URL
import com.mhmd.countriesapp.framework.presentation.theme.StarColor
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun CountryCard(
    country: Country,
    isFavorite: Boolean = false,
    index: Int,
    onFavoriteClick: (Int) -> Unit,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp,
            )
            .background(Color.Transparent)
            .fillMaxWidth()
            .height(50.dp),
        elevation = 8.dp,
    ) {

        Row(

            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colors.surface)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,

            ) {
            Row(

                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.9f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,

                ) {
            Image(
                painter = rememberImagePainter(
                    data = if (country.countryFlag == null) "" else Image_URL + country.countryFlag,
                    builder = {
                        crossfade(1000)
                        error(R.drawable.mega_logo)
                        // placeholder(R.drawable.app_logo)
                        //transformations(CircleCropTransformation())
                    }
                ),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
                    .clip(RoundedCornerShape(30.dp)),
                contentScale = ContentScale.Crop,

                )
            Text(
                text = if (country.countryCallingCode == null) "" else country.countryCallingCode.toString(),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth(Alignment.Start),
                style = MaterialTheme.typography.body2.copy(color = MaterialTheme.colors.onSurface)
            )
            Text(
                text = if (country.enName == null) "" else country.enName.toString(),
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .wrapContentWidth(Alignment.Start),
                style = MaterialTheme.typography.subtitle1.copy(color = MaterialTheme.colors.onSurface)
            )
        }
                IconButton(
                    modifier = Modifier
                        .width(40.dp)
                        .height(30.dp),
                    onClick = { onFavoriteClick(index) },
                ) {
                    Icon(if(isFavorite) Icons.Filled.Star else
                        Icons.Outlined.StarBorder,
                        "",
                        tint = StarColor
                    )
                }
            }
        }


}
