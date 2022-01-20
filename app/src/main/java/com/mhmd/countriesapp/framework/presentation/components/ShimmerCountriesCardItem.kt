package com.mhmd.countriesapp.framework.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerCountriesCardItem(
    colors: List<Color>,
    xShimmer: Float,
    yShimmer: Float,
    cardHeight: Dp,
    gradientWidth: Float,
    padding: Dp
) {
    val brush = linearGradient(
        colors,
        start = Offset(xShimmer - gradientWidth, yShimmer - gradientWidth),
        end = Offset(xShimmer, yShimmer)
    )
    Column(modifier = Modifier.padding(horizontal = padding, vertical = padding / 2)) {
        Surface(
            shape = RoundedCornerShape(10.dp),
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(cardHeight)
                    .background(brush = brush)
            )
        }
//        Spacer(modifier = Modifier.height(8.dp))
//        Surface(
//            shape = RoundedCornerShape(10.dp),
//            modifier = Modifier
//                .padding(vertical = 8.dp)
//        ) {
//            Spacer(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(cardHeight / 8)
//                    .background(brush = brush)
//            )
//        }
    }
}
