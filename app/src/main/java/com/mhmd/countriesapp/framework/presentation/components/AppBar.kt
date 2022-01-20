package com.mhmd.countriesapp.framework.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@Composable
fun AppBar(
    title: String
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = MaterialTheme.colors.primary,
        elevation = 8.dp,
        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp),
    ) {
        Text(
            text = title,
            maxLines = 1, overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(Alignment.Start),
            style = MaterialTheme.typography.h6.copy(color = Color.White)
        )
    }
}
