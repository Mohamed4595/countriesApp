package com.mhmd.countriesapp.framework.presentation.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mhmd.countriesapp.framework.presentation.components.ConnectivityMonitor
import com.mhmd.countriesapp.framework.presentation.components.GenericDialog
import com.mhmd.countriesapp.framework.presentation.components.GenericDialogInfo
import java.util.Queue

private val DarkColorPalette = darkColors(
    primary = PrimaryColorDark,
    onPrimary = Color.White,
    secondary = Color.Black,
    onSecondary = Color.White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = BackgroundDark,
    onBackground = Color.White,
    surface = SurfaceDark,

    )

private val LightColorPalette = lightColors(
    primary = PrimaryColorLight,
    onPrimary = Color.White,
    secondary = Color.White,
    onSecondary = Color.White,
    error = RedErrorDark,
    onError = RedErrorLight,
    background = BackgroundLight,
    onBackground = Color.Black,
    surface = SurfaceLight,

    )

@ExperimentalComposeUiApi
@Composable
fun CountriesAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isNetworkAvailable: Boolean,
    isNoStatusBar: Boolean = false,
    dialogQueue: Queue<GenericDialogInfo>? = null,
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()

    val colors = if (darkTheme) {
        systemUiController.setStatusBarColor(
            color = if (isNoStatusBar) BackgroundDark else PrimaryColorDark,
            darkIcons = false
        )
        systemUiController.setNavigationBarColor(
            color = BackgroundDark,
            darkIcons = false
        )
        DarkColorPalette
    } else {
        systemUiController.setStatusBarColor(
            color = if (isNoStatusBar) BackgroundLight else PrimaryColorLight,
            darkIcons = isNoStatusBar
        )
        systemUiController.setNavigationBarColor(
            color = BackgroundLight,
            darkIcons = false
        )
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (!darkTheme) BackgroundLight else BackgroundDark)
        ) {
            Column {
                ConnectivityMonitor(isNetworkAvailable = isNetworkAvailable)
                content()
            }

            ProcessDialogQueue(
                dialogQueue = dialogQueue,
            )
        }
    }
}

@Composable
fun ProcessDialogQueue(
    dialogQueue: Queue<GenericDialogInfo>?,
) {
    dialogQueue?.peek()?.let { dialogInfo ->
        GenericDialog(
            onDismiss = dialogInfo.onDismiss,
            title = dialogInfo.title,
            description = dialogInfo.description,
            positiveAction = dialogInfo.positiveAction,
        )
    }
}
