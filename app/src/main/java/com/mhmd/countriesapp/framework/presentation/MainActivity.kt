package com.mhmd.countriesapp.framework.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.ExperimentalComposeUiApi
import com.mhmd.countriesapp.R
import com.mhmd.countriesapp.framework.presentation.ui.MainViewModel
import com.mhmd.countriesapp.framework.presentation.utils.ConnectivityManager
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalComposeUiApi
class MainActivity : AppCompatActivity() {

    val connectivityManager: ConnectivityManager by inject()
    private val viewModel by viewModel<MainViewModel>()

    override fun onStart() {
        super.onStart()
        connectivityManager.registerConnectionObserver(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        connectivityManager.unregisterConnectionObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
